package net.terullet.util.concurrent;

public interface Observable<T> {
	T get();
	boolean addListener(Observer<T> observer);
	boolean removeListener(Observer<T> observer);
}
