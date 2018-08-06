package com.camas.chorusdigital.gallery;

public interface PhotoService {

	Iterable<Photo> list();

	Photo get(Long id);

	Photo save(Photo photo);

	void delete(Long id);

}
