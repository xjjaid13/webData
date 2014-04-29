package com.webCrawl.dao.crawl.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.webCrawl.dao.crawl.ICrawlLinkDao;
import com.webCrawl.entity.CrawlBug;
import com.webCrawl.entity.CrawlLink;

@Repository
public class CrawlLinkDaoImpl implements ICrawlLinkDao{

	private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }
	
	@Override
	public List<CrawlLink> queryAllLink(CrawlBug crawlBug) {
		SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(crawlBug);
		return jdbcTemplate.query("select * from t_crawl_link where bugId = :bugId", namedParameters, new BeanPropertyRowMapper<CrawlLink>(CrawlLink.class));
	}

	@Override
	public List<CrawlLink> queryWaitListLink(CrawlBug crawlBug) {
		SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(crawlBug);
		return jdbcTemplate.query("select * from t_crawl_link where bugId = :bugId and isCrawled = 0", namedParameters, new BeanPropertyRowMapper<CrawlLink>(CrawlLink.class));
	}

	@Override
	public void insertCrawlLink(CrawlBug crawlBug, String url) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("url", url);
		param.put("bugId", crawlBug.getBugId());
		jdbcTemplate.update("insert into t_crawl_link (link,isCrawled,bugId) values (:url,0,:bugId)",param);
	}

	@Override
	public void updateCrawlLinkStatus(CrawlBug crawlBug, String url) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("link", url);
		param.put("bugId", crawlBug.getBugId());
		jdbcTemplate.update("update t_crawl_link set isCrawled = 1 where bugId = :bugId and link = :link",param);
	}

}
