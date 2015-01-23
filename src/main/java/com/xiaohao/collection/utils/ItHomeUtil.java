package com.xiaohao.collection.utils;

import com.xiaohao.collection.entity.Blog;
import com.xiaohao.collection.mapper.BlogMapper;
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
public class ItHomeUtil {
    public static String dName ="http://www.cftea.com";

    public static String url="http://www.cftea.com/news/index.asp?page=";

    public static String url2 ="http://it.ithome.com/category/1_";

    public static String category="资讯";

    public static Long startId=40200L;

    public static BlogMapper bp =null;

    public static void main(String[] args)throws Exception{

        initDb();

        for(int z=29;z<=36;z++){
            String pageListUrl =url2+z+".html";
            prcessPageList(pageListUrl);
        }

    }

    public static void initDb(){
        ApplicationContext ac = new FileSystemXmlApplicationContext("src/main/resources/applicationContext.xml");
        bp = (BlogMapper) ac.getBean("blogMapper");
    }

    public static void prcessPageList(String listUrl)throws Exception{

        Thread.sleep(5000);

        Document doc = Jsoup.connect(listUrl)
                .userAgent("Internet Explorer 8").cookie("ASP.NET_SessionId","swz1enhuhtsjjvesnuft5cem").get();

        Elements es =doc.select(".content .cate_list ul.ulcl li .block h2 a");

        Iterator<Element> esIterator =es.iterator();

        while (esIterator.hasNext()){
            Element e =esIterator.next();
            String blogUrl =e.attr("href");
            processBlog(blogUrl);
        }



    }


    public static void processBlog(String blogUrl)throws Exception{

        Thread.sleep(5000);

        Document doc = Jsoup.connect(blogUrl)
                .userAgent("Mozilla").cookie("ASP.NET_SessionId","swz1enhuhtsjjvesnuft5cem").get();

        Element es =doc.select(".post_title h1").first();

        //Element cateEle =doc.select(".entry-meta a, .post-meta a").first();

        Element contentEle =doc.select("#paragraph").first();
//        contentEle =contentEle.removeClass(".copyright-area");
//        contentEle =contentEle.removeClass("text-13");
        String title =es.text();
        //String category=cateEle.text();
        String content=contentEle.html();
        content =content.replace("<script src=\"http://file.ithome.com/js/v2/content_set.js\"></script> ","");
        saveBlog(title,category,content);
    }


    public static void saveBlog(String title,String category,String content){

        Blog b =new Blog();
        b.setTitle(title);
        b.setCategory(category);
        b.setContent(content);
        b.setId(startId++);

        bp.insert(b);

    }
}
