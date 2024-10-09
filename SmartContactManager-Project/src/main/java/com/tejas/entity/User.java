package com.tejas.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name="USER_Table")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
   @Id
	private String userId;
	@Column(length=50)
	private String name;
	@Column(length=50)
	private String email;
	@Column(length=10)
	private String password;
	@Column(length=100)
	private String about;
	@Column(length=200)
	private String profilePic;
	@Column(length=15)
	private String phoneNumber;
	
	private boolean enabled=false;
	private boolean emailVerified=false;
	private boolean phoneVerified=false;
	
	private Providers provider = Providers.SELF;
	private String providerUserId;
	
	@OneToMany(fetch = FetchType.LAZY,mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
	private List<Contact> contactList = new ArrayList<>();
}
