package dev.jakvra.ideas.person;

import dev.jakvra.ideas.comment.Comment;
import dev.jakvra.ideas.post.Post;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collection;

import static jakarta.persistence.CascadeType.ALL;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    @ToString.Exclude
    @OneToMany(mappedBy = "author", cascade = ALL)
    private Collection<Post> posts = new ArrayList<>();
    @ToString.Exclude
    @OneToMany(mappedBy = "author", cascade = ALL)
    private Collection<Comment> comments = new ArrayList<>();

    @Builder
    public Person(String firstName, String lastName, Collection<Post> posts) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.posts = posts;
    }

}
