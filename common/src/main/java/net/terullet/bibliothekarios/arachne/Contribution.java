package net.terullet.bibliothekarios.arachne;

public interface Contribution {
	long getId();
	Work getWork();
	Contributor getContributor();
}
