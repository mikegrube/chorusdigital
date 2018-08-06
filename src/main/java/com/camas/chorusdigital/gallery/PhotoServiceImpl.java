package com.camas.chorusdigital.gallery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhotoServiceImpl implements PhotoService {
	private static final Logger log = LoggerFactory.getLogger(PhotoServiceImpl.class);

	private PhotoRepository repository;

	@Autowired
	public void setRepository(PhotoRepository repository) {
		this.repository = repository;
	}

	@Override
	public Iterable<Photo> list() {
		return repository.findAll();
	}

	@Override
	public Photo get(Long id) {
		return repository.findById(id).get();
	}

	@Override
	public Photo save(Photo photo) {
		return repository.save(photo);
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}

}
