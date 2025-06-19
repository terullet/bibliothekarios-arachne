package net.terullet.util.concurrent;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ThreadFactory;

import static org.assertj.core.api.Assertions.assertThat;

class NamedThreadFactoryTest {

    @Test
    void shouldImplementThreadFactory() {
        // given
        NamedThreadFactory factory = new NamedThreadFactory("test");

        // then
        assertThat(factory).isInstanceOf(ThreadFactory.class);
    }

    @Test
    void newThread_shouldCreateThreadWithCorrectName() {
        // given
        NamedThreadFactory factory = new NamedThreadFactory("test-pool");
        Runnable task = () -> {};

        // when
        Thread thread = factory.newThread(task);

        // then
        assertThat(thread.getName()).isEqualTo("test-pool-thread-1");
    }

    @Test
    void newThread_shouldIncrementThreadIdForEachCall() {
        // given
        NamedThreadFactory factory = new NamedThreadFactory("worker");
        Runnable task = () -> {};

        // when
        Thread thread1 = factory.newThread(task);
        Thread thread2 = factory.newThread(task);
        Thread thread3 = factory.newThread(task);

        // then
        assertThat(thread1.getName()).isEqualTo("worker-thread-1");
        assertThat(thread2.getName()).isEqualTo("worker-thread-2");
        assertThat(thread3.getName()).isEqualTo("worker-thread-3");
    }

    @Test
    void newThread_shouldCreateThreadWithGivenRunnable() {
        // given
        NamedThreadFactory factory = new NamedThreadFactory("test");
        boolean[] executed = {false};
        Runnable task = () -> executed[0] = true;

        // when
        Thread thread = factory.newThread(task);
        thread.run();

        // then
        assertThat(executed[0]).isTrue();
    }

    @Test
    void newThread_shouldHandleEmptyPrefix() {
        // given
        NamedThreadFactory factory = new NamedThreadFactory("");
        Runnable task = () -> {};

        // when
        Thread thread = factory.newThread(task);

        // then
        assertThat(thread.getName()).isEqualTo("-thread-1");
    }

    @Test
    void newThread_shouldHandleSpecialCharactersInPrefix() {
        // given
        NamedThreadFactory factory = new NamedThreadFactory("narou-api-fetcher");
        Runnable task = () -> {};

        // when
        Thread thread = factory.newThread(task);

        // then
        assertThat(thread.getName()).isEqualTo("narou-api-fetcher-thread-1");
    }

    @Test
    void newThread_shouldCreateNonDaemonThreadByDefault() {
        // given
        NamedThreadFactory factory = new NamedThreadFactory("test");
        Runnable task = () -> {};

        // when
        Thread thread = factory.newThread(task);

        // then
        assertThat(thread.isDaemon()).isFalse();
    }

    @Test
    void newThread_shouldUseNormalPriorityByDefault() {
        // given
        NamedThreadFactory factory = new NamedThreadFactory("test");
        Runnable task = () -> {};

        // when
        Thread thread = factory.newThread(task);

        // then
        assertThat(thread.getPriority()).isEqualTo(Thread.NORM_PRIORITY);
    }

    @Test
    void multipleFactories_shouldHaveIndependentCounters() {
        // given
        NamedThreadFactory factory1 = new NamedThreadFactory("pool1");
        NamedThreadFactory factory2 = new NamedThreadFactory("pool2");
        Runnable task = () -> {};

        // when
        Thread thread1a = factory1.newThread(task);
        Thread thread2a = factory2.newThread(task);
        Thread thread1b = factory1.newThread(task);
        Thread thread2b = factory2.newThread(task);

        // then
        assertThat(thread1a.getName()).isEqualTo("pool1-thread-1");
        assertThat(thread2a.getName()).isEqualTo("pool2-thread-1");
        assertThat(thread1b.getName()).isEqualTo("pool1-thread-2");
        assertThat(thread2b.getName()).isEqualTo("pool2-thread-2");
    }

    @Test
    void newThread_shouldHandleNullRunnable() {
        // given
        NamedThreadFactory factory = new NamedThreadFactory("test");

        // when
        Thread thread = factory.newThread(null);

        // then
        assertThat(thread).isNotNull();
        assertThat(thread.getName()).isEqualTo("test-thread-1");
    }
}