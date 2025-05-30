package net.terullet.bibliothekarios.arachne.web;

import java.util.concurrent.CompletionStage;

public interface WebEpisode {
	WebWork getWebWork();
	String getTitle();
	CompletionStage<String> getContent();
}
