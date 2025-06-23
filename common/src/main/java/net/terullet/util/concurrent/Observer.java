package net.terullet.util.concurrent;

public interface Observer<T> {
	void onChanged(T newValue);
}
