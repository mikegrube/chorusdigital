package com.camas.chorusdigital.help;

public interface HelpService {

	Iterable<Help> list();

	Help get(Long id);

	Help save(Help help);

	void delete(Long id);
}
