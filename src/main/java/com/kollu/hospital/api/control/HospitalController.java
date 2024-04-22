package com.kollu.hospital.api.control;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kollu.hospital.api.dto.Hospital;
import com.kollu.hospital.api.entity.HospitalEntity;
import com.kollu.hospital.api.service.HospitalService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/h")
@Slf4j
public class HospitalController {

	@Autowired
	private HospitalService hospitalService;

	
	@GetMapping("/hfetchall")
	public ResponseEntity<Object> fetchAllHospitalDetails(){
		try {
			
			List<Hospital> hospitals = null;
			List<HospitalEntity>  hospitalEntities =hospitalService.findAllHospitalDetails();
			
			if(hospitalEntities.isEmpty()) {
				log.info("HospitalEntity is Empty");
				return new ResponseEntity<>("Hospital Details Not Available", HttpStatus.NOT_FOUND);
				
			} else {
				hospitals = hospitalEntities.stream()
						.map(h -> new Hospital(h.getHospitalCode(), h.getHName(), h.getHAddress(), h.getHZipCode()))
						.collect(Collectors.toList());
				log.debug("Hospitals size::", hospitals.size());
			}
		
			return new ResponseEntity<>(hospitals, HttpStatus.OK);
		}catch (Exception e) {
			log.error("Error while fetching Hospital Details::", e.getLocalizedMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		} 
	}
	
	@PostMapping("/hsave")
	public ResponseEntity<Object> savePatientDetails(@RequestBody @Valid Hospital hospital){
		try {
			HospitalEntity entityObj = new HospitalEntity();
			List<HospitalEntity> entitiesList = new ArrayList<>();
			
			entityObj = setEntityData(hospital, entityObj);
			
			 entitiesList.add(entityObj);
			 hospitalService.saveAllHospitalDetails(entitiesList);
			
			return new ResponseEntity<>("Hospital Details Saved Successfully!", HttpStatus.CREATED);
		}catch (Exception e) {
			//log.error("Error while saving Patient details", e.getLocalizedMessage());
			log.info("Error while saving Hospital details", e.getLocalizedMessage());
			return new ResponseEntity<>("Unable to Save Hospital Details",HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/hupdate/{hcode}")
	public ResponseEntity<Object> updateHospitalDetails(@PathVariable("hcode") Long hcode, @RequestBody Hospital hospital){
		log.info("HCODE::"+hcode);
		try {
			Optional<HospitalEntity> hEntity = hospitalService.findHospitalByCode(hcode);
			log.debug("hEntity::" +hEntity);
			
			HospitalEntity entity = null;
			
			if(hEntity.isPresent()) {
				 entity= hEntity.get();
			} else {
				return new ResponseEntity<Object>("Hospital Code Invalid", HttpStatus.NOT_FOUND);
			}
			
			entity.setHName(hospital.getHospitalName());
			entity.setHAddress(hospital.getHospitalAddress());
			entity.setHZipCode(hospital.getHospitalZipCode());
		
			hospitalService.updateHospitalDetails(entity);
			
			return new ResponseEntity<>("Updated Hospital Details Successfully", HttpStatus.OK);
		}catch (Exception e) {
			log.info("Update - "+e.getMessage());
			return new ResponseEntity<>("Error while Updating Hospital Details ",HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/hdelete/{hcode}")
	public ResponseEntity<Object> deletePatientDetails(@PathVariable("hcode") Long hcode){
		try {
			
			Optional<HospitalEntity> hEntity = hospitalService.findHospitalByCode(hcode);
			log.debug("HCODE::" +hEntity);
			
			if(hEntity.isPresent()) {
				hospitalService.deleteHospitalByCode(hcode);
			} else {
				return new ResponseEntity<Object>("Hospital Code Invalid", HttpStatus.NOT_FOUND);
			}
		
			return new ResponseEntity<>("Hospital Record Deleted Successfully ", HttpStatus.ACCEPTED);
		}catch (Exception e) {
			return new ResponseEntity<>("Error while Deleting Hospital Record ",HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	private HospitalEntity setEntityData(Hospital hospital, HospitalEntity entityObj) {
		log.info("HospitalController.setEntityData()");
		entityObj.setHName(hospital.getHospitalName());
		entityObj.setHAddress(hospital.getHospitalAddress());
		entityObj.setHZipCode(hospital.getHospitalZipCode());
		return entityObj;
	}
	
}
