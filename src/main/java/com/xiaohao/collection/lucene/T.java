package com.xiaohao.collection.lucene;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.File;

/**
 * Created by xiaohao on 2015/1/21.
 */
public class T {

    public static void main(String[] args)throws Exception{

        Directory directory = FSDirectory.open(new File("F:/myCode/collection-blog/index/"));

        @SuppressWarnings("deprecation")
        IndexReader reader = IndexReader.open(directory);
        IndexSearcher indexSearcher = new IndexSearcher(reader);

        Term term = new Term("title", "javascript");
        TermQuery termQuery = new TermQuery(term);
        TopDocs topDocs = indexSearcher.search(termQuery, 4);

        ScoreDoc scoreDocs[] = topDocs.scoreDocs;

        for (int i = 0; i < scoreDocs.length; i++) {
            Document document = indexSearcher.doc(scoreDocs[i].doc);
            System.out.println(document.get("id"));
            System.out.println(document.get("title"));
            System.out.println(document.get("content"));
        }

        directory.close();
    }

}
