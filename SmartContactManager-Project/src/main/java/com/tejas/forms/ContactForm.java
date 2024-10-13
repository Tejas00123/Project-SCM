package com.tejas.forms;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactForm {

	@NotBlank(message="Name is required")
	private String name;
	
	@Email(message="Invalid email address")
	@NotBlank(message="Email is required")
	private String email;
	
	@NotBlank(message="phoneNo is required")
	@Size(min=8,max=12,message = "Invalid Phone Number")
	private String phoneNumber;
	
	@NotBlank(message="Address is required")
	private String address;

    private String description;

    private boolean favorite=true;

    private String websiteLink;

    private String linkedInLink;

    // annotation create karenge jo file validate
    // size
    // resolution

   
    private MultipartFile contactImage;

    private String picture;
	
}
