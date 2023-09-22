package dev.jakvra.ideas.controller;

import dev.jakvra.ideas.persistance.repository.PostReposiroty;
import dev.jakvra.ideas.persistance.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostReposiroty postReposiroty;

    @QueryMapping
    public List<Post> posts() {
        return postReposiroty.findAll();
    }

    @QueryMapping
    public List<Post> authorPosts(@Argument Long authorId) {
        return postReposiroty.findByAuthorId(authorId);
    }

    @QueryMapping
    public Optional<Post> post(@Argument Long id) {
        return postReposiroty.findById(id);
    }

    @MutationMapping
    public Post deletePost(@Argument Long id) {
        final Optional<Post> post = postReposiroty.findById(id);
        post.ifPresent(postReposiroty::delete);
        return post.orElse(null);
    }

}
