package net.terullet.bibliothekarios.arachne.ui;

import java.util.Map;
import java.util.Set;

public enum OperationState {
	EXPLORATION,
	WORK,
	EPISODE,
	;
	private static final Map<OperationState, Set<OperationState>> STATE_TRANSITIONABLE_MAP = Map.ofEntries(
			Map.entry(EXPLORATION, Set.of(WORK)),
			Map.entry(WORK, Set.of(EXPLORATION, EPISODE)),
			Map.entry(EPISODE, Set.of(WORK))
	);

	public boolean isTransitionableTo(OperationState to) {
		return STATE_TRANSITIONABLE_MAP.get(this).contains(to);
	}
}
