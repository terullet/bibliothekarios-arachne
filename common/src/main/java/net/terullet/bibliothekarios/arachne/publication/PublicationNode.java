package net.terullet.bibliothekarios.arachne.publication;

import java.util.List;

public interface PublicationNode {
	Publication getPublication();
	PublicationNode getParent();
	void setParent(PublicationNode parent);
	String getTitle();
	void setTitle(String title);
	List<PublicationNode> getChildren();
}
