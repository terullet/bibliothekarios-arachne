package net.terullet.bibliothekarios.arachne.repository.entity;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum ReviewTarget {
	WORK(1),
	EPISODE(2),
	CONTRIBUTOR(3),
	;
	private final int id;

	private static final Map<Integer, ReviewTarget> ID_REVIEW_TARGET_MAP =
			Arrays.stream(values()).collect(Collectors.toMap(ReviewTarget::getId, Function.identity()));

	ReviewTarget(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	/**
	 * Get ReviewTarget enum from ID.
	 *
	 * @param id ReviewTarget ID
	 * @return ReviewTarget enum value
	 * @throws IllegalArgumentException when invalid id is specified
	 */
	public static ReviewTarget fromId(int id) {
		ReviewTarget rt = ID_REVIEW_TARGET_MAP.get(id);
		if (rt == null) {
			throw new IllegalArgumentException(String.format("Invalid ReviewTarget ID: %d. Valid IDs are: %s", id, ID_REVIEW_TARGET_MAP.keySet()));
		}
		return rt;
	}

	/**
	 * Get ReviewTarget enum from ID. (null safe version)
	 *
	 * @param id ReviewTarget ID (nullable)
	 * @return ReviewTarget enum value or null
	 */
	public static ReviewTarget fromIdNullable(Integer id) {
		return id == null ? null : fromId(id);
	}

	public static boolean isValidId(int id) {
		return ID_REVIEW_TARGET_MAP.containsKey(id);
	}
}
