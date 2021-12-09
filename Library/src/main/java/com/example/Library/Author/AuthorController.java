package com.example.Library.Author;

import com.example.Library.Primitives.Name;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class AuthorController {

    private final AuthorRepository repository;

    private final AuthorModelAssembler assembler;

    public AuthorController(AuthorRepository repository, AuthorModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/authors")
    public CollectionModel<EntityModel<Author>> all() {
        List<EntityModel<Author>> Authors = repository.findAll().stream() //
                .map(assembler::toModel) //
                .collect(Collectors.toList());

        return CollectionModel.of(Authors, linkTo(methodOn(AuthorController.class).all()).withSelfRel());
    }

    @PostMapping("/author")
    ResponseEntity<?> newAuthor(@RequestHeader Map<String, String> headers) {
            Author author = new Author();

            headers.forEach((key, value) -> {
                if (key.equals("firstname")) {
                    author.setFirstname(new Name(value));
                }
                else if (key.equals("lastname")) {
                    author.setLastname(new Name(value));
                }
            });

        EntityModel<Author> entityModel = assembler.toModel(repository.save(author));

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @GetMapping("/author/{id}")
    public EntityModel<Author> getAuthor(@PathVariable Long id) {

        Author Author = repository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException(id));

        return assembler.toModel(Author);
    }

    @GetMapping("/author")
    public CollectionModel<EntityModel<Author>> getAuthor(@RequestHeader("lastname") String lastname) {
        List<EntityModel<Author>> Authors = repository.findByLastname(lastname).stream() //
                .map(assembler::toModel) //
                .collect(Collectors.toList());

        return CollectionModel.of(Authors, linkTo(methodOn(AuthorController.class).all()).withSelfRel());
    }

    @PutMapping("/authors/{id}")
    ResponseEntity<?> updateAuthor(@RequestHeader Map<String, String> headers, @PathVariable Long id) {
        Author author = repository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException(id));
        headers.forEach((key, value) -> {
            if (key.equals("firstname")) {
                author.setFirstname(new Name(value));
            }
            else if (key.equals("lastname")) {
                author.setLastname(new Name(value));
            }
        });

        repository.save(author);

        EntityModel<Author> entityModel = assembler.toModel(author);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @DeleteMapping("/author/{id}")
    ResponseEntity<?> deleteAuthor(@PathVariable Long id) {

        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/author/")
    public ResponseEntity<?> deleteAuthors(@RequestHeader("lastname") String lastname) {
        repository.deleteAll(repository.findByLastname(lastname));

        return ResponseEntity.noContent().build();
    }
}
