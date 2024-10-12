package com.tejas.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class User implements UserDetails{
   @Id
	private String userId;
	@Column(length=50)
	private String name;
	@Column(length=50)
	private String email;
	@Column(length=100)
	private String password;
	@Column(length=100)
	private String about;
	@Column(length=200)
	private String profilePic;
	@Column(length=15)
	private String phoneNumber;
	
	private boolean enabled=true;
	private boolean emailVerified=false;
	private boolean phoneVerified=false;
	
	@Enumerated(value=EnumType.STRING)
	private Providers provider = Providers.SELF;
	private String providerUserId;
	
	@OneToMany(fetch = FetchType.LAZY,mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
	private List<Contact> contactList = new ArrayList<>();
	
	
	@ElementCollection(fetch = FetchType.EAGER)
	private List<String> roleList = new ArrayList<>();
	 
	@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
		 // list of roles[USER,ADMIN]
        // Collection of SimpGrantedAuthority[roles{ADMIN,USER}]
        Collection<SimpleGrantedAuthority> roles = roleList.stream().map(role -> new SimpleGrantedAuthority(role))
                .collect(Collectors.toList());
        return roles;
		}

	@Override
	public String getUsername() {
		return this.email;
	}

	

	  @Override
	    public boolean isAccountNonLocked() {
	        return true;
	    }

	    @Override
	    public boolean isCredentialsNonExpired() {
	        return true;
	    }

	
	
}
