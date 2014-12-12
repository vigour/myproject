package com.keitsen.demo.module.domain.service;

import com.keitsen.demo.basic.service.IBasicService;
import com.keitsen.demo.module.domain.entity.District;

public interface IDistrictService extends IBasicService<District, String> {

	final static String SERVICE_NAME = "districtService";
}
