package com.example.Library.Book;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class BookModelAssembler implements RepresentationModelAssembler<Book, EntityModel<Book>> {
    @Override
    public EntityModel<Book> toModel(Book Book) {

        return EntityModel.of(Book,
                linkTo(methodOn(BookController.class).getBook(Book.getId())).withSelfRel(),
                linkTo(methodOn(BookController.class).all()).withRel("Books"));
    }
}