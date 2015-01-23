package com.xiaohao.collection.jsoup.impl;

import com.xiaohao.collection.jsoup.IProcessPageList;
import org.jsoup.nodes.Element;

/**
 * Created by xiaohao on 2015/1/16.
 */
public class Admin10000ProcessPageList implements IProcessPageList {

    @Override
    public String processBlogUrl(Element element) {
        String url=element.select("").first().text();
        return url;
    }
}
