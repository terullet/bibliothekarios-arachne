package net.terullet.bibliothekarios.arachne.web.com.syosetu;

import net.terullet.bibliothekarios.arachne.web.WebWork;

import java.time.ZonedDateTime;

public interface NarouWork extends WebWork {
	String getNcode();
	long getNarouId();
	NarouWorkType getWorkType();
	ZonedDateTime getLastPostedAt();
	ZonedDateTime getLastUpdatedAt();
}
