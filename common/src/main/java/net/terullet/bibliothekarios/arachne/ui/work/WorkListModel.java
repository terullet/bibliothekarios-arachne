package net.terullet.bibliothekarios.arachne.ui.work;

import net.terullet.util.concurrent.ObservableArrayList;
import net.terullet.util.concurrent.ObservableList;

public class WorkListModel {
	private final ObservableList<WorkOverviewModel> works = new ObservableArrayList<>();
	public final ObservableList<WorkOverviewModel> getWorks() {
		return this.works;
	}
}
