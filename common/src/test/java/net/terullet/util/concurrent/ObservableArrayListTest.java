package net.terullet.util.concurrent;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

class ObservableArrayListTest {

	private ObservableArrayList<String> list;
	private List<ObservableList.ListChange<String>> capturedChanges;
	private ObservableList.Observer<String> observer;

	@BeforeEach
	void setUp() {
		list = new ObservableArrayList<>();
		capturedChanges = new ArrayList<>();
		observer = capturedChanges::add;
	}

	@Test
	void constructor_defaultConstructor_createsEmptyList() {
		assertThat(list).isEmpty();
		assertThat(list.size()).isZero();
	}

	@Test
	void constructor_withCollection_copiesElements() {
		List<String> source = List.of("a", "b", "c");
		ObservableArrayList<String> newList = new ObservableArrayList<>(source);
		
		assertThat(newList).containsExactly("a", "b", "c");
		assertThat(newList.size()).isEqualTo(3);
	}

	@Test
	void constructor_withEmptyCollection_createsEmptyList() {
		List<String> source = Collections.emptyList();
		ObservableArrayList<String> newList = new ObservableArrayList<>(source);
		
		assertThat(newList).isEmpty();
		assertThat(newList.size()).isZero();
	}

	@Test
	void constructor_withCapacity_createsEmptyListWithCapacity() {
		ObservableArrayList<String> newList = new ObservableArrayList<>(100);
		
		assertThat(newList).isEmpty();
		assertThat(newList.size()).isZero();
	}

	@Test
	void addListener_validObserver_returnsTrue() {
		boolean result = list.addListener(observer);
		assertThat(result).isTrue();
	}

	@Test
	void addListener_sameObserverTwice_returnsFalseSecondTime() {
		list.addListener(observer);
		boolean result = list.addListener(observer);
		assertThat(result).isFalse();
	}

	@Test
	void addListener_multipleObservers_allReturnTrue() {
		ObservableList.Observer<String> observer1 = change -> {};
		ObservableList.Observer<String> observer2 = change -> {};
		ObservableList.Observer<String> observer3 = change -> {};
		
		assertThat(list.addListener(observer1)).isTrue();
		assertThat(list.addListener(observer2)).isTrue();
		assertThat(list.addListener(observer3)).isTrue();
	}

	@Test
	void addListener_nullObserver_handledGracefully() {
		assertThatCode(() -> list.addListener(null)).doesNotThrowAnyException();
		assertThatCode(() -> list.add("test")).doesNotThrowAnyException();
	}

	@Test
	void removeListener_existingObserver_returnsTrue() {
		list.addListener(observer);
		boolean result = list.removeListener(observer);
		assertThat(result).isTrue();
	}

	@Test
	void removeListener_nonExistingObserver_returnsFalse() {
		boolean result = list.removeListener(observer);
		assertThat(result).isFalse();
	}

	@Test
	void removeListener_nullObserver_returnsFalse() {
		boolean result = list.removeListener(null);
		assertThat(result).isFalse();
	}

	@Test
	void add_singleElement_notifiesInsertion() {
		list.addListener(observer);
		
		list.add("test");
		
		assertThat(list).containsExactly("test");
		assertThat(capturedChanges).hasSize(1);
		assertThat(capturedChanges.get(0)).isInstanceOf(ObservableList.ListInsertions.class);
		
		ObservableList.ListInsertions<String> insertion = (ObservableList.ListInsertions<String>) capturedChanges.get(0);
		assertThat(insertion.getInsertions()).hasSize(1);
		assertThat(insertion.getInsertions().get(0).getIndex()).isZero();
		assertThat(insertion.getInsertions().get(0).getItem()).isEqualTo("test");
	}

	@Test
	void add_multipleElements_notifiesEachInsertion() {
		list.addListener(observer);
		
		list.add("first");
		list.add("second");
		
		assertThat(list).containsExactly("first", "second");
		assertThat(capturedChanges).hasSize(2);
		assertThat(capturedChanges.get(0)).isInstanceOf(ObservableList.ListInsertions.class);
		assertThat(capturedChanges.get(1)).isInstanceOf(ObservableList.ListInsertions.class);
	}

