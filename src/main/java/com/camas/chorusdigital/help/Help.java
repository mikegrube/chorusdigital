package com.camas.chorusdigital.help;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;

@Entity
public class Help {
	private static final Logger log = LoggerFactory.getLogger(Help.class);

	@Id
	@GeneratedValue
	private Long id;

	private String title;

	@Column(columnDefinition="CLOB NOT NULL")
	@Lob
	private String text;

	public Help() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
