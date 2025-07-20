package net.terullet.util.concurrent;

import net.terullet.bibliothekarios.arachne.core.presentation.Observer;
import net.terullet.bibliothekarios.arachne.core.presentation.SimpleObservable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.*;

class SimpleObservableTest {

	private SimpleObservable<String> observable;
	private List<String> capturedValues;
	private Observer<String> observer;

	@BeforeEach
	void setUp() {
		observable = new SimpleObservable<>();
		capturedValues = new ArrayList<>();
		observer = capturedValues::add;
	}

	@Test
	void constructor_defaultConstructor_hasNullInitialValue() {
		assertThat(observable.get()).isNull();
	}

	@Test
	void constructor_withInitialValue_setsInitialValue() {
		SimpleObservable<String> obs = new SimpleObservable<>("initial");
		assertThat(obs.get()).isEqualTo("initial");
	}

	@Test
	void constructor_withNullInitialValue_setsNull() {
		SimpleObservable<String> obs = new SimpleObservable<>(null);
		assertThat(obs.get()).isNull();
	}

	@Test
	void get_afterConstruction_returnsInitialValue() {
		assertThat(observable.get()).isNull();
	}

	@Test
	void get_multipleCalls_returnsConsistentValue() {
		observable.set("test");
		assertThat(observable.get()).isEqualTo("test");
		assertThat(observable.get()).isEqualTo("test");
		assertThat(observable.get()).isEqualTo("test");
	}

	@Test
	void set_newValue_updatesValue() {
		observable.set("test");
		assertThat(observable.get()).isEqualTo("test");
	}

	@Test
	void set_nullValue_updatesValueToNull() {
		observable.set("test");
		observable.set(null);
		assertThat(observable.get()).isNull();
	}

	@Test
	void set_sameValue_doesNotNotifyObservers() {
		observable.set("test");
		observable.addListener(observer);
		observable.set("test");
		assertThat(capturedValues).isEmpty();
	}

	@Test
	void set_differentValue_notifiesObservers() {
		observable.addListener(observer);
		observable.set("test");
		assertThat(capturedValues).containsExactly("test");
		assertThat(observable.get()).isEqualTo("test");
	}

	@Test
	void set_nullToValue_notifiesObservers() {
		observable.addListener(observer);
		observable.set("test");
		assertThat(capturedValues).containsExactly("test");
	}

	@Test
	void set_valueToNull_notifiesObservers() {
		observable.set("test");
		observable.addListener(observer);
		observable.set(null);
		assertThat(capturedValues).containsExactly((String) null);
		assertThat(observable.get()).isNull();
	}

	@Test
	void set_nullToNull_doesNotNotify() {
		observable.addListener(observer);
		observable.set(null);
		assertThat(capturedValues).isEmpty();
	}

	@Test
	void set_multipleChanges_notifiesEachChange() {
		observable.addListener(observer);
		observable.set("first");
		observable.set("second");
		observable.set("third");
		assertThat(capturedValues).containsExactly("first", "second", "third");
		assertThat(observable.get()).isEqualTo("third");
	}

	@Test
	void set_sameValueMultipleTimes_onlyNotifiesFirstTime() {
		observable.addListener(observer);
		observable.set("test");
		observable.set("test");
		observable.set("test");
		assertThat(capturedValues).containsExactly("test");
	}

	@Test
	void addListener_validObserver_returnsTrue() {
		boolean result = observable.addListener(observer);
		assertThat(result).isTrue();
	}

	@Test
	void addListener_sameObserverTwice_returnsfalseSecondTime() {
		observable.addListener(observer);
		boolean result = observable.addListener(observer);
		assertThat(result).isFalse();
	}

	@Test
	void addListener_multipleObservers_allReturnTrue() {
		Observer<String> observer1 = value -> {};
		Observer<String> observer2 = value -> {};
		Observer<String> observer3 = value -> {};
		
		assertThat(observable.addListener(observer1)).isTrue();
		assertThat(observable.addListener(observer2)).isTrue();
		assertThat(observable.addListener(observer3)).isTrue();
	}