	@Test
	void addAtIndex_insertsAtCorrectPosition_notifiesInsertion() {
		list.addAll(List.of("a", "c"));
		list.addListener(observer);
		
		list.add(1, "b");
		
		assertThat(list).containsExactly("a", "b", "c");
		assertThat(capturedChanges).hasSize(1);
		assertThat(capturedChanges.get(0)).isInstanceOf(ObservableList.ListInsertions.class);
		
		ObservableList.ListInsertions<String> insertion = (ObservableList.ListInsertions<String>) capturedChanges.get(0);
		assertThat(insertion.getInsertions().get(0).getIndex()).isEqualTo(1);
		assertThat(insertion.getInsertions().get(0).getItem()).isEqualTo("b");
	}

	@Test
	void addAtIndex_atBeginning_insertsCorrectly() {
		list.addAll(List.of("b", "c"));
		list.addListener(observer);
		
		list.add(0, "a");
		
		assertThat(list).containsExactly("a", "b", "c");
		assertThat(capturedChanges).hasSize(1);
	}

	@Test
	void addAtIndex_atEnd_insertsCorrectly() {
		list.addAll(List.of("a", "b"));
		list.addListener(observer);
		
		list.add(2, "c");
		
		assertThat(list).containsExactly("a", "b", "c");
		assertThat(capturedChanges).hasSize(1);
	}

	@Test
	void addAll_multipleElements_notifiesBatchInsertion() {
		list.addListener(observer);
		
		list.addAll(List.of("a", "b", "c"));
		
		assertThat(list).containsExactly("a", "b", "c");
		assertThat(capturedChanges).hasSize(1);
		assertThat(capturedChanges.get(0)).isInstanceOf(ObservableList.ListInsertions.class);
		
		ObservableList.ListInsertions<String> insertion = (ObservableList.ListInsertions<String>) capturedChanges.get(0);
		assertThat(insertion.getInsertions()).hasSize(3);
		assertThat(insertion.getInsertions().get(0).getIndex()).isZero();
		assertThat(insertion.getInsertions().get(1).getIndex()).isEqualTo(1);
		assertThat(insertion.getInsertions().get(2).getIndex()).isEqualTo(2);
	}

	@Test
	void addAll_emptyCollection_doesNotNotify() {
		list.addListener(observer);
		
		list.addAll(Collections.emptyList());
		
		assertThat(capturedChanges).isEmpty();
	}

	@Test
	void addAll_atIndex_insertsAtCorrectPosition() {
		list.addAll(List.of("a", "d"));
		list.addListener(observer);
		
		list.addAll(1, List.of("b", "c"));
		
		assertThat(list).containsExactly("a", "b", "c", "d");
		assertThat(capturedChanges).hasSize(1);
		assertThat(capturedChanges.get(0)).isInstanceOf(ObservableList.ListInsertions.class);
		
		ObservableList.ListInsertions<String> insertion = (ObservableList.ListInsertions<String>) capturedChanges.get(0);
		assertThat(insertion.getInsertions()).hasSize(2);
		assertThat(insertion.getInsertions().get(0).getIndex()).isEqualTo(1);
		assertThat(insertion.getInsertions().get(1).getIndex()).isEqualTo(2);
	}

	@Test
	void addAll_atIndex_emptyCollection_doesNotNotify() {
		list.addAll(List.of("a", "b"));
		list.addListener(observer);
		
		list.addAll(1, Collections.emptyList());
		
		assertThat(capturedChanges).isEmpty();
	}

	@Test
	void remove_byObject_notifiesRemoval() {
		list.addAll(List.of("a", "b", "c"));
		list.addListener(observer);
		
		boolean result = list.remove("b");
		
		assertThat(result).isTrue();
		assertThat(list).containsExactly("a", "c");
		assertThat(capturedChanges).hasSize(1);
		assertThat(capturedChanges.get(0)).isInstanceOf(ObservableList.ListRemovals.class);
		
		ObservableList.ListRemovals<String> removal = (ObservableList.ListRemovals<String>) capturedChanges.get(0);
		assertThat(removal.getRemovals()).hasSize(1);
		assertThat(removal.getRemovals().get(0).getIndex()).isEqualTo(1);
		assertThat(removal.getRemovals().get(0).getItem()).isEqualTo("b");
	}

	@Test
	void remove_byObject_nonExistingElement_returnsFalse() {
		list.addAll(List.of("a", "b", "c"));
		list.addListener(observer);
		
		boolean result = list.remove("x");
		
		assertThat(result).isFalse();
		assertThat(list).containsExactly("a", "b", "c");
		assertThat(capturedChanges).isEmpty();
	}

