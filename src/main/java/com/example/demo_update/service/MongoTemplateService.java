package com.example.demo_update.service;

import com.example.demo_update.dto.VinInfo;

public interface MongoTemplateService {

    Boolean updateVinInfo(VinInfo vinInfo) throws IllegalAccessException;
}
