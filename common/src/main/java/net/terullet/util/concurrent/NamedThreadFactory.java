package net.terullet.util.concurrent;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ref.) <a href="https://blog.cybozu.io/entry/2017/07/10/080000">https://blog.cybozu.io/entry/2017/07/10/080000</a>
 */
public class NamedThreadFactory implements ThreadFactory {
	private final AtomicInteger threadId = new AtomicInteger(1);
	private final String format;

	public NamedThreadFactory(String prefix) {
		this.format = prefix + "-thread-%d";
	}
	/**
	 * Constructs a new unstarted {@code Thread} to run the given runnable.
	 *
	 * @param r a runnable to be executed by new thread instance
	 * @return constructed thread, or {@code null} if the request to
	 * create a thread is rejected
	 * @see <a href="../../lang/Thread.html#inheritance">Inheritance when
	 * creating threads</a>
	 */
	@Override
	public Thread newThread(Runnable r) {
		return new Thread(null, r, format.formatted(this.threadId.getAndIncrement()));
	}
}