	@Test
	void remove_byIndex_notifiesRemoval() {
		list.addAll(List.of("a", "b", "c"));
		list.addListener(observer);
		
		String removed = list.remove(1);
		
		assertThat(removed).isEqualTo("b");
		assertThat(list).containsExactly("a", "c");
		assertThat(capturedChanges).hasSize(1);
		assertThat(capturedChanges.get(0)).isInstanceOf(ObservableList.ListRemovals.class);
	}

	@Test
	void remove_firstElement_removesCorrectly() {
		list.addAll(List.of("a", "b", "c"));
		list.addListener(observer);
		
		String removed = list.remove(0);
		
		assertThat(removed).isEqualTo("a");
		assertThat(list).containsExactly("b", "c");
	}

	@Test
	void remove_lastElement_removesCorrectly() {
		list.addAll(List.of("a", "b", "c"));
		list.addListener(observer);
		
		String removed = list.remove(2);
		
		assertThat(removed).isEqualTo("c");
		assertThat(list).containsExactly("a", "b");
	}

	@Test
	void set_replacesElement_notifiesReplacement() {
		list.addAll(List.of("a", "b", "c"));
		list.addListener(observer);
		
		String old = list.set(1, "x");
		
		assertThat(old).isEqualTo("b");
		assertThat(list).containsExactly("a", "x", "c");
		assertThat(capturedChanges).hasSize(1);
		assertThat(capturedChanges.get(0)).isInstanceOf(ObservableList.ListReplacements.class);
		
		ObservableList.ListReplacements<String> replacement = (ObservableList.ListReplacements<String>) capturedChanges.get(0);
		assertThat(replacement.getReplacements()).hasSize(1);
		assertThat(replacement.getReplacements().get(0).getIndex()).isEqualTo(1);
		assertThat(replacement.getReplacements().get(0).getItem()).isEqualTo("x");
	}

	@Test
	void set_sameValue_doesNotNotify() {
		list.add("test");
		list.addListener(observer);
		
		list.set(0, "test");
		
		assertThat(capturedChanges).isEmpty();
	}

	@Test
	void set_nullValue_notifiesCorrectly() {
		list.add("test");
		list.addListener(observer);
		
		String old = list.set(0, null);
		
		assertThat(old).isEqualTo("test");
		assertThat(list.get(0)).isNull();
		assertThat(capturedChanges).hasSize(1);
	}

	@Test
	void clear_nonEmptyList_notifiesClearing() {
		list.addAll(List.of("a", "b", "c"));
		list.addListener(observer);
		
		list.clear();
		
		assertThat(list).isEmpty();
		assertThat(capturedChanges).hasSize(1);
		assertThat(capturedChanges.get(0)).isInstanceOf(ObservableList.ListClearing.class);
	}

	@Test
	void clear_emptyList_doesNotNotify() {
		list.addListener(observer);
		
		list.clear();
		
		assertThat(capturedChanges).isEmpty();
	}

	@Test
	void removeAll_multipleElements_notifiesRemovals() {
		list.addAll(List.of("a", "b", "c", "d"));
		list.addListener(observer);
		
		boolean result = list.removeAll(List.of("b", "d"));
		
		assertThat(result).isTrue();
		assertThat(list).containsExactly("a", "c");
		assertThat(capturedChanges).hasSize(1);
		assertThat(capturedChanges.get(0)).isInstanceOf(ObservableList.ListRemovals.class);
		
		ObservableList.ListRemovals<String> removal = (ObservableList.ListRemovals<String>) capturedChanges.get(0);
		assertThat(removal.getRemovals()).hasSize(2);
	}

	@Test
	void removeAll_noElementsToRemove_returnsFalse() {
		list.addAll(List.of("a", "b", "c"));
		list.addListener(observer);
		
		boolean result = list.removeAll(List.of("x", "y"));
		
		assertThat(result).isFalse();
		assertThat(list).containsExactly("a", "b", "c");
		assertThat(capturedChanges).isEmpty();
	}

	@Test
	void removeAll_emptyCollection_returnsFalse() {
		list.addAll(List.of("a", "b", "c"));
		list.addListener(observer);
		
		boolean result = list.removeAll(Collections.emptyList());
		
		assertThat(result).isFalse();
		assertThat(capturedChanges).isEmpty();
	}

	@Test
	void retainAll_keepsSpecifiedElements_notifiesRemovals() {
		list.addAll(List.of("a", "b", "c", "d"));
		list.addListener(observer);
		
		boolean result = list.retainAll(List.of("a", "c"));
		
		assertThat(result).isTrue();
		assertThat(list).containsExactly("a", "c");
		assertThat(capturedChanges).hasSize(1);
		assertThat(capturedChanges.get(0)).isInstanceOf(ObservableList.ListRemovals.class);
	}

