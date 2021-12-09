package com.example.Library.Author;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AuthorModelAssembler implements RepresentationModelAssembler<Author, EntityModel<Author>> {
    @Override
    public EntityModel<Author> toModel(Author Author) {

        return EntityModel.of(Author,
                linkTo(methodOn(AuthorController.class).getAuthor(Author.getId())).withSelfRel(),
                linkTo(methodOn(AuthorController.class).all()).withRel("Authors"));
    }
}