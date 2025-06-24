package net.terullet.util.concurrent;

public interface WritableObservable<T> extends Observable<T> {
	void set(T value);
}
