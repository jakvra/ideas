package dev.jakvra.ideas.graphql;

public record PostInput(
        String title,
        String content,
        Long authorId
) {
}
