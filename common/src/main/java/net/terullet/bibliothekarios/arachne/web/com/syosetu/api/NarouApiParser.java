package net.terullet.bibliothekarios.arachne.web.com.syosetu.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import javafx.util.Pair;
import net.terullet.util.concurrent.NamedThreadFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.zip.GZIPInputStream;

public class NarouApiParser {
	public static final ExecutorService executors = Executors.newCachedThreadPool(new NamedThreadFactory("NarouApiParser"));

	public List<? extends NarouWorkMetadata> parse(Pair<NarouApiQuery, InputStream> response) throws IOException {
		JsonMapper jsonMapper = new JsonMapper();
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode rootNode = null;
		Reader reader = null;
		try {
			rootNode = jsonMapper.readTree(reader = new InputStreamReader(new GZIPInputStream(new BufferedInputStream(response.getValue())), StandardCharsets.UTF_8));
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
		if (!(rootNode instanceof ArrayNode arrayNode)) {
			throw new IllegalStateException("rootNode must be an instance of ArrayNode");
		}
		int count = arrayNode.get(0).get("allcount").asInt();
		if (count <= 0) {
			return Collections.emptyList();
		}
		arrayNode.remove(0);
		return switch (response.getKey()) {
			case NarouAllAgesApiQuery aaq ->
					objectMapper.readValue(arrayNode.traverse(), new TypeReference<List<NarouAllAgesWorkMetadata>>() { });
			case NarouR18ApiQuery r18q ->
					objectMapper.readValue(arrayNode.traverse(), new TypeReference<List<NarouR18WorkMetadata>>() { });
		};
	}
}
