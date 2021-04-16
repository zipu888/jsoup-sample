package com.xiaohao.collection.disruptor;

/**
 * 定义队列数据处理器 需要实现disruptor的Eventhandler接口
 *
 * Created by xiaohao on 2014/9/28.
 */

import com.lmax.disruptor.EventHandler;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

//@Repository("eventHandler")
public class MQHandler implements EventHandler<MQLogEvent>  {

    @Resource(name="indexWriter")
    IndexWriter indexWriter;

    //@Override
    public void onEvent(MQLogEvent mqLogEvent, long l, boolean b) throws Exception {
        Document data =(Document)mqLogEvent.getData();
        if(data!=null){
            indexWriter.addDocument(data);
            indexWriter.commit();
        }
    }
}
