package net.terullet.bibliothekarios.arachne.ui.web;

import java.util.List;

public interface Section {
	WebWork getWork();
	Section getParent();
	void setParent(Section parent);
	List<Section> getChildNodes();
	List<Episode> getEpisodes();
}
