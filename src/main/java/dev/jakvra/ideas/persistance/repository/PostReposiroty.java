package dev.jakvra.ideas.persistance.repository;

import dev.jakvra.ideas.persistance.domain.Post;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.ScrollPosition;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Window;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostReposiroty extends JpaRepository<Post, Long> {

    List<Post> findByAuthorId(Long authorId);

    Window<Post> findByAuthorId(Long authorId, ScrollPosition position, Limit limit, Sort sort);

}
