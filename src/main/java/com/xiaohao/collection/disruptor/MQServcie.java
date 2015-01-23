package com.xiaohao.collection.disruptor;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SleepingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import org.apache.lucene.document.Document;

import javax.annotation.Resource;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * <p>使用disruptor来实现异步的日志记录</p>
 *
 * disruptor 是一个本地系统的异步操作框架
 *
 * Created by xiaohao on 2014/9/28.
 */
public class MQServcie {

    //注入我们的事件处理器 用来处理队列中的数据
    @Resource(name = "eventHandler")
    MQHandler mqHandler;

    private int bufferSize = 2048;

    private Disruptor<MQLogEvent> disruptor;

    RingBuffer<MQLogEvent> ringBuffer=null;

    /**
     * 用来初始化圈形的队列
     */
    private void init(){

        Executor executor = Executors.newCachedThreadPool();
        // 日志事件生成工厂
        LogEventFactory factory = new LogEventFactory();
        // 创建disruptor 需要制定日志的生成工程
        disruptor = new Disruptor<MQLogEvent>(factory, bufferSize, executor, ProducerType.SINGLE,
                new SleepingWaitStrategy());
        // 生成事件处理器
       disruptor.handleEventsWith(mqHandler);
        // 启动disruptor
        ringBuffer = disruptor.start();
        // Get the ring buffer from the Disruptor to be used for publishing.
       // RingBuffer<MQLogEvent> ringBuffer = disruptor.getRingBuffer();
    }

    public void putData(Document document){
        //得到下一个序列
        long sequence = ringBuffer.next(); // Grab the next sequence
        try {
            //得到序列的节点
            MQLogEvent event = ringBuffer.get(sequence); // Get the entry in the Disruptor
            //放入需要处理的data
            event.setData(document); // Fill with data
        } finally {
            //发布出去
            ringBuffer.publish(sequence);
        }
    }

    //停掉disruptor
    public void destroy() {
        if (disruptor != null) {
            disruptor.shutdown();
        }
    }
}
