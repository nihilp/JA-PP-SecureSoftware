package com.example.Library.Book;

import com.example.Library.Author.Author;
import com.example.Library.Primitives.*;
import com.example.Library.Publisher.Publisher;
import com.sun.istack.NotNull;


import javax.persistence.*;
import java.util.Objects;

@Entity
public class Book {

    private @Id @GeneratedValue Long id;

    private String title;
    private String isbn;

    @ManyToOne (optional = false)
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private Author author;

    @ManyToOne (optional = false)
    @JoinColumn(name = "publisher_id", referencedColumnName = "id")
    private Publisher publisher;

    public Book(@NotNull BookTitle title, @NotNull ISBN isbn, Author author, Publisher publisher) {

        this.title = title.getValue();
        this.isbn = isbn.getValue();
        this.author = author;
        this.publisher = publisher;
    }

    public Book() {

    }

    public String getTitle() {
        return this.title;
    }

    public Long getId() {
        return this.id;
    }

    public String getISBN() {
        return this.isbn;
    }

    public Author getAuthor() {
        return this.author;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setISBN(@NotNull ISBN isbn) {
        this.isbn = isbn.getValue();
    }

    public void setTitle(@NotNull BookTitle title) {
        this.title = title.getValue();
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

//    public void setAuthor(long author_id) {
//        this.author_id = author_id;
//    }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof Book))
            return false;
        Book book = (Book) o;
        return Objects.equals(this.id, book.id) && Objects.equals(this.isbn, book.getISBN())
                && Objects.equals(this.title, book.getTitle()) && this.author.equals(book.getAuthor());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.isbn, this.title, this.author);
    }

    @Override
    public String toString() {
        return "Book{" + "id=" + this.id + ", title='" + this.title + '\'' + ", isbn='" + this.isbn + '}';
    }
}