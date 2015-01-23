package com.xiaohao.collection.service.impl;

import com.xiaohao.collection.entity.Blog;
import com.xiaohao.collection.mapper.BlogMapper;
import com.xiaohao.collection.service.BlogService;

import javax.annotation.Resource;

/**
 * Created by xiaohao on 2015/1/16.
 */
public class BlogServiceImpl implements BlogService {



    @Resource(name="blogMapper")
    BlogMapper blogMapper;


    @Override
    public int insertBlog(Blog blog) {
        return blogMapper.insert(blog);
    }
}
