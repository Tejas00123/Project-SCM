package com.tejas.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserForm {

	@NotBlank(message="Name is required")
	@Size(min=3,message="Min 3 char are required")
	private String name;
	
	@Email(message="Invalid email address")
	@NotBlank(message="Email is required")
	private String email;
	
	@NotBlank(message="phoneNo is required")
    @Size(min = 8, max = 12, message = "Invalid Phone Number")
	private String phoneNo;
	
	@NotBlank(message = "Password is required")
    @Size(min = 6, message = "Min 6 Characters is required")
	private String password;
	
	private String about;
}
