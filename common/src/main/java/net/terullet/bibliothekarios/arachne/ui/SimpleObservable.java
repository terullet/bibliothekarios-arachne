package net.terullet.bibliothekarios.arachne.ui;

import java.util.HashSet;
import java.util.Set;

public class SimpleObservable<T> implements Observable<T> {
	private T value;
	private final Set<Observer<T>> listeners = new HashSet<>();

	public T get() {
		return this.value;
	}
	public void set(T value) {
		this.value = value;
		for (Observer<T> l : this.listeners) {
			l.onChanged(value);
		}
	}
	public boolean addListener(Observer<T> observer) {
		return this.listeners.add(observer);
	}
	public boolean removeListener(Observer<T> observer) {
		return this.listeners.remove(observer);
	}
}
