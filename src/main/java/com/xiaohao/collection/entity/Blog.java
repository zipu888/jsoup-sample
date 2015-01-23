package com.xiaohao.collection.entity;
import org.apache.lucene.document.*;

import java.io.Serializable;


/**
 * 
 * 
 * 
 **/
@SuppressWarnings("serial")
public class Blog implements Serializable {

	/****/
	private Long id;

	/****/
	private String title;

	/****/
	private String category;

	/****/
	private String content;



	public void setId(Long id){
		this.id = id;
	}

	public Long getId(){
		return this.id;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return this.title;
	}

	public void setCategory(String category){
		this.category = category;
	}

	public String getCategory(){
		return this.category;
	}

	public void setContent(String content){
		this.content = content;
	}

	public String getContent(){
		return this.content;
	}

    public Document toDocument() {
        Document d = new Document();
        d.add(new IntField("id",id.intValue(), Field.Store.YES));
        d.add(new TextField("title",title, Field.Store.YES));
        d.add(new TextField("content",content, Field.Store.YES));
        return d;
    }
}