	@Test
	void addListener_nullObserver_handledBySet() {
		assertThatCode(() -> observable.addListener(null)).doesNotThrowAnyException();
		assertThatCode(() -> observable.set("test")).doesNotThrowAnyException();
	}

	@Test
	void removeListener_existingObserver_returnsTrue() {
		observable.addListener(observer);
		boolean result = observable.removeListener(observer);
		assertThat(result).isTrue();
	}

	@Test
	void removeListener_nonExistingObserver_returnsFalse() {
		boolean result = observable.removeListener(observer);
		assertThat(result).isFalse();
	}

	@Test
	void removeListener_removedObserver_doesNotReceiveNotifications() {
		observable.addListener(observer);
		observable.set("test1");
		observable.removeListener(observer);
		observable.set("test2");
		assertThat(capturedValues).containsExactly("test1");
	}

	@Test
	void removeListener_nullObserver_returnsFalse() {
		boolean result = observable.removeListener(null);
		assertThat(result).isFalse();
	}

	@Test
	void multipleObservers_allNotifiedWhenValueChanges() {
		List<String> observer1Values = new ArrayList<>();
		List<String> observer2Values = new ArrayList<>();
		List<String> observer3Values = new ArrayList<>();
		
		observable.addListener(observer1Values::add);
		observable.addListener(observer2Values::add);
		observable.addListener(observer3Values::add);
		
		observable.set("test");
		
		assertThat(observer1Values).containsExactly("test");
		assertThat(observer2Values).containsExactly("test");
		assertThat(observer3Values).containsExactly("test");
	}

	@Test
	void multipleObservers_removingOneDoesNotAffectOthers() {
		List<String> observer1Values = new ArrayList<>();
		List<String> observer2Values = new ArrayList<>();
		
		Observer<String> observer1 = observer1Values::add;
		Observer<String> observer2 = observer2Values::add;
		
		observable.addListener(observer1);
		observable.addListener(observer2);
		
		observable.set("first");
		observable.removeListener(observer1);
		observable.set("second");
		
		assertThat(observer1Values).containsExactly("first");
		assertThat(observer2Values).containsExactly("first", "second");
	}

	@Test
	void observerException_continuesToNotifyOtherObservers() {
		List<String> observer1Values = new ArrayList<>();
		List<String> observer2Values = new ArrayList<>();
		
		observable.addListener(value -> {
			observer1Values.add(value);
			throw new RuntimeException("Observer exception");
		});
		observable.addListener(observer2Values::add);
		
		assertThatCode(() -> observable.set("test")).doesNotThrowAnyException();
		
		assertThat(observer1Values).containsExactly("test");
		assertThat(observer2Values).containsExactly("test");
	}

	@Test
	void observerException_multipleExceptions_allObserversStillNotified() {
		List<String> values = new ArrayList<>();
		
		observable.addListener(value -> { throw new RuntimeException("Exception 1"); });
		observable.addListener(value -> { throw new IllegalStateException("Exception 2"); });
		observable.addListener(values::add);
		observable.addListener(value -> { throw new UnsupportedOperationException("Exception 3"); });
		
		assertThatCode(() -> observable.set("test")).doesNotThrowAnyException();
		assertThat(values).containsExactly("test");
	}

	@Test
	void observerNotifiedDuringSetOperation_canAccessNewValue() {
		AtomicReference<String> observedValue = new AtomicReference<>();
		
		observable.addListener(value -> observedValue.set(observable.get()));
		observable.set("test");
		
		assertThat(observedValue.get()).isEqualTo("test");
	}

