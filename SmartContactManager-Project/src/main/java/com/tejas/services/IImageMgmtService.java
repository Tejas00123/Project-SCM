package com.tejas.services;

import org.springframework.web.multipart.MultipartFile;

public interface IImageMgmtService {

	public String uploadImage(MultipartFile contactImage,String userFileName);
	
	public String getUrlOfUpploadedImage(String publicId);
}
