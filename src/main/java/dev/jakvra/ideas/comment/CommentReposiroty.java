package dev.jakvra.ideas.comment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentReposiroty extends JpaRepository<Comment, Long> {
}
