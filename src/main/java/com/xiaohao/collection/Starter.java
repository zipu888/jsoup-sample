package com.xiaohao.collection;

import com.xiaohao.collection.entity.Blog;
import com.xiaohao.collection.entity.Food;
import com.xiaohao.collection.mapper.BlogMapper;
import com.xiaohao.collection.mapper.FoodMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;

/**
 * Created by xiaohao on 2015/1/16.
 */
public class Starter {

    public static BlogMapper bp =null;

    public static FoodMapper fp =null;

    public static long i=3098;

    public static void init() throws ClassNotFoundException, SQLException {
        ApplicationContext ac = new FileSystemXmlApplicationContext("src/main/resources/applicationContext.xml");
        bp = (BlogMapper) ac.getBean("blogMapper");
        fp=(FoodMapper)ac.getBean("foodMapper");
    }

    public static void main(String[] args) throws Exception{

        init();

        //http://yingyang.00cha.com/index.asp

        //分类元素  body > div > div.nr > div.nra > div > div:nth-child(5) > ul > li:nth-child(3) > a



        //

        //食物列表 body > div > div.nr > div.nra > div > div.nsf > ul > li.lb > a

        //营养列表
        // body > div > div.nr > div.nra > div > div.biao > ul > li.lor

        Document doc = Jsoup.connect("http://yingyang.00cha.com/index.asp")
                .userAgent("Mozilla").get();

        Elements es =doc.select(".ccdd li a");

        String siteUrl ="http://yingyang.00cha.com/";

        Iterator<Element> esIterator =es.iterator();
        int index =0;
        while (esIterator.hasNext()){
            Element cate =esIterator.next();

            String cateName = cate.text();
            String cateUrl = siteUrl+cate.attr("href");
            Long cateId = Long.valueOf(++index*10000);

            Document food = Jsoup.connect(cateUrl)
                    .userAgent("Mozilla").get();
            Elements foods =food.select(".lb a");
            Iterator<Element> foodsIter =foods.iterator();
            int foodindex =1;
            while (foodsIter.hasNext()){
                Element foodEle =foodsIter.next();

                Food insertFood = new Food();
                foodindex++;

                insertFood.setFoodId(cateId+foodindex);
                insertFood.setFoodName(foodEle.text());
                insertFood.setCategoryId(cateId);
                insertFood.setCategoryName(cateName);

                String foodDetailUrl =siteUrl+foodEle.attr("href");
                Document foodDetail = Jsoup.connect(foodDetailUrl)
                        .userAgent("Mozilla").get();
                Elements foodDetails =foodDetail.select(".lor");
                Iterator<Element> foodDetailsIter =foodDetails.iterator();
                int foodDetailIndex=0;
                while (foodDetailsIter.hasNext()) {
                    Element foodDetailsEle = foodDetailsIter.next();
                    if(foodDetailIndex==0){
                        insertFood.setCal(new BigDecimal(foodDetailsEle.text()));
                    }
                    if(foodDetailIndex==1){
                        insertFood.setFat(new BigDecimal(foodDetailsEle.text()));
                    }
                    if(foodDetailIndex==2){
                        insertFood.setPro(new BigDecimal(foodDetailsEle.text()));
                    }
                    if(foodDetailIndex==3){
                        insertFood.setCh(new BigDecimal(foodDetailsEle.text()));
                    }
                    if(foodDetailIndex==4){
                        insertFood.setDf(new BigDecimal(foodDetailsEle.text()));
                    }
                    if(foodDetailIndex==5){
                        insertFood.setVb1(new BigDecimal(foodDetailsEle.text()));
                    }
                    if(foodDetailIndex==6){
                        insertFood.setCa(new BigDecimal(foodDetailsEle.text()));
                    }
                    if(foodDetailIndex==7){
                        insertFood.setVb2(new BigDecimal(foodDetailsEle.text()));
                    }//
                    if(foodDetailIndex==8){
                        insertFood.setMg(new BigDecimal(foodDetailsEle.text()));
                    }
                    if(foodDetailIndex==9){
                        insertFood.setVb3(new BigDecimal(foodDetailsEle.text()));
                    }
                    if(foodDetailIndex==10){
                        insertFood.setFe(new BigDecimal(foodDetailsEle.text()));
                    }
                    if(foodDetailIndex==11){
                        insertFood.setVc(new BigDecimal(foodDetailsEle.text()));
                    }
                    if(foodDetailIndex==12){
                        insertFood.setMn(new BigDecimal(foodDetailsEle.text()));
                    }
                    if(foodDetailIndex==13){
                        insertFood.setVe(new BigDecimal(foodDetailsEle.text()));
                    }
                    if(foodDetailIndex==14){
                        insertFood.setZn(new BigDecimal(foodDetailsEle.text()));
                    }
                    if(foodDetailIndex==15){
                        insertFood.setVa(new BigDecimal(foodDetailsEle.text()));
                    }
                    if(foodDetailIndex==16){
                        //insertFood.setCa(new BigDecimal(foodDetailsEle.text()));
                    }
                    if(foodDetailIndex==17){
                        insertFood.setCu(new BigDecimal(foodDetailsEle.text()));
                    }
                    if(foodDetailIndex==18){
                        insertFood.setCaro(new BigDecimal(foodDetailsEle.text()));
                    }
                    if(foodDetailIndex==19){
                        insertFood.setK(new BigDecimal(foodDetailsEle.text()));
                    }
                    if(foodDetailIndex==20){
                        insertFood.setP(new BigDecimal(foodDetailsEle.text()));
                    }
                    if(foodDetailIndex==21){
                        insertFood.setVa2(new BigDecimal(foodDetailsEle.text()));
                    }
                    if(foodDetailIndex==22){
                        insertFood.setNa2(new BigDecimal(foodDetailsEle.text()));
                    }
                    if(foodDetailIndex==23){
                        insertFood.setSe(new BigDecimal(foodDetailsEle.text()));
                    }

                    foodDetailIndex++;

                }

                fp.insert(insertFood);



            }
        }


    }

    private static void admin100Start() throws Exception {
        String dName ="http://www.admin10000.com";

        for (int u = 28; u< Constant.categroyUrlArr.length; u++){
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
