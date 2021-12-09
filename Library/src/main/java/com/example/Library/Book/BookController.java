package com.example.Library.Book;

import com.example.Library.Primitives.BookTitle;
import com.example.Library.Primitives.ISBN;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class BookController {

    private final BookRepository repository;

    private final BookModelAssembler assembler;

    BookController(BookRepository repository, BookModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/books")
    public CollectionModel<EntityModel<Book>> all() {
        List<EntityModel<Book>> Books = repository.findAll().stream() //
                .map(assembler::toModel) //
                .collect(Collectors.toList());

        return CollectionModel.of(Books, linkTo(methodOn(BookController.class).all()).withSelfRel());
    }

    @PostMapping("/books")
    ResponseEntity<?> newBook(@RequestBody Book newBook) {

        EntityModel<Book> entityModel = assembler.toModel(repository.save(newBook));

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @GetMapping("/book/{id}")
    public EntityModel<Book> getBook(@PathVariable Long id) {

        Book Book = repository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));

        return assembler.toModel(Book);
    }

    @GetMapping("/book")
    public CollectionModel<EntityModel<Book>> getBook(@RequestHeader Map<String, String> headers) {
        List<EntityModel<Book>> Books = new ArrayList<>();
        if (headers.containsKey("title")) {
            Books = repository.findByTitle(headers.get("title")).stream() //
                    .map(assembler::toModel) //
                    .collect(Collectors.toList());
        }
        else if (headers.containsKey("isbn")) {
            Books = repository.findByIsbn("isbn").stream() //
                    .map(assembler::toModel) //
                    .collect(Collectors.toList());
        }
        else {
            throw new BookNotFoundException(null);
        }

        return CollectionModel.of(Books, linkTo(methodOn(BookController.class).all()).withSelfRel());
    }

    @PutMapping("/books/{id}")
    ResponseEntity<?> updateBook(@RequestHeader Map<String, String> headers, @PathVariable Long id) {
        Book book = repository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        headers.forEach((key, value) -> {
            switch (key) {
                case "title":
                    book.setTitle(new BookTitle(value));
                    break;
                case "ISBN":
                    book.setISBN(new ISBN(value));
                    break;
//                case "author_id":
//                    book.setAuthor(author_id);
//                    break;
                default:
                    break;
            }
        });

        repository.save(book);

        EntityModel<Book> entityModel = assembler.toModel(book);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @DeleteMapping("/book/{id}")
    ResponseEntity<?> deleteBook(@PathVariable Long id) {

        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/book/")
    public ResponseEntity<?> deleteBooks(@RequestHeader("title") String title) {
        for(Book Book : repository.findByTitle(title)) {
            repository.delete(Book);
        }

        return ResponseEntity.noContent().build();
    }
}
