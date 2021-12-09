package com.example.Library;

import com.example.Library.Author.Author;
import com.example.Library.Author.AuthorRepository;
import com.example.Library.Book.Book;
import com.example.Library.Book.BookRepository;
import com.example.Library.Primitives.BookTitle;
import com.example.Library.Primitives.ISBN;
import com.example.Library.Primitives.Name;
import com.example.Library.Primitives.PublisherName;
import com.example.Library.Publisher.Publisher;
import com.example.Library.Publisher.PublisherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(AuthorRepository authorRepository, PublisherRepository publisherRepository, BookRepository bookRepository) {

        return args -> {
            Author myAuthor = new Author(new Name("John"), new Name("Tolkien"));
            Publisher myPublisher = new Publisher(new PublisherName("ABC @ 123"));
            authorRepository.save(myAuthor);
            publisherRepository.save(myPublisher);

            bookRepository.save(new Book(new BookTitle("The Two Towers"), new ISBN("0345339711"), myAuthor, myPublisher));

            myAuthor = new Author(new Name("Christopher"), new Name("Tolkien"));

            authorRepository.save(myAuthor);

            bookRepository.save(new Book(new BookTitle("Morgoths Ring"), new ISBN("0261103008"), myAuthor, myPublisher));

            authorRepository.save(new Author(new Name("Haruki"), new Name("Murakami")));

            authorRepository.findAll().forEach(author -> log.info("Preloaded " + author));

            publisherRepository.findAll().forEach(publisher -> {
                log.info("Preloaded " + publisher);
            });

            bookRepository.findAll().forEach(book -> {
                log.info("Preloaded " + book);
            });

        };
    }
}
