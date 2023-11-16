package com.example.demo_update.respository;

import com.example.demo_update.dto.VinInfo;


import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import org.springframework.data.mongodb.repository.Update;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface VinInfoRepository extends MongoRepository <VinInfo, String> {


    @Query("{"+"vin: ?0" +"}")
    List <VinInfo> findAllByVin(String Vin);

    @Query("{'vin': ?0, 'field1': ?1}")
    List<VinInfo> findAllByVin(String vin, String field1);

//   check if $set works in the @Query. The test, testUpdateOneFieldbyVinJustQuery, proves that this way does not work.
    @Query("{'vin': ?0, 'field1': { $ne: ?1}}, {$set: {'field1': ?1 }}")
    VinInfo updateVinInfoByVinJustQuery( String vin, String newField1Value);

//    use @Query and @Update to filfull the update request. The test, testUpdateOneFieldbyVin, proves.
    @Query("{'vin': ?0, 'field1': { $ne: ?1}}")
    @Update("{$set: {'field1': ?1 }}")
    void updateVinInfoByVinTagF1( String vin, String newField1Value);

    @Query("{'vin': ?0, 'field2': { $ne: ?1}}")
    @Update("{$set: {'field2': ?1 }}")
    void updateVinInfoByVinTagF2( String vin, String newField2Value);

    @Query("{'vin': ?0, 'field3': { $ne: ?1}}")
    @Update("{$set: {'field3': ?1 }}")
    void updateVinInfoByVinTagF3( String vin, String newField3Value);


//  @Update does not allow to use $cond. The test, testUpdateFieldsbyVin, proves.
    @Query("{'vin': ?0, 'field1': { $ne: ?1}, 'field2': { $ne: ?2}, 'field3': { $ne: ?3}}")
    @Update("{$set: {'field1': { $cond: { if: { $ne: ['?1', '$field1'] }, then: '?1', else: '$field1' } }, 'field2': ?2, 'field3': ?3 }}")
    void updateVinInfoByVinMFs(String vin, String newField1Value, String newField2Value, String newField3Value);

    @Query("{'vin': ?0 }")
    @Update("{$set: {'field1': ?1, 'field2': ?2, 'field3': ?3 }}")
    void updateVinInfoByVinClean(String vin, String newField1Value, String newField2Value, String newField3Value);




//    @Query("{'vin': ?0, 'field1': { $ne: ?1}, 'field2': { $ne: ?2}, 'field3': { $ne: ?3}}")
//    @Update("{$set: {'field1': ?1, 'field2': ?2, 'field3': ?3 }}")
//    void updateVinInfoByVinMFs(String vin, String newField1Value, String newField2Value, String newField3Value);

}