	@Test
	void setDuringNotification_handlesNestedChanges() {
		AtomicInteger callCount = new AtomicInteger(0);
		
		observable.addListener(value -> {
			int count = callCount.incrementAndGet();
			if (count == 1 && "first".equals(value)) {
				observable.set("second");
			}
		});
		
		observable.set("first");
		
		assertThat(callCount.get()).isEqualTo(2);
		assertThat(observable.get()).isEqualTo("second");
	}

	@ParameterizedTest
	@ValueSource(strings = {"test", "hello", "world", ""})
	void set_variousStringValues_updatesCorrectly(String value) {
		observable.set(value);
		assertThat(observable.get()).isEqualTo(value);
	}

	@Test
	void integerObservable_worksCorrectly() {
		SimpleObservable<Integer> intObservable = new SimpleObservable<>();
		List<Integer> intValues = new ArrayList<>();
		intObservable.addListener(intValues::add);
		
		intObservable.set(42);
		intObservable.set(100);
		intObservable.set(42);
		
		assertThat(intValues).containsExactly(42, 100, 42);
		assertThat(intObservable.get()).isEqualTo(42);
	}

	@Test
	void customObjectObservable_worksCorrectly() {
		record Person(String name, int age) {}
		
		SimpleObservable<Person> personObservable = new SimpleObservable<>();
		List<Person> personValues = new ArrayList<>();
		personObservable.addListener(personValues::add);
		
		Person person1 = new Person("Alice", 30);
		Person person2 = new Person("Bob", 25);
		
		personObservable.set(person1);
		personObservable.set(person2);
		personObservable.set(person1);
		
		assertThat(personValues).containsExactly(person1, person2, person1);
		assertThat(personObservable.get()).isEqualTo(person1);
	}

	@Test
	void customObjectObservable_withEqualsOverride_respectsEquality() {
		class Value {
			private final String content;
			Value(String content) { this.content = content; }
			@Override
			public boolean equals(Object obj) {
				return obj instanceof Value other && content.equals(other.content);
			}
			@Override
			public int hashCode() { return content.hashCode(); }
		}
		
		SimpleObservable<Value> valueObservable = new SimpleObservable<>();
		List<Value> values = new ArrayList<>();
		valueObservable.addListener(values::add);
		
		Value value1 = new Value("test");
		Value value2 = new Value("test");
		Value value3 = new Value("different");
		
		valueObservable.set(value1);
		valueObservable.set(value2);
		valueObservable.set(value3);
		
		assertThat(values).hasSize(2);
		assertThat(values.get(0)).isEqualTo(value1);
		assertThat(values.get(1)).isEqualTo(value3);
	}