	@Test
	void retainAll_allElementsRetained_returnsFalse() {
		list.addAll(List.of("a", "b", "c"));
		list.addListener(observer);
		
		boolean result = list.retainAll(List.of("a", "b", "c", "d"));
		
		assertThat(result).isFalse();
		assertThat(list).containsExactly("a", "b", "c");
		assertThat(capturedChanges).isEmpty();
	}

	@Test
	void sort_reordersElements_notifiesSort() {
		list.addAll(List.of("c", "a", "b"));
		list.addListener(observer);
		
		list.sort(String::compareTo);
		
		assertThat(list).containsExactly("a", "b", "c");
		assertThat(capturedChanges).hasSize(1);
		assertThat(capturedChanges.get(0)).isInstanceOf(ObservableList.ListSort.class);
		
		ObservableList.ListSort<String> sort = (ObservableList.ListSort<String>) capturedChanges.get(0);
		assertThat(sort.getSorted()).containsExactly("a", "b", "c");
	}

	@Test
	void sort_alreadySorted_stillNotifies() {
		list.addAll(List.of("a", "b", "c"));
		list.addListener(observer);
		
		list.sort(String::compareTo);
		
		assertThat(list).containsExactly("a", "b", "c");
		assertThat(capturedChanges).hasSize(1);
		assertThat(capturedChanges.get(0)).isInstanceOf(ObservableList.ListSort.class);
	}

	@Test
	void sort_emptyList_stillNotifies() {
		list.addListener(observer);
		
		list.sort(String::compareTo);
		
		assertThat(list).isEmpty();
		assertThat(capturedChanges).hasSize(1);
		assertThat(capturedChanges.get(0)).isInstanceOf(ObservableList.ListSort.class);
	}

	@Test
	void replaceAll_transformsElements_notifiesReplacements() {
		list.addAll(List.of("a", "b", "c"));
		list.addListener(observer);
		
		list.replaceAll(String::toUpperCase);
		
		assertThat(list).containsExactly("A", "B", "C");
		assertThat(capturedChanges).hasSize(1);
		assertThat(capturedChanges.get(0)).isInstanceOf(ObservableList.ListReplacements.class);
		
		ObservableList.ListReplacements<String> replacement = (ObservableList.ListReplacements<String>) capturedChanges.get(0);
		assertThat(replacement.getReplacements()).hasSize(3);
	}

	@Test
	void replaceAll_noChanges_doesNotNotify() {
		list.addAll(List.of("a", "b", "c"));
		list.addListener(observer);
		
		list.replaceAll(s -> s);
		
		assertThat(list).containsExactly("a", "b", "c");
		assertThat(capturedChanges).isEmpty();
	}

	@Test
	void replaceAll_partialChanges_notifiesOnlyChangedElements() {
		list.addAll(List.of("a", "B", "c"));
		list.addListener(observer);
		
		list.replaceAll(s -> s.toUpperCase());
		
		assertThat(list).containsExactly("A", "B", "C");
		assertThat(capturedChanges).hasSize(1);
		assertThat(capturedChanges.get(0)).isInstanceOf(ObservableList.ListReplacements.class);
		
		ObservableList.ListReplacements<String> replacement = (ObservableList.ListReplacements<String>) capturedChanges.get(0);
		assertThat(replacement.getReplacements()).hasSize(2);
	}

	@Test
	void removeIf_removesMatchingElements_notifiesRemovals() {
		list.addAll(List.of("apple", "banana", "apricot", "cherry"));
		list.addListener(observer);
		
		boolean result = list.removeIf(s -> s.startsWith("a"));
		
		assertThat(result).isTrue();
		assertThat(list).containsExactly("banana", "cherry");
		assertThat(capturedChanges).hasSize(1);
		assertThat(capturedChanges.get(0)).isInstanceOf(ObservableList.ListRemovals.class);
	}

	@Test
	void removeIf_noMatchingElements_returnsFalse() {
		list.addAll(List.of("banana", "cherry"));
		list.addListener(observer);
		
		boolean result = list.removeIf(s -> s.startsWith("a"));
		
		assertThat(result).isFalse();
		assertThat(list).containsExactly("banana", "cherry");
		assertThat(capturedChanges).isEmpty();
	}

