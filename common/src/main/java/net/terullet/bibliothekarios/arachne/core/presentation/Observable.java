package net.terullet.bibliothekarios.arachne.core.presentation;

public interface Observable<T> {
	T get();
	boolean addListener(Observer<T> observer);
	boolean removeListener(Observer<T> observer);
}
