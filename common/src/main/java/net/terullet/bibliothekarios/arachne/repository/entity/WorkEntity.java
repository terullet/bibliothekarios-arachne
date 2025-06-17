package net.terullet.bibliothekarios.arachne.repository.entity;

import net.terullet.bibliothekarios.arachne.repository.Site;

import java.time.LocalDateTime;

public interface WorkEntity {
	long getId();
	Site getSite();
	String getSummary();
	LocalDateTime getRegisteredAt();
}
