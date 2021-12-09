package com.example.Library.Publisher;

import com.example.Library.Primitives.PublisherName;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class PublisherController {

    private final PublisherRepository repository;

    private final PublisherModelAssembler assembler;

    public PublisherController(PublisherRepository repository, PublisherModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/publishers")
    public CollectionModel<EntityModel<Publisher>> all() {
        List<EntityModel<Publisher>> Publishers = repository.findAll().stream() //
                .map(assembler::toModel) //
                .collect(Collectors.toList());

        return CollectionModel.of(Publishers, linkTo(methodOn(PublisherController.class).all()).withSelfRel());
    }

    @PostMapping("/publisher")
    ResponseEntity<?> newPublisher(@RequestHeader("name") String name) {
        EntityModel<Publisher> entityModel = assembler.toModel(repository.save(new Publisher(new PublisherName(name))));

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @GetMapping("/publisher/{id}")
    public EntityModel<Publisher> getPublisher(@PathVariable Long id) {

        Publisher Publisher = repository.findById(id)
                .orElseThrow(() -> new PublisherNotFoundException(id));

        return assembler.toModel(Publisher);
    }

    @GetMapping("/publisher")
    public CollectionModel<EntityModel<Publisher>> getPublisher(@RequestHeader("name") String name) {
        List<EntityModel<Publisher>> Publishers = repository.findByName(name).stream() //
                .map(assembler::toModel) //
                .collect(Collectors.toList());

        return CollectionModel.of(Publishers, linkTo(methodOn(PublisherController.class).all()).withSelfRel());
    }

    @PutMapping("/publisher/{id}")
    ResponseEntity<?> updatePublisher(@RequestHeader("name") String name, @PathVariable Long id) {
        Publisher publisher = repository.findById(id)
                .orElseThrow(() -> new PublisherNotFoundException(id));
        if (!name.isEmpty()) {
            publisher.setName(new PublisherName(name));
        }

        repository.save(publisher);

        EntityModel<Publisher> entityModel = assembler.toModel(publisher);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @DeleteMapping("/publisher/{id}")
    ResponseEntity<?> deletePublisher(@PathVariable Long id) {

        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/publisher/")
    public ResponseEntity<?> deletePublishers(@RequestHeader("name") String name) {
        repository.deleteAll(repository.findByName(name));

        return ResponseEntity.noContent().build();
    }
}
