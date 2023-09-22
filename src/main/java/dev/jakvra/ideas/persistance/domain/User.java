package dev.jakvra.ideas.persistance.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import static jakarta.persistence.CascadeType.ALL;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "author")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBird;
    @ToString.Exclude
    @OneToMany(mappedBy = "author", cascade = ALL)
    private Collection<Post> posts = new ArrayList<>();
    @ToString.Exclude
    @OneToMany(mappedBy = "author", cascade = ALL)
    private Collection<Comment> comments = new ArrayList<>();

    @Builder
    public User(String firstName, String lastName, LocalDate dateOfBird, Collection<Post> posts) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBird = dateOfBird;
        this.posts = posts;
    }

}
