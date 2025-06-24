package net.terullet.bibliothekarios.arachne.ui.work;

import net.terullet.bibliothekarios.arachne.service.WorkOverview;
import net.terullet.util.concurrent.Observable;
import net.terullet.util.concurrent.SimpleObservable;
import net.terullet.util.concurrent.WritableObservable;

public class WorkOverviewModel {
	private final long id;
	public final long getId() {
		return this.id;
	}
	private final WritableObservable<String> titleObservable = new SimpleObservable<>();
	public final Observable<String> titleObservable() {
		return this.titleObservable;
	}
	public final String getTitle() {
		return this.titleObservable.get();
	}
	public final void setTitle(String title) {
		this.titleObservable.set(title);
	}

	public WorkOverviewModel(WorkOverview workOverview) {
		this.id = workOverview.id();
		this.update(workOverview);
	}

	public void update(WorkOverview workOverview) {
		if (this.id != workOverview.id()) {
			throw new IllegalArgumentException(String.format("WorkOverview ID doesn't match (target: %d, given: %d).", this.id, workOverview.id()));
		}
		this.setTitle(workOverview.title());
	}
}
