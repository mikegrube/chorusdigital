package com.camas.chorusdigital.utility;

import com.camas.chorusdigital.gallery.Photo;

import java.io.File;

public interface UtilityService {

	String getSplashPic();

	String getGalleryPic(Photo photo);

	File getResourceFile(String subPath);

}