package com.example.Library.Publisher;

import com.example.Library.Primitives.PublisherName;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Publisher {

    private @Id @GeneratedValue Long id;

    private String name;

    public Publisher() {}

    public Publisher(PublisherName name) {
        this.name = name.getValue();
    }

    public String getName() {
        return this.name;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(PublisherName name) {
        this.name = name.getValue();
    }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof Publisher))
            return false;
        Publisher author = (Publisher) o;
        return Objects.equals(this.id, author.id) && Objects.equals(this.name, author.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name);
    }

    @Override
    public String toString() {
        return "Publisher{" + "id=" + this.id + ", firstName='" + this.name + '\'' + '}';
    }
}
