package net.terullet.bibliothekarios.arachne.domain.web.com.syosetu;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum NarouWorkType {
	CURRENTLY_SERIALIZED(0, "連載中"),
	SERIES_ENDED(1, "完結済"),
	STANDALONE(2, "短編");

	private final int id;
	private final String description;
	NarouWorkType(int id, String description) {
		this.id = id;
		this.description = description;
	}

	public final int getId() {
		return this.id;
	}
	public final String getDescription() {
		return this.description;
	}
	@Override
	public final String toString() {
		return this.description;
	}

	private static final Map<Integer, NarouWorkType> ID_WORK_TYPE_MAP = Arrays.stream(NarouWorkType.values()).collect(Collectors.toMap(NarouWorkType::getId, Function.identity()));
	public static NarouWorkType fromId(int id) {
		NarouWorkType workType = ID_WORK_TYPE_MAP.get(id);
		if (workType == null) {
			throw new IllegalArgumentException(String.format("Invalid NarouWorkType ID: %d. Valid IDs are: %s", id, ID_WORK_TYPE_MAP.keySet()));
		}
		return workType;
	}
}
