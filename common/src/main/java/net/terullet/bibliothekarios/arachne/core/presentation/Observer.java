package net.terullet.bibliothekarios.arachne.core.presentation;

public interface Observer<T> {
	void onChanged(T newValue);
}
