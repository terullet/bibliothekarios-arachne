package net.terullet.util.concurrent;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class SimpleObservable<T> implements WritableObservable<T> {
	private T value;
	private final Object valueSyncObj = new Object();
	private final Set<Observer<T>> observers = new HashSet<>();
	private final Object observerSyncObj = new Object();

	public SimpleObservable() { }
	public SimpleObservable(T initialValue) {
		this.value = initialValue;
	}

	public T get() {
		synchronized (this.valueSyncObj) {
			return this.value;
		}
	}
	public void set(T value) {
		synchronized (this.valueSyncObj) {
			if (!Objects.equals(this.value, value)) {
				this.value = value;
				synchronized (this.observerSyncObj) {
					for (Observer<T> l : this.observers) {
						try {
							l.onChanged(value);
						} catch (Exception e) {
							// Ignore exception to continue notifying.
						}
					}
				}
			}
		}
	}
	public boolean addListener(Observer<T> observer) {
		synchronized (this.observerSyncObj) {
			return this.observers.add(observer);
		}
	}
	public boolean removeListener(Observer<T> observer) {
		synchronized (this.observerSyncObj) {
			return this.observers.remove(observer);
		}
	}
}
