package com.tejas.services;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.tejas.helper.AppConstant;

import jakarta.annotation.Resource;

@Service
public class ImageMgmtServiceImpl implements IImageMgmtService {
     
	@Resource
    private Cloudinary cloudinary;
	
	@Override
	public String uploadImage(MultipartFile contactImage,String userFileName) {

		try{
            HashMap<Object, Object> options = new HashMap<>();
            options.put("folder", userFileName);
            Map uploadedFile = cloudinary.uploader().upload(contactImage.getBytes(), options);
            String publicId = (String) uploadedFile.get("public_id");
            return  cloudinary.url().secure(true).generate(publicId);

        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
	}
	
	@Override
	public String getUrlOfUpploadedImage(String publicId) {
		  return cloudinary
	                .url()
	                .transformation(
	                        new Transformation<>()
	                                .width(AppConstant.CONTACT_IMAGE_WIDTH)
	                                .height(AppConstant.CONTACT_IMAGE_HEIGHT)
	                                .crop(AppConstant.CONTACT_IMAGE_CROP))
	                .generate(publicId);
	}
}
