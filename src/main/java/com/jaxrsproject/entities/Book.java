package com.jaxrsproject.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.io.Serializable;


@Entity
public class Book implements Serializable {
	@Id
	private String ISBN ; 
	private String title ; 
	private String publisher ; 
	private String language ; 
	private String author ; 
	private int nbPages ;
	private String coverUrl ;


	public Book() {}
	public Book(String iSBN, String title, String publisher, String language, String author, int nbPages , String coverUrl) {
		super();
		ISBN = iSBN;
		this.title = title;
		this.publisher = publisher;
		this.language = language;
		this.author = author;
		this.nbPages = nbPages;
		this.coverUrl = coverUrl ;
	}
	public String getISBN() {
		return ISBN;
	}
	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public int getNbPages() {
		return nbPages;
	}
	public void setNbPages(int nbPages) {
		this.nbPages = nbPages;
	}
	public String getCoverUrl() {
		return coverUrl;
	}
	public void setCoverUrl(String coverUrl) {
		this.coverUrl = coverUrl;
	}
}
