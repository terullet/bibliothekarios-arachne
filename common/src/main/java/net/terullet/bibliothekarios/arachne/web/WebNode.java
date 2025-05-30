package net.terullet.bibliothekarios.arachne.web;

import java.util.List;

public interface WebNode {
	WebWork getWork();
	WebNode getParent();
	void setParent(WebNode parent);
	List<WebNode> getChildNodes();
	List<WebEpisode> getEpisodes();
}
