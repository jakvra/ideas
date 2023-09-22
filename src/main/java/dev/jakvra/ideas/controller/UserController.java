package dev.jakvra.ideas.controller;

import dev.jakvra.ideas.persistance.domain.Comment;
import dev.jakvra.ideas.persistance.domain.Post;
import dev.jakvra.ideas.persistance.repository.CommentReposiroty;
import dev.jakvra.ideas.persistance.repository.PostReposiroty;
import dev.jakvra.ideas.persistance.repository.UserRepository;
import dev.jakvra.ideas.persistance.domain.User;
import dev.jakvra.ideas.graphql.UserInput;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.ScrollPosition;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Window;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.graphql.data.query.ScrollSubrange;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final PostReposiroty postReposiroty;
    private final CommentReposiroty commentReposiroty;

    @QueryMapping
    public List<User> users() {
        return userRepository.findAll();
    }

    @QueryMapping
    Optional<User> user(@Argument Long id) {
        return userRepository.findById(id);
    }

    @SchemaMapping
    public Window<Post> posts(User author, ScrollSubrange subrange) {
        ScrollPosition scrollPosition = subrange.position().orElse(ScrollPosition.offset());
        Limit limit = Limit.of(subrange.count().orElse(10));
        Sort sort = Sort.by("id").ascending();
        return postReposiroty.findByAuthorId(author.getId(), scrollPosition, limit, sort);
    }

    @SchemaMapping
    public Window<Comment> comments(User author, ScrollSubrange subrange) {
        ScrollPosition scrollPosition = subrange.position().orElse(ScrollPosition.offset());
        Limit limit = Limit.of(subrange.count().orElse(10));
        Sort sort = Sort.by("id").ascending();
        return commentReposiroty.findByAuthorId(author.getId(), scrollPosition, limit, sort);
    }


    @MutationMapping
    public User createUser(@Argument UserInput user) {
        return userRepository.save(User.builder()
                .firstName(user.firstName())
                .lastName(user.lastName())
                .build());
    }

    @MutationMapping
    public User deleteUser(@Argument Long id) {
        final Optional<User> user = userRepository.findById(id);
        user.ifPresent(userRepository::delete);
        return user.orElse(null);
    }

}
