package dev.jakvra.ideas.post;

public record PostInput(
        String title,
        String content,
        Long authorId
) {
}
