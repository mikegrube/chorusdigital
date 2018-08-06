package com.camas.chorusdigital.help;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HelpServiceImpl implements HelpService {

	private HelpRepository repository;

	@Autowired
	public void setRepository(HelpRepository repository) {
		this.repository = repository;
	}

	@Override
	public Iterable<Help> list() {
		return repository.findAll();
	}

	@Override
	public Help get(Long id) {
		return repository.findById(id).get();
	}

	@Override
	public Help save(Help help) {
		return repository.save(help);
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}
}
