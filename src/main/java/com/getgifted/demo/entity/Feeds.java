package com.getgifted.demo.entity;

import org.springframework.lang.NonNull;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * The Class Feeds.
 */
@Entity
@Table
public class Feeds {

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	/** The title. */
	@NonNull
	private String title;
	
	/** The description. */
	@NonNull
	@Column(columnDefinition = "TEXT", length = 1000)
	private String description;
	
	/** The publication date. */
	@NonNull
	private String publicationDate;

	/**
	 * Instantiates a new feeds.
	 */
	public Feeds() {
		super();
	}

	/**
	 * Instantiates a new feeds.
	 *
	 * @param id the id
	 * @param title the title
	 * @param description the description
	 * @param publicationDate the publication date
	 */
	public Feeds(Long id, String title, String description, String publicationDate) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.publicationDate = publicationDate;
	}

	/**
	 * Instantiates a new feeds.
	 *
	 * @param title the title
	 * @param description the description
	 * @param publicationDate the publication date
	 */
	public Feeds(String title, String description, String publicationDate) {
		super();
		this.title = title;
		this.description = description;
		this.publicationDate = publicationDate;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title.
	 *
	 * @param title the new title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the publication date.
	 *
	 * @return the publication date
	 */
	public String getPublicationDate() {
		return publicationDate;
	}

	/**
	 * Sets the publication date.
	 *
	 * @param publicationDate the new publication date
	 */
	public void setPublicationDate(String publicationDate) {
		this.publicationDate = publicationDate;
	}
	
	

}
