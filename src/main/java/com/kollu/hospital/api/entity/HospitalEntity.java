package com.kollu.hospital.api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "HOSPITAL_DATA")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HospitalEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hospitalcode_generator")
	@SequenceGenerator(name="hospitalcode_generator", sequenceName = "hospital_seq", allocationSize=1)
	private Long hospitalCode;
	
	@Column(name = "H_NAME")
	private String hName;
	
	@Column(name = "H_ADDRESS")
	private String hAddress;
	
	@Column(name = "H_ZIPCODE")
	private Integer hZipCode;
	
}
