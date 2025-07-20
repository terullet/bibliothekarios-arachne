package net.terullet.bibliothekarios.arachne.ui.work;

import net.terullet.bibliothekarios.arachne.core.presentation.ObservableArrayList;
import net.terullet.bibliothekarios.arachne.core.presentation.ObservableList;

public class WorkListModel {
	private final ObservableList<WorkOverviewModel> works = new ObservableArrayList<>();
	public final ObservableList<WorkOverviewModel> getWorks() {
		return this.works;
	}
}
