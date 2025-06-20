package net.terullet.bibliothekarios.arachne.ui;

public interface Observable<T> {
	T get();
	void set(T value);
	boolean addListener(Observer<T> observer);
	boolean removeListener(Observer<T> observer);
}
