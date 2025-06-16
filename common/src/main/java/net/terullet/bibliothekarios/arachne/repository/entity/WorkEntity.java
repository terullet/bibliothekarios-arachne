package net.terullet.bibliothekarios.arachne.repository.entity;

import net.terullet.bibliothekarios.arachne.domain.Site;

import java.time.LocalDateTime;

public interface WorkEntity {
	long getId();
	Site getSite();
	String getSummary();
	LocalDateTime getRegisteredAt();
}
