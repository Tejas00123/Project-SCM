package com.tejas.forms;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserForm {

	private String name;
	private String email;
	private String phoneNo;
	private String password;
	private String about;
}
