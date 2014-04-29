package com.test;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.webCrawl.entity.CrawlBug;
import com.webCrawl.entity.CrawlLink;

@Repository("testJDBC")
public class TestJDBC {
	
	 private JdbcTemplate jdbcTemplate;

     @Autowired
     public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
     }

     public List<CrawlLink> select(CrawlBug crawlBug){
    	 return jdbcTemplate.query("select * from t_crawl_link", new BeanPropertyRowMapper<CrawlLink>(CrawlLink.class));
     }
}
