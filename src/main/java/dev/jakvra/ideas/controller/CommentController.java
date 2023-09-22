package dev.jakvra.ideas.controller;

import dev.jakvra.ideas.persistance.repository.CommentReposiroty;
import dev.jakvra.ideas.persistance.domain.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentReposiroty commentReposiroty;

    @QueryMapping
    public List<Comment> comments() {
        return commentReposiroty.findAll();
    }

}