	@Test
	void removeIf_allElementsMatch_removesAll() {
		list.addAll(List.of("apple", "apricot", "avocado"));
		list.addListener(observer);
		
		boolean result = list.removeIf(s -> s.startsWith("a"));
		
		assertThat(result).isTrue();
		assertThat(list).isEmpty();
		assertThat(capturedChanges).hasSize(1);
		assertThat(capturedChanges.get(0)).isInstanceOf(ObservableList.ListRemovals.class);
	}

	@ParameterizedTest
	@ValueSource(ints = {0, 1, 5, 10, 100})
	void size_variousSizes_returnsCorrectSize(int size) {
		for (int i = 0; i < size; i++) {
			list.add("item" + i);
		}
		assertThat(list.size()).isEqualTo(size);
	}

	@Test
	void isEmpty_emptyList_returnsTrue() {
		assertThat(list.isEmpty()).isTrue();
	}

	@Test
	void isEmpty_nonEmptyList_returnsFalse() {
		list.add("test");
		assertThat(list.isEmpty()).isFalse();
	}

	@Test
	void contains_existingElement_returnsTrue() {
		list.addAll(List.of("a", "b", "c"));
		assertThat(list.contains("b")).isTrue();
	}

	@Test
	void contains_nonExistingElement_returnsFalse() {
		list.addAll(List.of("a", "b", "c"));
		assertThat(list.contains("x")).isFalse();
	}

	@Test
	void contains_nullElement_handledCorrectly() {
		list.add(null);
		assertThat(list.contains(null)).isTrue();
	}

	@Test
	void containsAll_allPresent_returnsTrue() {
		list.addAll(List.of("a", "b", "c", "d"));
		assertThat(list.containsAll(List.of("b", "d"))).isTrue();
	}

	@Test
	void containsAll_someAbsent_returnsFalse() {
		list.addAll(List.of("a", "b", "c"));
		assertThat(list.containsAll(List.of("b", "x"))).isFalse();
	}

	@Test
	void containsAll_emptyCollection_returnsTrue() {
		list.addAll(List.of("a", "b", "c"));
		assertThat(list.containsAll(Collections.emptyList())).isTrue();
	}

	@Test
	void get_validIndex_returnsElement() {
		list.addAll(List.of("a", "b", "c"));
		assertThat(list.get(1)).isEqualTo("b");
	}

	@Test
	void get_invalidIndex_throwsException() {
		list.addAll(List.of("a", "b", "c"));
		assertThatThrownBy(() -> list.get(5)).isInstanceOf(IndexOutOfBoundsException.class);
	}

	@Test
	void indexOf_existingElement_returnsCorrectIndex() {
		list.addAll(List.of("a", "b", "c", "b"));
		assertThat(list.indexOf("b")).isEqualTo(1);
	}

	@Test
	void indexOf_nonExistingElement_returnsMinusOne() {
		list.addAll(List.of("a", "b", "c"));
		assertThat(list.indexOf("x")).isEqualTo(-1);
	}

	@Test
	void lastIndexOf_existingElement_returnsLastIndex() {
		list.addAll(List.of("a", "b", "c", "b"));
		assertThat(list.lastIndexOf("b")).isEqualTo(3);
	}

	@Test
	void lastIndexOf_nonExistingElement_returnsMinusOne() {
		list.addAll(List.of("a", "b", "c"));
		assertThat(list.lastIndexOf("x")).isEqualTo(-1);
	}

	@Test
	void iterator_concurrent_returnsSnapshot() {
		list.addAll(List.of("a", "b", "c"));
		
		Iterator<String> iterator = list.iterator();
		list.add("d");
		
		List<String> iteratorItems = new ArrayList<>();
		iterator.forEachRemaining(iteratorItems::add);
		
		assertThat(iteratorItems).containsExactly("a", "b", "c");
		assertThat(list).containsExactly("a", "b", "c", "d");
	}

	@Test
	void listIterator_concurrent_returnsSnapshot() {
		list.addAll(List.of("a", "b", "c"));
		
		ListIterator<String> listIterator = list.listIterator();
		list.add("d");
		
		List<String> iteratorItems = new ArrayList<>();
		listIterator.forEachRemaining(iteratorItems::add);
		
		assertThat(iteratorItems).containsExactly("a", "b", "c");
		assertThat(list).containsExactly("a", "b", "c", "d");
	}

