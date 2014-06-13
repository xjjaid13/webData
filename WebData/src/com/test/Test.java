package com.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.webCrawl.entity.CrawlBug;

public class Test {

	@Autowired
	public Test1 test1;
	
	public void say(){
		test1.doSomething();
	}
	
	public static void main(String[] args) {
		ApplicationContext context =
			    new ClassPathXmlApplicationContext(new String[] {"conf.xml"});
		CrawlBug crawlBug = new CrawlBug();
		crawlBug.setBugId(1);
		TestJDBC test = (TestJDBC) context.getBean("testJDBC");
		System.out.println(test.select(crawlBug).size());
		
	}
	
}
