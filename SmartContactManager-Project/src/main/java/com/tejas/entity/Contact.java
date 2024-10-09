package com.tejas.entity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.ManyToAny;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name="CONTACT_Table")
@AllArgsConstructor
@NoArgsConstructor
public class Contact {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;
    @Column(length=50)
	private String name;
    @Column(length=50)
	private String email;
    @Column(length=15)
	private String phoneNumber;
    @Column(length=50)
	private String address;
    @Column(length=200)
	private String picture;
    @Column(length=100)
	private String description;
	private boolean favorite=false;
	@Column(length=200)
	private String websiteLink;
	@Column(length=200)
	private String linkedInLink;
	
	@ManyToOne
	private User user;
	
	@OneToMany(mappedBy = "contact",fetch = FetchType.EAGER,cascade = CascadeType.ALL,orphanRemoval = true)
	private List<SocialLink> linksList = new ArrayList<>();
}
