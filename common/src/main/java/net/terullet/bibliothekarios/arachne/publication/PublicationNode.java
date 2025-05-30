package net.terullet.bibliothekarios.arachne.publication;

import java.util.List;

public class PublicationNode {
	private Publication publication;
	private PublicationNode parent;
	private String title;
	private List<PublicationNode> children;
}
