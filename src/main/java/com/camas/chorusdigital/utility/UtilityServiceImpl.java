package com.camas.chorusdigital.utility;

import com.camas.chorusdigital.gallery.Photo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class UtilityServiceImpl implements UtilityService {
	private static final Logger log = LoggerFactory.getLogger(UtilityServiceImpl.class);

	@Value("${repository.path}")
	private String repositoryPath;

	private int pictureCount = 0;

	@Override
	public String getSplashPic() {

		int pictureCount = getPictureCount();

		int randomNum = ThreadLocalRandom.current().nextInt(1, pictureCount + 1);
		String picURL = "/files/splash/Splash-" + Integer.toString(randomNum) + ".jpg";
		//log.info("Splash image name is: " + picURL);

		return picURL;
	}

	@Override
	public String getGalleryPic(Photo photo) {

		String photoLocation = "/files/" + photo.getPhotoLocation() + "." + photo.getType();

		return photoLocation;
	}

	private int getPictureCount() {

		if (pictureCount == 0) {

			FileSystemResource res = new FileSystemResource(repositoryPath + "splash");
			File splashDir = res.getFile();

			int ct = 0;
			String[] files = splashDir.list();
			for (int i = 0; i < files.length; i++) {
				String fileName = files[i];
				if (fileName.contains("Splash-")) {
					ct++;
				}
			}

			//log.info("Found " + ct + " splash files");
			pictureCount = ct;
		}

		return pictureCount;
	}

	public File getResourceFile(String subPath) {

		FileSystemResource res = new FileSystemResource(repositoryPath + subPath);
		return res.getFile();

	}
}
