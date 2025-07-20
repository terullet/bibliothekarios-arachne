package net.terullet.bibliothekarios.arachne.core.presentation;

public interface WritableObservable<T> extends Observable<T> {
	void set(T value);
}
