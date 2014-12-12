package com.keitsen.demo.module.domain.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.keitsen.demo.basic.service.impl.BasicService;
import com.keitsen.demo.module.domain.entity.District;
import com.keitsen.demo.module.domain.service.IDistrictService;

@Service(IDistrictService.SERVICE_NAME)
@Transactional
public class DistrictServiceImpl extends BasicService<District, String> implements
		IDistrictService {

}
