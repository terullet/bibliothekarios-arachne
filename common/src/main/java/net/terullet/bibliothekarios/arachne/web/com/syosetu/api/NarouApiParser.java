package net.terullet.bibliothekarios.arachne.web.com.syosetu.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import javafx.util.Pair;
import net.terullet.util.concurrent.NamedThreadFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.zip.GZIPInputStream;

class NarouApiParser {
	private static final Logger logger = LogManager.getLogger(new Throwable().getStackTrace()[0].getClassName());
	static final ExecutorService EXECUTOR = Executors.newCachedThreadPool(new NamedThreadFactory("NarouApiParser"));
	private final JsonMapper jsonMapper = new JsonMapper();
	private final ObjectMapper objectMapper = new ObjectMapper();

	private void detectErrors(Pair<NarouApiQuery, HttpResponse<InputStream>> response) {
		HttpResponse<InputStream> httpResponse = response.getValue();
		logger.debug(httpResponse.headers());
		if (httpResponse.statusCode() == 200) {
			logger.trace("Succeeded!");
			return;
		}
		logger.warn("Unexpected status({}) received.", httpResponse.statusCode());
		throw new RuntimeException("Unexpected StatusCode(" + httpResponse.statusCode() + ") received.");
	}

	List<? extends NarouWorkMetadata> parse(Pair<NarouApiQuery, HttpResponse<InputStream>> response) {
		// check errors.
		this.detectErrors(response);
		// parse json response.
		JsonNode rootNode = null;
		Reader reader = null;
		try {
			rootNode = this.jsonMapper.readTree(reader = new InputStreamReader(new GZIPInputStream(new BufferedInputStream(response.getValue().body())), StandardCharsets.UTF_8));
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					logger.warn(e);
				}
			}
		}
		if (!(rootNode instanceof ArrayNode arrayNode)) {
			throw new IllegalStateException("rootNode must be an instance of ArrayNode");
		}
		// extract "allcount".
		int count = arrayNode.get(0).get("allcount").asInt();
		if (count <= 0) {
			return Collections.emptyList();
		}
		arrayNode.remove(0);
		// map to List<? extends NarouWorkMetadata>
		try {
			return switch (response.getKey()) {
				case NarouAllAgesApiQuery aaq ->
						this.objectMapper.readValue(arrayNode.traverse(), new TypeReference<List<NarouAllAgesWorkMetadata>>() { });
				case NarouR18ApiQuery r18q ->
						this.objectMapper.readValue(arrayNode.traverse(), new TypeReference<List<NarouR18WorkMetadata>>() { });
			};
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
