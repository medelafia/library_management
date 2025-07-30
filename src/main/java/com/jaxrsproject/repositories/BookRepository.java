package com.jaxrsproject.repositories;

import com.jaxrsproject.entities.Book;
import com.jaxrsproject.utils.HibernateUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import java.util.List;



public class BookRepository {
	public List<Book>  getAllBooks() {
		List<Book> books = null ;
		try(Session session = HibernateUtils.getSessionFactory().openSession()) {
			books = session.createQuery("from Book" , Book.class).list()  ;
		} catch (HibernateException hibernateException) {
			hibernateException.printStackTrace();
		}
		return books;
	}
	public Book saveBook(Book book) {
		try(Session session = HibernateUtils.getSessionFactory().openSession()) {
			session.beginTransaction();
			session.persist(book);
			session.getTransaction().commit();
			return book;
		} catch (HibernateException hibernateException) {
			hibernateException.printStackTrace();
		}
		return null ;
	}
	public Book getBookById(String isbn ) {
		Book book = null ;
		try(Session session = HibernateUtils.getSessionFactory().openSession()) {
			book = session.get(Book.class, isbn);
		} catch (HibernateException hibernateException) {
			hibernateException.printStackTrace();
		}
		return book ;
	}
	public List<Book> getBooksByAuthor(String authorName) {
		List<Book> books = null ;
		try(Session session = HibernateUtils.getSessionFactory().openSession()) {
			Query<Book> query = session.createQuery("from Book where author=:auth" , Book.class)  ;
			query.setParameter("auth", authorName);

			books = query.list();
		} catch (HibernateException hibernateException) {
 			hibernateException.printStackTrace();
		}
		return books;
	}
	public boolean deleteBookById( String isbn  ) {
		try(Session session = HibernateUtils.getSessionFactory().openSession()) {
			if(session.get(Book.class, isbn) != null) {
				session.beginTransaction();
				session.remove(session.get(Book.class, isbn));
				session.getTransaction().commit();
				return true;
			}
		} catch (HibernateException hibernateException) {
			hibernateException.printStackTrace();
		}
		return false;
	}
	public Book updateBook(Book book) {
		try (Session session = HibernateUtils.getSessionFactory().openSession()) {
			session.beginTransaction();

			session.update(book);

			session.getTransaction().commit();
			return book ;
		} catch (HibernateException hibernateException) {
			hibernateException.printStackTrace();
		}
		return null ;
	}
}
