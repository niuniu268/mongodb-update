package com.example.demo_update.service.impl;

import com.example.demo_update.dto.VinInfo;
import com.example.demo_update.service.MongoTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.*;

@Service
public class MongoTemplateServiceImpl implements MongoTemplateService {
    @Autowired
    MongoTemplate mongoTemplate;
    @Override
    public Boolean updateVinInfo (VinInfo vinInfo) throws IllegalAccessException {
//      The input object, `vinInfo`, is used to find a data entry in the database where the VIN matches the input object's VIN.
        Query query = new Query(Criteria.where("vin").is(vinInfo.getVin()));
        VinInfo resultQuery = mongoTemplate.findOne( query,  VinInfo.class);

//        Using ObjectConvertMap to convert object into map
        Map<String, String> mapVin = ObjectConvertMap(vinInfo);
        Map<String, String> mapQuery = ObjectConvertMap(resultQuery);

//        comparing the fields between the input object's map and the query map. If the values differ, the values are updated.
        Set <String> keySet = mapVin.keySet( );
        for (String key : keySet) {
            if (!Objects.equals(key, "id")) {
                if (!Objects.equals(mapVin.get(key), mapQuery.get(key))) {
                    Update update = new Update().set(key, mapVin.get(key));
                    Query queryk = new Query(Criteria.where("vin").is(vinInfo.getVin()));
                    mongoTemplate.updateFirst(queryk, update, VinInfo.class);
                }
            }
        }

        return true;
    }
//  converting object into a map
    protected Map<String, String> ObjectConvertMap(VinInfo vinInfo) throws IllegalAccessException {

        Map<String, String> map = new HashMap <>();
        Class<?> clazz = vinInfo.getClass();
        for(Field field: clazz.getDeclaredFields()){
            field.setAccessible( true );
            map.put( field.getName( ), (String) field.get( vinInfo ) );
        }

        return  map;
    }


}