	@Test
	void listIterator_withIndex_startsAtCorrectPosition() {
		list.addAll(List.of("a", "b", "c", "d"));
		
		ListIterator<String> listIterator = list.listIterator(2);
		
		assertThat(listIterator.hasNext()).isTrue();
		assertThat(listIterator.next()).isEqualTo("c");
	}

	@Test
	void subList_returnsSnapshot() {
		list.addAll(List.of("a", "b", "c", "d"));
		
		List<String> subList = list.subList(1, 3);
		list.set(1, "x");
		
		assertThat(subList).containsExactly("b", "c");
		assertThat(list.get(1)).isEqualTo("x");
	}

	@Test
	void toArray_returnsCorrectArray() {
		list.addAll(List.of("a", "b", "c"));
		
		Object[] array = list.toArray();
		
		assertThat(array).containsExactly("a", "b", "c");
	}

	@Test
	void toArray_withTypedArray_returnsTypedArray() {
		list.addAll(List.of("a", "b", "c"));
		
		String[] array = list.toArray(new String[0]);
		
		assertThat(array).containsExactly("a", "b", "c");
	}

	@Test
	void stream_concurrent_returnsSnapshot() {
		list.addAll(List.of("a", "b", "c"));
		
		List<String> streamItems = list.stream().map(String::toUpperCase).collect(Collectors.toList());
		list.add("d");
		
		assertThat(streamItems).containsExactly("A", "B", "C");
		assertThat(list).containsExactly("a", "b", "c", "d");
	}

	@Test
	void parallelStream_concurrent_returnsSnapshot() {
		list.addAll(List.of("a", "b", "c"));
		
		List<String> streamItems = list.parallelStream().map(String::toUpperCase).collect(Collectors.toList());
		list.add("d");
		
		assertThat(streamItems).containsExactlyInAnyOrder("A", "B", "C");
		assertThat(list).containsExactly("a", "b", "c", "d");
	}

	@Test
	void forEach_iteratesOverAllElements() {
		list.addAll(List.of("a", "b", "c"));
		
		List<String> visited = new ArrayList<>();
		list.forEach(visited::add);
		
		assertThat(visited).containsExactly("a", "b", "c");
	}

	@Test
	void spliterator_concurrent_returnsSnapshot() {
		list.addAll(List.of("a", "b", "c"));
		
		Spliterator<String> spliterator = list.spliterator();
		list.add("d");
		
		List<String> spliteratorItems = new ArrayList<>();
		spliterator.forEachRemaining(spliteratorItems::add);
		
		assertThat(spliteratorItems).containsExactly("a", "b", "c");
		assertThat(list).containsExactly("a", "b", "c", "d");
	}

	@Test
	void equals_sameContent_returnsTrue() {
		list.addAll(List.of("a", "b", "c"));
		List<String> other = List.of("a", "b", "c");
		
		assertThat(list).isEqualTo(other);
		assertThat(other).isEqualTo(list);
	}

	@Test
	void equals_differentContent_returnsFalse() {
		list.addAll(List.of("a", "b", "c"));
		List<String> other = List.of("a", "b", "x");
		
		assertThat(list).isNotEqualTo(other);
	}

	@Test
	void equals_differentSizes_returnsFalse() {
		list.addAll(List.of("a", "b", "c"));
		List<String> other = List.of("a", "b");
		
		assertThat(list).isNotEqualTo(other);
	}

	@Test
	void equals_nonListObject_returnsFalse() {
		list.addAll(List.of("a", "b", "c"));
		Set<String> other = Set.of("a", "b", "c");
		
		assertThat(list).isNotEqualTo(other);
	}

	@Test
	void hashCode_consistent() {
		list.addAll(List.of("a", "b", "c"));
		
		int hash1 = list.hashCode();
		int hash2 = list.hashCode();
		
		assertThat(hash1).isEqualTo(hash2);
	}

	@Test
	void hashCode_sameContent_sameHash() {
		list.addAll(List.of("a", "b", "c"));
		List<String> other = new ArrayList<>(List.of("a", "b", "c"));
		
		assertThat(list.hashCode()).isEqualTo(other.hashCode());
	}

	@Test
	void toString_returnsListString() {
		list.addAll(List.of("a", "b", "c"));
		
		String result = list.toString();
		
		assertThat(result).isEqualTo("[a, b, c]");
	}

	@Test
	void toString_emptyList_returnsEmptyListString() {
		String result = list.toString();
		assertThat(result).isEqualTo("[]");
	}

