package com.example.demo_update;

import com.example.demo_update.dto.VinInfo;
import com.example.demo_update.respository.VinInfoRepository;
import com.example.demo_update.service.MongoTemplateService;
import com.example.demo_update.service.RepositoryService;
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
	@Autowired
	private MongoTemplateService mongoTemplateService;
	@Autowired
	private RepositoryService repositoryService;

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
		System.out.println("===========================" );
		System.out.println( vinInfoRepository.updateVinInfoByVinJustQuery( "003", "002"));
		System.out.println("after update" );
		System.out.println(vinInfoRepository.findAllByVin( "003" ) );
		System.out.println("===========================" );
	}

	@Test
	void testUpdateOneFieldbyVin(){
		System.out.println("before update" );
		System.out.println(vinInfoRepository.findAllByVin( "003" ) );
		System.out.println("===========================" );
		vinInfoRepository.updateVinInfoByVinTagF1( "003", "002");
		System.out.println("after update" );
		System.out.println(vinInfoRepository.findAllByVin( "003" ) );
		System.out.println("===========================" );
	}

	@Test
	void testUpdateFieldsbyVin(){
		System.out.println("before update" );
		System.out.println(vinInfoRepository.findAllByVin( "003" ) );
		System.out.println("===========================" );
		vinInfoRepository.updateVinInfoByVinMFs( "003", "002", "004", "005");
		System.out.println("after update" );
		System.out.println(vinInfoRepository.findAllByVin( "003" ) );
		System.out.println("===========================" );
	}
	@Test
	void testMongoTemplate(){

		System.out.println("before update" );
		System.out.println(vinInfoRepository.findAllByVin( "003" ) );
		System.out.println("===========================" );


		Query query = new Query();
		query.addCriteria( Criteria.where("vin").is("003"));
		Update update = new Update();
		update.set("field1", "002").set("field2", "004").set("field3", "005");
		mongoTemplate.updateFirst(query, update, VinInfo.class);

		System.out.println("after update" );
		System.out.println(vinInfoRepository.findAllByVin( "003" ) );
		System.out.println("===========================" );
	}

	@Test
	void testMongoTemplateServiceImpl() throws IllegalAccessException {

		System.out.println("before update" );
		System.out.println(vinInfoRepository.findAllByVin( "003" ) );

		System.out.println("===========================" );

		VinInfo testVin = new VinInfo(  );
		testVin.setVin( "003" );
		testVin.setField1( "005field1" );
		testVin.setField2( "005field2" );
		testVin.setField3( "005field3" );
		testVin.setField4( "005field4" );
		System.out.println("Mock input object: " );
		System.out.println(testVin );

		mongoTemplateService.updateVinInfo( testVin );

		System.out.println("after update" );
		System.out.println(vinInfoRepository.findAllByVin( "003" ) );
		System.out.println("===========================" );
	}

	@Test
	void testRepositoryServiceImpl(){

		System.out.println("before update" );
		System.out.println(vinInfoRepository.findAllByVin( "003" ) );
		System.out.println("===========================" );
		VinInfo testVin = new VinInfo(  );
		testVin.setVin( "003" );
		testVin.setField1( "005field1" );
		testVin.setField2( "005field2" );
		testVin.setField3( "005field3" );
		testVin.setField4( "005field4" );

		System.out.println("Mock input object: " );
		System.out.println(testVin );

		repositoryService.updateVinInfo( testVin );

		System.out.println("after update" );
		System.out.println(vinInfoRepository.findAllByVin( "003" ) );
		System.out.println("===========================" );

	}

	@Test
	void testContext(){}

}
