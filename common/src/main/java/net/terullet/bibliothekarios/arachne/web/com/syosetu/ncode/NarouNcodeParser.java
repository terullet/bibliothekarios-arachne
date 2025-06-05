package net.terullet.bibliothekarios.arachne.web.com.syosetu.ncode;

import net.terullet.bibliothekarios.arachne.web.com.syosetu.NarouEpisodeMetadata;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.DeflaterInputStream;
import java.util.zip.GZIPInputStream;

class NarouNcodeParser {
	private static final Logger logger = LogManager.getLogger(new Throwable().getStackTrace()[0].getClassName());

	private InputStream decompressInputStream(HttpResponse<InputStream> httpResponse) throws IOException {
		return switch (Encoding.fromKey(httpResponse.headers().firstValue("Content-Encoding").orElse(""))) {
			case GZIP -> new GZIPInputStream(new BufferedInputStream(httpResponse.body()));
			case DEFLATE -> new DeflaterInputStream(new BufferedInputStream(httpResponse.body()));
			case null -> new BufferedInputStream(httpResponse.body());
		};
	}
	private Document parseToDocument(HttpResponse<InputStream> httpResponse) throws IOException {
		String charset = "utf-8";
		for (String c : httpResponse.headers().map().get("Content-Type")) {
			Matcher m = CONTENT_TYPE_CHARSET_PATTERN.matcher(c);
			if (m.find()) {
				charset = m.group("charset");
			}
		}
		return Jsoup.parse(decompressInputStream(httpResponse), charset, httpResponse.request().uri().toString());
	}

	private static final Pattern CONTENT_TYPE_CHARSET_PATTERN = Pattern.compile("charset=(?<charset>\\S+)");
	private void detectErrors(NarouNcodeFetcher.Response response) {
		switch (response.httpResponse().statusCode()) {
			case 200, 301:
				return;
			case 302:
				List<String> locations = response.httpResponse().headers().map().get("Location");
				if (locations != null && !locations.isEmpty() && locations.getFirst().contains("login")) {
					throw new AuthenticationRequiredException("Authentication required.");
				}
				return;
			case 404:
				Document doc;
				try {
					doc = this.parseToDocument(response.httpResponse());
				} catch (IOException e) {
					logger.error("Failed to parse error page.", e);
					throw new RuntimeException("Failed to parse error page.", e);
				}
				logger.warn(doc.select("#contents_main .description").getFirst().ownText());
				String msg = doc.select("#contents_main .description .nothing").getFirst().ownText();
				if (msg.contains("見つかりません")) {
					throw new ContentNotFoundException(response.httpResponse().request().uri().toString());
				}
				if (msg.contains("接続規制")) {
					throw new AccessRestrictedException(msg);
				}
			case 503:
				throw new RuntimeException("Server would be under maintenance.");
		}
		throw new RuntimeException("Unexpected status(" + response.httpResponse().statusCode() + ") received.");
	}

	private static final Pattern EPISODE_URL_EPISODE_NUMBER_PATTERN = Pattern.compile("/[nN]\\d{4}[a-zA-Z]{1,2}/(?<episodeNumber>\\d+)/");
	private static final Pattern DATETIME_PATTERN = Pattern.compile("(?<data>\\d{4}/\\d{2}/\\d{2})\\D+?(?<time>\\d{2}:\\d{2})");
	PageResponse extractEpisodes(NarouNcodeFetcher.WorkResponse response) {
		// detect errors.
		this.detectErrors(response);
		// parse
		Document doc;
		try {
			doc = this.parseToDocument(response.httpResponse());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		List<NarouEpisodeResponseMetadata> episodes = new ArrayList<>();
		for (Element e : doc.select(".p-eplist").getFirst().children()) {
			switch (e.className()) {
				case "p-eplist__chapter-title":
					// TODO: Construct WebNode
					break;
				case "p-eplist__sublist":
					Element titleElement = e.select("a.p-eplist__subtitle").getFirst();
					Matcher episodeNumberMatcher = EPISODE_URL_EPISODE_NUMBER_PATTERN.matcher(titleElement.attr("href"));
					if (!episodeNumberMatcher.find()) {
						throw new RuntimeException("Failed to parse where the episode is.");
					}
					int episodeNumber = Integer.parseInt(episodeNumberMatcher.group("episodeNumber"));
					Element timeElement = e.getElementsByClass("p-eplist__update").getFirst();
					Matcher postedAtMatcher = DATETIME_PATTERN.matcher(timeElement.ownText());
					if (!postedAtMatcher.find()) {
						throw new RuntimeException("Failed to parse when the episode is posted.");
					}
					ZonedDateTime postedAt = ZonedDateTime.of(LocalDateTime.parse(postedAtMatcher.group("date") + " " + postedAtMatcher.group("time"), NarouEpisodeMetadata.NAROU_EPISODE_DATETIME_FORMAT), ZoneOffset.ofHours(9));
					ZonedDateTime lastUpdatedAt = null;
					Elements tues = timeElement.getElementsByTag("span");
					if (!tues.isEmpty()) {
						Matcher lastModifiedAtMatcher = DATETIME_PATTERN.matcher(tues.getFirst().attr("title"));
						if (!lastModifiedAtMatcher.find()) {
							throw new RuntimeException("Failed to parse when the episode is updated.");
						}
						lastUpdatedAt = ZonedDateTime.of(LocalDateTime.parse(lastModifiedAtMatcher.group("date") + " " + lastModifiedAtMatcher.group("time"), NarouEpisodeMetadata.NAROU_EPISODE_DATETIME_FORMAT), ZoneOffset.ofHours(9));
					}
					episodes.add(new NarouEpisodeResponseMetadata(response.work(), episodeNumber, titleElement.ownText(), postedAt, lastUpdatedAt));
					break;
				default:
					logger.debug("Unknown element: {}.{}", e.tagName(), e.className());
					break;
			}
		}
		return new PageResponse(response.pageNumber(), response.pageNumber() == 0 ? this.parseNumPages(doc) : 0, episodes);
	}

	private static final Pattern WORK_URL_PAGE_NUMBER_PATTERN = Pattern.compile("\\?p=(?<page>\\d+)");
	int parseNumPages(Document document) {
		Elements lastPages = document.select("c-pager__item c-pager__item--last");
		if (lastPages.isEmpty()) {
			return 0;
		}
		String lastURL = lastPages.getFirst().attr("href");
		Matcher matcher = WORK_URL_PAGE_NUMBER_PATTERN.matcher(lastURL);
		if (matcher.find()) {
			return Integer.parseInt(matcher.group("page"));
		}
		return 0;
	}

	List<String> extractToParagraphs(NarouNcodeFetcher.EpisodeResponse response) {
		this.detectErrors(response);
		BufferedReader br = null;
		List<String> paragraphs = new LinkedList<>();
		try {
			br = new BufferedReader(new InputStreamReader(decompressInputStream(response.httpResponse()), StandardCharsets.UTF_8));
			String str;
			while ((str = br.readLine()) != null) {
				paragraphs.add(str);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					logger.warn(e);
				}
			}
		}
		return paragraphs;
	}

	record PageResponse(int requestPageNumber, int lastPageNumber, List<NarouEpisodeResponseMetadata> episodes) { }
}
