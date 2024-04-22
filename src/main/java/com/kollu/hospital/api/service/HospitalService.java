package com.kollu.hospital.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.kollu.hospital.api.dao.HospitalRepo;
import com.kollu.hospital.api.entity.HospitalEntity;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@CacheConfig(cacheNames = "hospitalEntity")
public class HospitalService {
	
	@Autowired
	private HospitalRepo hospitalRepo;

	@Cacheable(value = "hospitalEntity")
	public List<HospitalEntity> findAllHospitalDetails() { 
		log.info("HospitalService.findAllHospitalDetails()"); 
		return hospitalRepo.findAll();
	}

	@Cacheable(value = "hospitalEntity")
	public void saveAllHospitalDetails(List<HospitalEntity> entitiesList) {
		log.info("entitiesList Size::" +entitiesList.size());
		hospitalRepo.saveAll(entitiesList);
	}
	
	@CachePut(value = "hospitalEntity")
	public HospitalEntity updateHospitalDetails(HospitalEntity entity) {
		return hospitalRepo.save(entity); 
		
	}

	@Cacheable(value = "hospitalEntity")
	public Optional<HospitalEntity> findHospitalByCode(Long hcode) {
		log.info("hcode :: "+hcode); 
		return hospitalRepo.findById(hcode);
	}

	@CacheEvict(value = "hospitalEntity")
	public void deleteHospitalByCode(Long hcode) {
		log.info("hcode::"+hcode); 
		hospitalRepo.deleteById(hcode);
	}
}
