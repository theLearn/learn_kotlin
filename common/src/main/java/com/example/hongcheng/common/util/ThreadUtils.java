package com.example.hongcheng.common.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Description:  [线程管理]<br>
 * @version      1.0  2015-7-4
 */
public final class ThreadUtils
{
    // 系统处理断任务线程池
    private static  ExecutorService cachedExecutorService = Executors.newCachedThreadPool(Executors
            .defaultThreadFactory());
    
    // 固定的线程数
    private static  ExecutorService fixedExecutorService = Executors.newFixedThreadPool(Runtime.getRuntime()
            .availableProcessors() * 2, Executors.defaultThreadFactory());
    
    // 单线程数
    private static  ExecutorService singleExecutorService = Executors.newSingleThreadExecutor();
    
    // 超时命令处理器
    private static  ScheduledExecutorService defaultScheduledExecutorService = Executors.newScheduledThreadPool(
            Runtime.getRuntime().availableProcessors() * 2, Executors.defaultThreadFactory());
    
    // 超时命令处理器
    private static  ScheduledExecutorService timeoutExecutorService = Executors.newScheduledThreadPool(Runtime
            .getRuntime().availableProcessors() * 2, Executors.defaultThreadFactory());
    
    // 默认IO处理线程为CPU数目
    // private static final SimpleIoProcessorPool<NioSession> ioProcessorPool =
    // new SimpleIoProcessorPool<NioSession>(
    // NioProcessor.class, Runtime.getRuntime().availableProcessors() * 3);
    
    private static  ExecutorService defaultExecutorService = new ThreadPoolExecutor(Runtime.getRuntime()
            .availableProcessors() * 10, Runtime.getRuntime().availableProcessors() * 16, 0, TimeUnit.NANOSECONDS,
            new LinkedBlockingQueue<Runnable>(), Executors.defaultThreadFactory());
    
    private ThreadUtils()
    {
    }
    
    public static ExecutorService getDefaultExecutorservice()
    {
        return defaultExecutorService;
    }
    
    public static ExecutorService getFixedExecutorservice()
    {
        return fixedExecutorService;
    }
    
    public static ExecutorService getSingleExecutorservice()
    {
        return singleExecutorService;
    }
    
    /**
     * 获取默认的IO处理器 <br>
     * @return
     */
    // public static SimpleIoProcessorPool<NioSession> getIoProcessorPool()
    // {
    // return ioProcessorPool;
    // }
    
    /**
     * 获取默认的调度线程处理器 <br>
     * @return
     */
    public static ScheduledExecutorService getDefaultScheduledExecutorService()
    {
        return defaultScheduledExecutorService;
    }
    
    /**
     * 获取超时处理线程池 <br>
     * @return
     */
    public static ScheduledExecutorService getTimeoutExecutorService()
    {
        return timeoutExecutorService;
    }
    
    /**
     * 获取超时处理线程池 <br>
     * @return
     */
    public static ExecutorService getCachedExecutorService()
    {
        return cachedExecutorService;
    }
    
    /**
     * 延迟执行函数 <br>
     * @param doRun
     */
    public static void execute(Runnable doRun)
    {
        cachedExecutorService.execute(doRun);
    }
    
}
