package com.jaxrsproject.services;

import java.util.List;


import com.jaxrsproject.entities.Book;
import com.jaxrsproject.repositories.BookRepository;


public class BookServices {
	private BookRepository bookRepository ;
	

	public BookServices() {
		this.bookRepository = new BookRepository() ;
	}
	
	
	public List<Book> getAllBooks() {
		return bookRepository.getAllBooks() ; 
	}
	
	public List<Book> getAllBooksByAuthor(String authorName){ 
		return bookRepository.getBooksByAuthor(authorName ) ; 
	}
	
	public Book saveBook(Book book) {
		if(this.bookRepository.getBookById(book.getISBN()) != null)  {
			return null  ;
		}
		return bookRepository.saveBook(book) ; 
	}
	
	public boolean deleteBookById(String isbn)  {
		return bookRepository.deleteBookById(isbn);
	}
	
	public Book getBookById(String isbn ) { 
		return this.bookRepository.getBookById(isbn)  ; 
	}
	public Book updateBook(Book book ) {
		Book savedBook = this.bookRepository.getBookById(book.getISBN()) ;
		if( savedBook != null) {
			book.setCoverUrl(savedBook.getCoverUrl()) ;
			return bookRepository.updateBook(book) ;
		}
		return null  ;
	}
}
