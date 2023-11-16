package com.example.demo_update.service.impl;

import com.example.demo_update.dto.VinInfo;
import com.example.demo_update.respository.VinInfoRepository;
import com.example.demo_update.service.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class RepositoryServiceImpl implements RepositoryService {
    @Autowired
    private VinInfoRepository vinInfoRepository;
    @Override
    public Boolean updateVinInfo (VinInfo vinInfo) {
//      retrieving all data with a matching VIN from the database
        for (VinInfo info : vinInfoRepository.findAllByVin( vinInfo.getVin( ) )) {

//            Field1 is checked for equality between the input object and the database and updated
            if (!Objects.equals( info.getField1(), vinInfo.getField1())){
                vinInfoRepository.updateVinInfoByVinTagF1( vinInfo.getVin(), vinInfo.getField1());
            }
            if (!Objects.equals( info.getField2(), vinInfo.getField2())){
                vinInfoRepository.updateVinInfoByVinTagF2( vinInfo.getVin(), vinInfo.getField2());
            }
            if (!Objects.equals( info.getField3(), vinInfo.getField3())){
                vinInfoRepository.updateVinInfoByVinTagF3( vinInfo.getVin(), vinInfo.getField3());
            }

        }

        return true;
    }
}
