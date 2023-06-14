package com.getgifted.demo.entity;

import java.util.Date;

import org.springframework.lang.NonNull;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table
public class Feeds {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NonNull
	private String title;
	@NonNull
	private String description;
	@NonNull
	private Date publicationDate;

	public Feeds() {
		super();
	}

	public Feeds(Long id, String title, String description, Date publicationDate) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.publicationDate = publicationDate;
	}

	public Feeds(String title, String description, Date publicationDate) {
		super();
		this.title = title;
		this.description = description;
		this.publicationDate = publicationDate;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
	}

}
