package dev.jakvra.ideas.persistance.repository;

import dev.jakvra.ideas.persistance.domain.Comment;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.ScrollPosition;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Window;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentReposiroty extends JpaRepository<Comment, Long> {

    Window<Comment> findByAuthorId(Long id, ScrollPosition scrollPosition, Limit limit, Sort sort);

}
