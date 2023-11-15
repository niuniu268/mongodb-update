package com.example.demo_update;

import com.example.demo_update.dto.VinInfo;
import com.example.demo_update.respository.VinInfoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.event.annotation.AfterTestMethod;


@SpringBootTest
class DemoUpdateApplicationTests {

	@Autowired
	private VinInfoRepository vinInfoRepository;
	@Autowired
	private MongoTemplate mongoTemplate;

	@BeforeAll
	static void beforeAll(){
		System.out.println("Junit test" );
	}

	@AfterEach
	void afterMethod(){
		vinInfoRepository.updateVinInfoByVinClean( "003", "003field1", "003field2", "003field3" );
	}

	@Test
	void testSelectAll() {
		System.out.println("before update" );
		System.out.println(vinInfoRepository.findAllByVin( "004" ) );
	}

	@Test
	void testUpdateOneFieldbyVinJustQuery(){
		System.out.println("before update" );
		System.out.println(vinInfoRepository.findAllByVin( "003" ) );
//		vinInfoRepository.updateVinInfoByVin( "003", "002");
		System.out.println( vinInfoRepository.updateVinInfoByVinJustQuery( "003", "002"));
		System.out.println("after update" );
		System.out.println(vinInfoRepository.findAllByVin( "003" ) );
	}

	@Test
	void testUpdateOneFieldbyVin(){
		System.out.println("before update" );
		System.out.println(vinInfoRepository.findAllByVin( "003" ) );
		vinInfoRepository.updateVinInfoByVinTagF1( "003", "002");
		System.out.println("after update" );
		System.out.println(vinInfoRepository.findAllByVin( "003" ) );
	}

	@Test
	void testUpdateFieldsbyVin(){
		System.out.println("before update" );
		System.out.println(vinInfoRepository.findAllByVin( "003" ) );
		vinInfoRepository.updateVinInfoByVinMFs( "003", "002", "004", "005");
		System.out.println("after update" );
		System.out.println(vinInfoRepository.findAllByVin( "003" ) );
	}
	@Test
	void testMongoTemplate(){

		System.out.println("before update" );
		System.out.println(vinInfoRepository.findAllByVin( "003" ) );

		Query query = new Query();
		query.addCriteria( Criteria.where("vin").is("003").and("field1").ne("002").and("field2").ne("003").and("field3").ne("003"));
		Update update = new Update();
		update.set("field1", "002").set("field2", "004").set("field3", "005");
		mongoTemplate.updateFirst(query, update, VinInfo.class);

		System.out.println("after update" );
		System.out.println(vinInfoRepository.findAllByVin( "003" ) );

	}

	@Test
	void testContext(){}

}
