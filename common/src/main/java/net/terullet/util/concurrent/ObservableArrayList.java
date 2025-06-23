package net.terullet.util.concurrent;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public class ObservableArrayList<T> implements ObservableList<T> {
	private final List<T> list;
	private final Object listSyncObj = new Object();
	private final Set<Observer<T>> observers = new HashSet<>();
	private final Object observerSyncObj = new Object();

	@Override
	public boolean addListener(Observer<T> observer) {
		synchronized (this.observerSyncObj) {
			return this.observers.add(observer);
		}
	}

	@Override
	public boolean removeListener(Observer<T> observer) {
		synchronized (this.observerSyncObj) {
			return this.observers.remove(observer);
		}
	}

	private void notifyObservers(ListChange<T> change) {
		synchronized (this.observerSyncObj) {
			for (Observer<T> observer : this.observers) {
				try {
					observer.onChanged(change);
				} catch (Exception e) {
					// Ignore exception to continue notifying.
				}
			}
		}
	}

	public ObservableArrayList() {
		this.list = new ArrayList<>();
	}

	public ObservableArrayList(Collection<? extends T> c) {
		this.list = new ArrayList<>(c);
	}

	public ObservableArrayList(int initialCapacity) {
		this.list = new ArrayList<>(initialCapacity);
	}

	@Override
	public int size() {
		synchronized (this.listSyncObj) {
			return this.list.size();
		}
	}

	@Override
	public boolean isEmpty() {
		synchronized (this.listSyncObj) {
			return this.list.isEmpty();
		}
	}

	@Override
	public boolean contains(Object o) {
		synchronized (this.listSyncObj) {
			return this.list.contains(o);
		}
	}

	@Override
	public Iterator<T> iterator() {
		synchronized (this.listSyncObj) {
			return new ArrayList<>(this.list).iterator();
		}
	}

	@Override
	public Object[] toArray() {
		synchronized (this.listSyncObj) {
			return this.list.toArray();
		}
	}

	@Override
	public <U> U[] toArray(U[] a) {
		synchronized (this.listSyncObj) {
			return this.list.toArray(a);
		}
	}

	@Override
	public boolean add(T t) {
		synchronized (this.listSyncObj) {
			int index = this.list.size();
			// ArrayList.add is always true.
			this.list.add(t);
			this.notifyObservers(new ListInsertions<>(List.of(new InsertionElement<>(index, t))));
			return true;
		}
	}

	@Override
	public boolean remove(Object o) {
		synchronized (this.listSyncObj) {
			int index = this.list.indexOf(o);
			if (index >= 0) {
				@SuppressWarnings("unchecked")
				T removed = (T) o;
				boolean result = this.list.remove(o);
				if (result) {
					this.notifyObservers(new ListRemovals<>(List.of(new RemovalElement<>(index, removed))));
				}
				return result;
			}
			return false;
		}
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		synchronized (this.listSyncObj) {
			return new HashSet<>(this.list).containsAll(c);
		}
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		synchronized (this.listSyncObj) {
			int startIndex = this.list.size();
			boolean result = this.list.addAll(c);
			if (result && !c.isEmpty()) {
				List<InsertionElement<T>> insertions = new ArrayList<>();
				int index = startIndex;
				for (T item : c) {
					insertions.add(new InsertionElement<>(index++, item));
				}
				this.notifyObservers(new ListInsertions<>(insertions));
			}
			return result;
		}
	}

	@Override
	public boolean addAll(int index, Collection<? extends T> c) {
		synchronized (this.listSyncObj) {
			boolean result = this.list.addAll(index, c);
			if (result && !c.isEmpty()) {
				List<InsertionElement<T>> insertions = new ArrayList<>();
				int currentIndex = index;
				for (T item : c) {
					insertions.add(new InsertionElement<>(currentIndex++, item));
				}
				this.notifyObservers(new ListInsertions<>(insertions));
			}
			return result;
		}
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		synchronized (this.listSyncObj) {
			List<RemovalElement<T>> removals = new ArrayList<>();
			for (int i = this.list.size() - 1; i >= 0; i--) {
				T item = this.list.get(i);
				if (c.contains(item)) {
					removals.add(new RemovalElement<>(i, item));
				}
			}
			boolean result = this.list.removeAll(c);
			if (result && !removals.isEmpty()) {
				Collections.reverse(removals);
				this.notifyObservers(new ListRemovals<>(removals));
			}
			return result;
		}
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		synchronized (this.listSyncObj) {
			List<RemovalElement<T>> removals = new ArrayList<>();
			for (int i = this.list.size() - 1; i >= 0; i--) {
				T item = this.list.get(i);
				if (!c.contains(item)) {
					removals.add(new RemovalElement<>(i, item));
				}
			}
			boolean result = this.list.retainAll(c);
			if (result && !removals.isEmpty()) {
				Collections.reverse(removals);
				this.notifyObservers(new ListRemovals<>(removals));
			}
			return result;
		}
	}

	@Override
	public void clear() {
		synchronized (this.listSyncObj) {
			if (!this.list.isEmpty()) {
				this.list.clear();
				this.notifyObservers(new ListClearing<>());
			}
		}
	}

	@Override
	public T get(int index) {
		synchronized (this.listSyncObj) {
			return this.list.get(index);
		}
	}

	@Override
	public T set(int index, T element) {
		synchronized (this.listSyncObj) {
			T old = this.list.set(index, element);
			if (!Objects.equals(old, element)) {
				this.notifyObservers(new ListReplacements<>(List.of(new ReplacementElement<>(index, element))));
			}
			return old;
		}
	}

	@Override
	public void add(int index, T element) {
		synchronized (this.listSyncObj) {
			this.list.add(index, element);
			this.notifyObservers(new ListInsertions<>(List.of(new InsertionElement<>(index, element))));
		}
	}

	@Override
	public T remove(int index) {
		synchronized (this.listSyncObj) {
			T removed = this.list.remove(index);
			this.notifyObservers(new ListRemovals<>(List.of(new RemovalElement<>(index, removed))));
			return removed;
		}
	}

	@Override
	public int indexOf(Object o) {
		synchronized (this.listSyncObj) {
			return this.list.indexOf(o);
		}
	}

	@Override
	public int lastIndexOf(Object o) {
		synchronized (this.listSyncObj) {
			return this.list.lastIndexOf(o);
		}
	}

	@Override
	public ListIterator<T> listIterator() {
		synchronized (this.listSyncObj) {
			return new ArrayList<>(this.list).listIterator();
		}
	}

	@Override
	public ListIterator<T> listIterator(int index) {
		synchronized (this.listSyncObj) {
			return new ArrayList<>(this.list).listIterator(index);
		}
	}

	@Override
	public List<T> subList(int fromIndex, int toIndex) {
		synchronized (this.listSyncObj) {
			return new ArrayList<>(this.list.subList(fromIndex, toIndex));
		}
	}

	@Override
	public void replaceAll(UnaryOperator<T> operator) {
		synchronized (this.listSyncObj) {
			List<ReplacementElement<T>> replacements = new ArrayList<>();
			for (int i = 0; i < this.list.size(); i++) {
				T old = this.list.get(i);
				T newValue = operator.apply(old);
				if (!Objects.equals(old, newValue)) {
					this.list.set(i, newValue);
					replacements.add(new ReplacementElement<>(i, newValue));
				}
			}
			if (!replacements.isEmpty()) {
				this.notifyObservers(new ListReplacements<>(replacements));
			}
		}
	}

	@Override
	public void sort(Comparator<? super T> c) {
		synchronized (this.listSyncObj) {
			this.list.sort(c);
			this.notifyObservers(new ListSort<>(new ArrayList<>(this.list)));
		}
	}

	@Override
	public boolean removeIf(Predicate<? super T> filter) {
		synchronized (this.listSyncObj) {
			List<RemovalElement<T>> removals = new ArrayList<>();
			for (int i = this.list.size() - 1; i >= 0; i--) {
				T item = this.list.get(i);
				if (filter.test(item)) {
					removals.add(new RemovalElement<>(i, item));
				}
			}
			boolean result = this.list.removeIf(filter);
			if (result && !removals.isEmpty()) {
				Collections.reverse(removals);
				this.notifyObservers(new ListRemovals<>(removals));
			}
			return result;
		}
	}

	@Override
	public Stream<T> stream() {
		synchronized (this.listSyncObj) {
			return new ArrayList<>(this.list).stream();
		}
	}

	@Override
	public Stream<T> parallelStream() {
		synchronized (this.listSyncObj) {
			return new ArrayList<>(this.list).parallelStream();
		}
	}

	@Override
	public void forEach(Consumer<? super T> action) {
		synchronized (this.listSyncObj) {
			this.list.forEach(action);
		}
	}

	@Override
	public Spliterator<T> spliterator() {
		synchronized (this.listSyncObj) {
			return new ArrayList<>(this.list).spliterator();
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof List)) return false;
		synchronized (this.listSyncObj) {
			return this.list.equals(o);
		}
	}

	@Override
	public int hashCode() {
		synchronized (this.listSyncObj) {
			return this.list.hashCode();
		}
	}

	@Override
	public String toString() {
		synchronized (this.listSyncObj) {
			return this.list.toString();
		}
	}
}
