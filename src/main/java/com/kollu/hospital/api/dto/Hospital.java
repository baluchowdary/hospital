package com.kollu.hospital.api.dto;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Hospital implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	private Long hospitalCode;
	
	@NotBlank(message = "Hospital Name Required")
	private String hospitalName;
	
	@NotBlank(message = "Hospital Address Required")
	private String hospitalAddress;
	
	//@Pattern(regexp = "[0-9]*")
	//@Size(min = 5, max = 6, message = "ZipCode number must be 6 digits")
	@NotNull(message = "Zipcode should not be empty")
	private Integer hospitalZipCode;
	
	
	

}
