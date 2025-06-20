package net.terullet.bibliothekarios.arachne.ui;

public interface Observer<T> {
	void onChanged(T newValue);
}
