package com.xiaohao.collection.utils;

import com.xiaohao.collection.entity.Blog;
import com.xiaohao.collection.mapper.BlogMapper;
import com.xiaohao.collection.disruptor.MQServcie;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.util.Iterator;

/**
 * Created by xiaohao on 2015/1/19.
 */
public class ImportNewUtil {

    public static String url ="http://www.importnew.com/all-posts/page/";

    public static Long startId=20000L;

    public static BlogMapper bp =null;

    public static MQServcie mqServcie;

    public static void main(String[] args)throws Exception{

        initDb();

        for(int z=2;z<=36;z++){
            String pageListUrl =url+z+"/";
            prcessPageList(pageListUrl);
        }

    }

    public static void initDb(){
        ApplicationContext ac = new FileSystemXmlApplicationContext("src/main/resources/applicationContext.xml");
        bp = (BlogMapper) ac.getBean("blogMapper");
        mqServcie =ac.getBean(MQServcie.class);
    }

    public static void prcessPageList(String listUrl)throws Exception{

        Thread.sleep(2500);

        Document doc = Jsoup.connect(listUrl)
                .userAgent("Mozilla").get();

        Elements es =doc.select("#archive .floated-thumb .post-meta .meta-title");

        Iterator<Element> esIterator =es.iterator();

        while (esIterator.hasNext()){
            Element e =esIterator.next();
            String blogUrl =e.attr("href");
            processBlog(blogUrl);
        }



    }


    public static void processBlog(String blogUrl)throws Exception{

        Thread.sleep(3000);

        Document doc = Jsoup.connect(blogUrl)
                .userAgent("Mozilla").get();

        Element es =doc.select(".entry-header h1").first();

        Element cateEle =doc.select(".entry-meta a, .post-meta a").first();

        Element contentEle =doc.select(".entry").first();
        contentEle =contentEle.removeClass(".copyright-area");
        contentEle =contentEle.removeClass("text-13");
        String title =es.text();
        String category=cateEle.text();
        String content=contentEle.html();
        saveBlog(title,category,content);
    }


    public static void saveBlog(String title,String category,String content){

        Blog b =new Blog();
        b.setTitle(title);
        b.setCategory(category);
        b.setContent(content);
        b.setId(startId++);

        bp.insert(b);

        mqServcie.putData(b.toDocument());

    }


}
