package com.jaxrsproject.controllers;


import com.jaxrsproject.entities.Book;
import com.jaxrsproject.services.BookServices;
import com.jaxrsproject.services.S3Services;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import java.io.IOException;
import java.io.InputStream;


@Path("/books")
public class BooksController {
	private BookServices bookServices ;
	private S3Services s3Services ;
	public BooksController() {
		this.bookServices = new BookServices();
		this.s3Services = new S3Services();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllBooks() {
		 return Response.ok(this.bookServices.getAllBooks()).build() ; 
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBookById(@PathParam("id") String id) {
		Book book = this.bookServices.getBookById(id);
		if(book == null) return Response.status(404).build();
		return Response.ok(book).build() ;
	}

	@GET 
	@Path("/search") 
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBookByAuthorName(@QueryParam("author")String author) { 
		return Response.ok(this.bookServices.getAllBooksByAuthor(author)).build() ; 
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveBook(Book book) {
		Book savedBook = this.bookServices.saveBook(book);
		if(savedBook == null) return Response.status(403).build();
		return Response.ok(this.bookServices.saveBook(book)).build() ; 
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateBook(Book book) {
		Book updatedBook = this.bookServices.updateBook(book);
		if(updatedBook == null) return Response.status(404).build();
		return Response.ok(this.bookServices.updateBook(book)).build() ; 
	}

	@DELETE
	@Path("/{isbn}")
	public Response deleteBook(@PathParam("isbn")String isbn ) { 
		if(this.bookServices.deleteBookById(isbn))  return Response.ok().build();
		return Response.status(404).build() ;
	}

	@POST
	@Path("/cover/{isbn}")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadCover(@FormDataParam("cover")InputStream inputStream , @FormDataParam("cover")FormDataContentDisposition  formDataContentDisposition , @PathParam("isbn") String isbn ) {
		try {
			return Response.ok(s3Services.uploadImage(isbn, inputStream, formDataContentDisposition.getFileName())).build();
		}catch (IOException ioException) {
			return Response.status(Response.Status.EXPECTATION_FAILED).build() ;
		}
	}
	@GET
	@Path("cover/{key}")
	@Produces({"image/png", "image/jpeg", "image/webp"})
	public Response getCover(@PathParam("key") String key) throws IOException {
		return Response.ok(this.s3Services.getImage(key).readAllBytes()).build()  ;
	}

}