	@Test
	void multipleObservers_allNotifiedOfChanges() {
		List<ObservableList.ListChange<String>> observer1Changes = new ArrayList<>();
		List<ObservableList.ListChange<String>> observer2Changes = new ArrayList<>();
		
		list.addListener(observer1Changes::add);
		list.addListener(observer2Changes::add);
		
		list.add("test");
		
		assertThat(observer1Changes).hasSize(1);
		assertThat(observer2Changes).hasSize(1);
		assertThat(observer1Changes.get(0)).isInstanceOf(ObservableList.ListInsertions.class);
		assertThat(observer2Changes.get(0)).isInstanceOf(ObservableList.ListInsertions.class);
	}

	@Test
	void removedObserver_doesNotReceiveNotifications() {
		List<ObservableList.ListChange<String>> removedObserverChanges = new ArrayList<>();
		ObservableList.Observer<String> removedObserver = removedObserverChanges::add;
		
		list.addListener(observer);
		list.addListener(removedObserver);
		
		list.add("test1");
		list.removeListener(removedObserver);
		list.add("test2");
		
		assertThat(capturedChanges).hasSize(2);
		assertThat(removedObserverChanges).hasSize(1);
	}

	@Test
	void observerException_continuesToNotifyOtherObservers() {
		List<ObservableList.ListChange<String>> normalObserverChanges = new ArrayList<>();
		
		list.addListener(change -> { throw new RuntimeException("Observer exception"); });
		list.addListener(normalObserverChanges::add);
		
		assertThatCode(() -> list.add("test")).doesNotThrowAnyException();
		
		assertThat(normalObserverChanges).hasSize(1);
	}

	@Test
	@Timeout(5)
	void threadSafety_concurrentModifications_maintainsConsistency() throws InterruptedException {
		final int numThreads = 10;
		final int operationsPerThread = 100;
		final CountDownLatch startLatch = new CountDownLatch(1);
		final CountDownLatch endLatch = new CountDownLatch(numThreads);
		final AtomicInteger changeCount = new AtomicInteger(0);
		
		list.addListener(change -> changeCount.incrementAndGet());
		
		ExecutorService executor = Executors.newFixedThreadPool(numThreads);
		
		for (int i = 0; i < numThreads; i++) {
			final int threadId = i;
			executor.submit(() -> {
				try {
					startLatch.await();
					for (int j = 0; j < operationsPerThread; j++) {
						String value = "thread" + threadId + "-item" + j;
						list.add(value);
						if (j % 10 == 0 && !list.isEmpty()) {
							list.remove(0);
						}
					}
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				} finally {
					endLatch.countDown();
				}
			});
		}
		
		startLatch.countDown();
		endLatch.await();
		executor.shutdown();
		
		assertThat(changeCount.get()).isGreaterThan(0);
		int expectedSize = numThreads * operationsPerThread - (numThreads * operationsPerThread / 10);
		assertThat(list.size()).isEqualTo(expectedSize);
	}

	@Test
	@Timeout(5)
	void threadSafety_concurrentObserverModifications_noDeadlock() throws InterruptedException {
		final int numObservers = 5;
		final CountDownLatch startLatch = new CountDownLatch(1);
		final CountDownLatch endLatch = new CountDownLatch(numObservers * 2);
		final AtomicReference<Exception> exceptionRef = new AtomicReference<>();
		
		ExecutorService executor = Executors.newFixedThreadPool(numObservers * 2);
		
		List<ObservableList.Observer<String>> observers = new ArrayList<>();
		for (int i = 0; i < numObservers; i++) {
			observers.add(change -> {});
		}
		
		for (int i = 0; i < numObservers; i++) {
			final ObservableList.Observer<String> obs = observers.get(i);
			
			executor.submit(() -> {
				try {
					startLatch.await();
					for (int j = 0; j < 100; j++) {
						list.addListener(obs);
						Thread.yield();
					}
				} catch (Exception e) {
					exceptionRef.set(e);
				} finally {
					endLatch.countDown();
				}
			});
			
			executor.submit(() -> {
				try {
					startLatch.await();
					for (int j = 0; j < 100; j++) {
						list.removeListener(obs);
						Thread.yield();
					}
				} catch (Exception e) {
					exceptionRef.set(e);
				} finally {
					endLatch.countDown();
				}
			});
		}
		
		executor.submit(() -> {
			try {
				startLatch.await();
				for (int i = 0; i < 50; i++) {
					list.add("item" + i);
					Thread.yield();
				}
			} catch (Exception e) {
				exceptionRef.set(e);
			}
		});
		
		startLatch.countDown();
		endLatch.await();
		executor.shutdown();
		
		assertThat(exceptionRef.get()).isNull();
	}

