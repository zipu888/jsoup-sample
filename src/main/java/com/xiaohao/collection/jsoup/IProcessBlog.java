package com.xiaohao.collection.jsoup;

import org.jsoup.nodes.Element;

/**
 * Created by xiaohao on 2015/1/16.
 */
public interface IProcessBlog {

    public String processBlogTitle(Element element);

    public String processBlogContent(Element element);

    public String processBlogAuthor(Element element);

    public String processBlogTag(Element element);

}