	@Test
	@Timeout(5)
	void threadSafety_concurrentGetAndSet_maintainsConsistency() throws InterruptedException {
		final int numThreads = 10;
		final int operationsPerThread = 100;
		final CountDownLatch startLatch = new CountDownLatch(1);
		final CountDownLatch endLatch = new CountDownLatch(numThreads);
		final AtomicInteger notificationCount = new AtomicInteger(0);
		
		observable.addListener(value -> notificationCount.incrementAndGet());
		
		ExecutorService executor = Executors.newFixedThreadPool(numThreads);
		
		for (int i = 0; i < numThreads; i++) {
			final int threadId = i;
			executor.submit(() -> {
				try {
					startLatch.await();
					for (int j = 0; j < operationsPerThread; j++) {
						String value = "thread" + threadId + "-value" + j;
						observable.set(value);
						String retrieved = observable.get();
						assertThat(retrieved).isNotNull();
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
		
		assertThat(notificationCount.get()).isGreaterThan(0);
		assertThat(observable.get()).isNotNull();
	}

	@Test
	@Timeout(5)
	void threadSafety_concurrentObserverModifications_noDeadlock() throws InterruptedException {
		final int numObservers = 5;
		final CountDownLatch startLatch = new CountDownLatch(1);
		final CountDownLatch endLatch = new CountDownLatch(numObservers * 2 + 1);
		final AtomicReference<Exception> exceptionRef = new AtomicReference<>();
		
		ExecutorService executor = Executors.newFixedThreadPool(numObservers * 2 + 1);
		
		List<Observer<String>> observers = new ArrayList<>();
		for (int i = 0; i < numObservers; i++) {
			observers.add(value -> {});
		}
		
		for (int i = 0; i < numObservers; i++) {
			final Observer<String> obs = observers.get(i);
			
			executor.submit(() -> {
				try {
					startLatch.await();
					for (int j = 0; j < 100; j++) {
						observable.addListener(obs);
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
						observable.removeListener(obs);
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
					observable.set("value" + i);
					Thread.yield();
				}
			} catch (Exception e) {
				exceptionRef.set(e);
			} finally {
				endLatch.countDown();
			}
		});
		
		startLatch.countDown();
		endLatch.await();
		executor.shutdown();
		
		assertThat(exceptionRef.get()).isNull();
	}

	@Test
	@Timeout(5)
	void threadSafety_concurrentGetOperations_consistent() throws InterruptedException {
		observable.set("initial");
		
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
						String value = observable.get();
						if (value != null) {
							successCount.incrementAndGet();
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
		
		assertThat(successCount.get()).isEqualTo(numThreads * operationsPerThread);
	}

	@Test
	@Timeout(5)
	void threadSafety_concurrentSetOperations_allChangesNotified() throws InterruptedException {
		final int numThreads = 5;
		final int operationsPerThread = 20;
		final CountDownLatch startLatch = new CountDownLatch(1);
		final CountDownLatch endLatch = new CountDownLatch(numThreads);
		final AtomicInteger notificationCount = new AtomicInteger(0);
		
		observable.addListener(value -> notificationCount.incrementAndGet());
		
		ExecutorService executor = Executors.newFixedThreadPool(numThreads);
		
		for (int i = 0; i < numThreads; i++) {
			final int threadId = i;
			executor.submit(() -> {
				try {
					startLatch.await();
					for (int j = 0; j < operationsPerThread; j++) {
						observable.set("thread" + threadId + "-op" + j);
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
		
		assertThat(notificationCount.get()).isEqualTo(numThreads * operationsPerThread);
	}

	@Test
	void observerExecutionDuringException_allObserversExecuted() {
		List<String> executionOrder = new ArrayList<>();
		
		observable.addListener(value -> {
			executionOrder.add("observer1");
			throw new RuntimeException("Exception in observer1");
		});
		observable.addListener(value -> executionOrder.add("observer2"));
		observable.addListener(value -> {
			executionOrder.add("observer3");
			throw new RuntimeException("Exception in observer3");
		});
		observable.addListener(value -> executionOrder.add("observer4"));
		
		observable.set("test");
		
		assertThat(executionOrder).containsExactlyInAnyOrder("observer1", "observer2", "observer3", "observer4");
	}

	@Test
	void equals_objectsWithNullValues_handledCorrectly() {
		SimpleObservable<String> other = new SimpleObservable<>();
		List<String> otherValues = new ArrayList<>();
		
		observable.addListener(observer);
		other.addListener(otherValues::add);
		
		observable.set("test");
		observable.set(null);
		other.set("test");
		other.set(null);
		
		assertThat(observable.get()).isEqualTo(other.get());
		assertThat(capturedValues).containsExactly("test", null);
		assertThat(otherValues).containsExactly("test", null);
	}

	@Test
	void synchronization_valueLockAndObserverLock_preventDataRaces() {
		AtomicReference<String> valueSeenByObserver = new AtomicReference<>();
		
		observable.addListener(value -> {
			valueSeenByObserver.set(observable.get());
		});
		
		observable.set("test");
		
		assertThat(valueSeenByObserver.get()).isEqualTo("test");
		assertThat(valueSeenByObserver.get()).isEqualTo(observable.get());
	}
}
