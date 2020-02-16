package com.vedantu.daos;

import javax.annotation.PreDestroy;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import com.mongodb.MongoClient;

@Service
public class MongoConnection {
	MongoClient client;
	MongoTemplate template;
	
	public MongoConnection() {
		super();
		mongoTemplate();
	}
	
	
	public MongoTemplate mongoTemplate() {
		client = new MongoClient("localhost", 27017);
		
		template = new  MongoTemplate(client, "springtask");
		// here springtask is db name
		return template;
	}
	
	public MongoTemplate getMongoTemplate() {
		return template;
	}
	
	@PreDestroy
	public void cleanUp() {
		System.out.println("cleanup method is called");
		try {
			if(client != null) {
				client.close();
			}
		}catch(Exception e){
			System.out.println(e);
		}
	}
}
