package com.kaixin.android.utils;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author rock
 * thread pool
 * @createTime 2011-8-2
 */
public final class ThreadPoolUtil {

	private final static int CORE_POOL_SIZE = 1;
	private final static int MAX_POOL_SIZE = 1;
	private final static int KEEP_ALIVE_TIME = 1;
	private final static int MAX_QUEUE_SIZE = 100;
	private final static ThreadPoolExecutor executor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME,
			TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(MAX_QUEUE_SIZE,true),new ThreadPoolExecutor.CallerRunsPolicy());
	public static ThreadPoolExecutor getExecutor() {
		return executor;
	}
	public static void clearThreadQueue(){
		if (executor.getQueue().size() > 0) {
			executor.getQueue().clear();
		}
	}
}
