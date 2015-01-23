package com.xiaohao.collection;

import com.xiaohao.collection.entity.Blog;
import com.xiaohao.collection.mapper.BlogMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;

/**
 * Created by xiaohao on 2015/1/16.
 */
public class Starter {

    public static BlogMapper bp =null;

    public static long i=3098;

    public static void init() throws ClassNotFoundException, SQLException {
        ApplicationContext ac = new FileSystemXmlApplicationContext("src/main/resources/applicationContext.xml");
        bp = (BlogMapper) ac.getBean("blogMapper");
    }

    public static void main(String[] args) throws Exception{

        init();

        String dName ="http://www.admin10000.com";

        for (int u=28;u<Constant.categroyUrlArr.length;u++){
            String cate =Constant.categroyUrlArr[u].replace("http://www.admin10000.com/","");
            cate =cate.replace("/","");
            for(int z =2;z<=Constant.maxSize[u];z++){

                Thread.sleep(3000);
                //http://www.admin10000.com/javascript/2/
                Document doc = Jsoup.connect(Constant.categroyUrlArr[u]+z+"/")
                        .userAgent("Mozilla").get();

                Elements es =doc.select("#categoryPage #main .list .document ul li");

                Iterator<Element> esIterator =es.iterator();

                while (esIterator.hasNext()){
                    Element e =esIterator.next();
                    Element titleEle =e.select(".head h2 a").first();
                    String url =titleEle.attr("href");

                    processBlog(dName+url,cate);

                }
            }

        }



    }

    private static void processBlog(String s,String str) throws Exception{

        Thread.sleep(3000);

        Document doc = Jsoup.connect(s)
                .userAgent("Mozilla").get();

        Element titleEle =doc.select("#documentPage #main h1.heading").first();

        String titile =titleEle.text();

        Element contentEle =doc.select("#documentPage #main .body").first();

        String content =contentEle.html();


        Blog blog = new Blog();
        blog.setTitle(titile);
        blog.setContent(content);
        blog.setCategory(str);
        blog.setId(i++);
        bp.insert(blog);
    }
}
