package net.terullet.bibliothekarios.arachne.ui;

import net.terullet.bibliothekarios.arachne.ui.work.WorkListModel;
import net.terullet.bibliothekarios.arachne.core.presentation.Observable;
import net.terullet.bibliothekarios.arachne.core.presentation.SimpleObservable;
import net.terullet.bibliothekarios.arachne.core.presentation.WritableObservable;

public class MainModel {
	private final WritableObservable<OperationState> stateObservable = new SimpleObservable<>(OperationState.WORK);
	public final Observable<OperationState> stateObservable() {
		return this.stateObservable;
	}
	public final OperationState getState() {
		return this.stateObservable.get();
	}
	public final void transitState(OperationState state) {
		if (!this.getState().isTransitionableTo(state)) {
			throw new IllegalArgumentException("OperationState Transition from " + this.getState() + " to " + state + "is PROHIBITED.");
		}
		this.stateObservable.set(state);
	}
	private final WorkListModel workList = new WorkListModel();
	public WorkListModel getWorkList() {
		return this.workList;
	}
}
