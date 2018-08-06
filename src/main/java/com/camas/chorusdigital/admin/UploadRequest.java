package com.camas.chorusdigital.admin;

import org.springframework.web.multipart.MultipartFile;

public class UploadRequest {

	String type;
	MultipartFile file;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}
}
