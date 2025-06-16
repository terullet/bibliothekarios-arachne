package net.terullet.bibliothekarios.arachne.ui.web.com.syosetu;

import net.terullet.bibliothekarios.arachne.domain.web.com.syosetu.NarouWorkType;
import net.terullet.bibliothekarios.arachne.ui.web.WebWork;

import java.time.ZonedDateTime;

public interface NarouWork extends WebWork {
	String getNcode();
	long getNarouId();
	NarouWorkType getWorkType();
	ZonedDateTime getLastPostedAt();
	ZonedDateTime getLastUpdatedAt();
}
