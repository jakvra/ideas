package dev.jakvra.ideas.post;

import dev.jakvra.ideas.comment.Comment;
import dev.jakvra.ideas.person.Person;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    @NotNull
    @ToString.Exclude
    @ManyToOne(fetch = LAZY)
    private Person author;
    @ToString.Exclude
    @OneToMany(mappedBy = "post", cascade = ALL)
    private List<Comment> comments;

    @Builder
    public Post(String title, String content, Person author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

}
