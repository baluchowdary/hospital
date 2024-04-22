package com.kollu.hospital.api.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kollu.hospital.api.entity.HospitalEntity;

@Repository
public interface HospitalRepo extends JpaRepository<HospitalEntity, Long>{

}
