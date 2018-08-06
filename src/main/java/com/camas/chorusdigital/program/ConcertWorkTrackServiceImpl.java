package com.camas.chorusdigital.program;

import com.camas.chorusdigital.concert.Concert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class ConcertWorkTrackServiceImpl implements ConcertWorkTrackService {
	private static final Logger log = LoggerFactory.getLogger(ConcertWorkTrackServiceImpl.class);

	@Value("${repository.path}")
	private String repositoryPath;

	private ConcertWorkTrackRepository repository;
	@Autowired
	public void setRepository(ConcertWorkTrackRepository repository) {
		this.repository = repository;
	}

	private ConcertWorkService concertWorkService;
	@Autowired
	public void setConcertWorkService(ConcertWorkService concertWorkService) {
		this.concertWorkService = concertWorkService;
	}

	@Override
	public Iterable<ConcertWorkTrack> list() { return repository.findAll(); }

	@Override
	public ConcertWorkTrack get(Long id) {
		return repository.findById(id).get();
	}

	@Override
	public ConcertWorkTrack save(ConcertWorkTrack concertWorkTrack) {

		concertWorkTrack.getAudioFileName();

		return repository.save(concertWorkTrack);
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}

	@Override
	public Iterable<ConcertWorkTrack> concertWorkTracksForConcertWork(ConcertWork concertWork) {
		return repository.findByConcertWorkOrderByTrackAsc(concertWork);
	}

	@Override
	public ConcertWorkTrack findConcertWorkTrack(ConcertWork concertWork, String title) {
		return repository.findByConcertWorkAndTitle(concertWork, title);
	}

	@Override
	public Object availableConcertWorks() {
		return concertWorkService.list();
	}

	@Override
	public ConcertWork findConcertWork(Long concertWorkId) {
		return concertWorkService.get(concertWorkId);
	}

	@Override
	public Object audioFilesForConcert(Concert concert) {

		List<String> fileNames = new ArrayList<>();

		FileSystemResource res = new FileSystemResource(getConcertPath(concert));
		File concertDir = res.getFile();
		log.info(concertDir.getAbsolutePath());

		String[] files = concertDir.list();
		for (int i = 0; i < files.length; i++) {
			fileNames.add(files[i]);
		}

		fileNames.sort(Comparator.naturalOrder());

		return fileNames;
	}

	String getConcertPath(Concert concert) {

		//'files' is defined in StaticResourceConfiguration

		return repositoryPath + "audio/" + concert.getSeason().getStartYear() + "-" + concert.getTitle() + "/";
	}

	public String getTrackAudioPath(ConcertWorkTrack concertWorkTrack) {

		Concert concert = concertWorkTrack.getConcertWork().getConcert();

		//'files' is defined in StaticResourceConfiguration
		return repositoryPath + "/audio/" + concert.getSeason().getStartYear() + "-" + concert.getTitle() + "/" + concertWorkTrack.getAudioFileName();

	}

}

