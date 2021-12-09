package com.example.Library.Author;

import com.example.Library.Primitives.Name;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Author {

    private @Id @GeneratedValue Long id;

    private String firstname;
    private String lastname;

    public Author() {}

    public Author(Name firstname, Name lastname) {

        this.firstname = firstname.getValue();
        this.lastname = lastname.getValue();
    }

    public String getName() {
        return this.firstname + " " + this.lastname;
    }

    public Long getId() {
        return this.id;
    }

    public String getFirstname() {
        return this.firstname;
    }

    public String getLastname() {
        return this.lastname;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setFirstname(Name firstname) {
        this.firstname = firstname.getValue();
    }

    public void setLastname(Name lastname) {
        this.lastname = lastname.getValue();
    }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof Author))
            return false;
        Author author = (Author) o;
        return Objects.equals(this.id, author.id) && Objects.equals(this.firstname, author.firstname)
                && Objects.equals(this.lastname, author.lastname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.firstname, this.lastname);
    }

    @Override
    public String toString() {
        return "Author{" + "id=" + this.id + ", firstname='" + this.firstname + '\'' + ", lastname='" + this.lastname
                + '\'' + '}';
    }
}
