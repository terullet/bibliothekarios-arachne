package net.terullet.bibliothekarios.arachne.core.presentation;

import java.util.Collections;
import java.util.List;

public interface ObservableList<T> extends List<T> {
	boolean addListener(Observer<T> observer);
	boolean removeListener(Observer<T> observer);
	final class InsertionElement<T> {
		private final int index;
		public int getIndex() {
			return this.index;
		}
		private final T item;
		public T getItem() {
			return this.item;
		}
		InsertionElement(int index, T item) {
			this.index = index;
			this.item = item;
		}
	}
	final class RemovalElement<T> {
		private final int index;
		public int getIndex() {
			return this.index;
		}
		private final T item;
		public T getItem() {
			return this.item;
		}
		RemovalElement(int index, T item) {
			this.index = index;
			this.item = item;
		}
	}
	final class ReplacementElement<T> {
		private final int index;
		public int getIndex() {
			return this.index;
		}
		private final T item;
		public T getItem() {
			return this.item;
		}
		ReplacementElement(int index, T item) {
			this.index = index;
			this.item = item;
		}
	}
	sealed interface ListChange<T> permits ListInsertions, ListRemovals, ListReplacements, ListClearing, ListSort { }
	final class ListInsertions<T> implements ListChange<T> {
		private final List<InsertionElement<T>> insertions;
		public List<InsertionElement<T>> getInsertions() {
			return this.insertions;
		}
		ListInsertions(List<InsertionElement<T>> insertions) {
			this.insertions = Collections.unmodifiableList(insertions);
		}
	}
	final class ListRemovals<T> implements ListChange<T> {
		private final List<RemovalElement<T>> removals;
		public List<RemovalElement<T>> getRemovals() {
			return this.removals;
		}
		ListRemovals(List<RemovalElement<T>> removals) {
			this.removals = Collections.unmodifiableList(removals);
		}
	}
	final class ListReplacements<T> implements ListChange<T> {
		private final List<ReplacementElement<T>> replacements;
		public List<ReplacementElement<T>> getReplacements() {
			return this.replacements;
		}
		ListReplacements(List<ReplacementElement<T>> replacements) {
			this.replacements = Collections.unmodifiableList(replacements);
		}
	}
	final class ListClearing<T> implements ListChange<T> { }
	final class ListSort<T> implements ListChange<T> {
		private final List<T> sorted;
		public List<T> getSorted() {
			return this.sorted;
		}
		ListSort(List<T> sorted) {
			this.sorted = Collections.unmodifiableList(sorted);
		}
	}
	interface Observer<T> {
		void onChanged(ListChange<T> change);
	}
}