	@Test
	@Timeout(5)
	void threadSafety_concurrentReadOperations_consistent() throws InterruptedException {
		list.addAll(List.of("a", "b", "c"));
		
		final int numThreads = 10;
		final int operationsPerThread = 1000;
		final CountDownLatch startLatch = new CountDownLatch(1);
		final CountDownLatch endLatch = new CountDownLatch(numThreads);
		final AtomicInteger successCount = new AtomicInteger(0);
		
		ExecutorService executor = Executors.newFixedThreadPool(numThreads);
		
		for (int i = 0; i < numThreads; i++) {
			executor.submit(() -> {
				try {
					startLatch.await();
					for (int j = 0; j < operationsPerThread; j++) {
						int size = list.size();
						if (size >= 3) {
							String value = list.get(0);
							if (value != null) {
								successCount.incrementAndGet();
							}
						}
					}
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				} finally {
					endLatch.countDown();
				}
			});
		}
		
		startLatch.countDown();
		endLatch.await();
		executor.shutdown();
		
		assertThat(successCount.get()).isGreaterThan(0);
	}

	@Test
	void listChangeElements_correctConstruction() {
		ObservableList.InsertionElement<String> insertion = new ObservableList.InsertionElement<>(5, "test");
		assertThat(insertion.getIndex()).isEqualTo(5);
		assertThat(insertion.getItem()).isEqualTo("test");
		
		ObservableList.RemovalElement<String> removal = new ObservableList.RemovalElement<>(3, "removed");
		assertThat(removal.getIndex()).isEqualTo(3);
		assertThat(removal.getItem()).isEqualTo("removed");
		
		ObservableList.ReplacementElement<String> replacement = new ObservableList.ReplacementElement<>(1, "new");
		assertThat(replacement.getIndex()).isEqualTo(1);
		assertThat(replacement.getItem()).isEqualTo("new");
	}

	@Test
	void listChangeTypes_correctConstruction() {
		List<ObservableList.InsertionElement<String>> insertions = List.of(
			new ObservableList.InsertionElement<>(0, "a"),
			new ObservableList.InsertionElement<>(1, "b")
		);
		ObservableList.ListInsertions<String> insertionsChange = new ObservableList.ListInsertions<>(insertions);
		assertThat(insertionsChange.getInsertions()).hasSize(2);
		assertThat(insertionsChange.getInsertions()).isUnmodifiable();
		
		List<ObservableList.RemovalElement<String>> removals = List.of(
			new ObservableList.RemovalElement<>(0, "a")
		);
		ObservableList.ListRemovals<String> removalsChange = new ObservableList.ListRemovals<>(removals);
		assertThat(removalsChange.getRemovals()).hasSize(1);
		assertThat(removalsChange.getRemovals()).isUnmodifiable();
		
		List<ObservableList.ReplacementElement<String>> replacements = List.of(
			new ObservableList.ReplacementElement<>(0, "new")
		);
		ObservableList.ListReplacements<String> replacementsChange = new ObservableList.ListReplacements<>(replacements);
		assertThat(replacementsChange.getReplacements()).hasSize(1);
		assertThat(replacementsChange.getReplacements()).isUnmodifiable();
		
		ObservableList.ListClearing<String> clearing = new ObservableList.ListClearing<>();
		assertThat(clearing).isNotNull();
		
		List<String> sorted = List.of("a", "b", "c");
		ObservableList.ListSort<String> sort = new ObservableList.ListSort<>(sorted);
		assertThat(sort.getSorted()).containsExactly("a", "b", "c");
		assertThat(sort.getSorted()).isUnmodifiable();
	}

	@Test
	void batchOperations_correctNotificationOrder() {
		list.addListener(observer);
		
		list.addAll(List.of("1", "2", "3"));
		list.removeAll(List.of("2"));
		list.replaceAll(s -> "x" + s);
		list.sort(String::compareTo);
		
		assertThat(capturedChanges).hasSize(4);
		assertThat(capturedChanges.get(0)).isInstanceOf(ObservableList.ListInsertions.class);
		assertThat(capturedChanges.get(1)).isInstanceOf(ObservableList.ListRemovals.class);
		assertThat(capturedChanges.get(2)).isInstanceOf(ObservableList.ListReplacements.class);
		assertThat(capturedChanges.get(3)).isInstanceOf(ObservableList.ListSort.class);
	}
}