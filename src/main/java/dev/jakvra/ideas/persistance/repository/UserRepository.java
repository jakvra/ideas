package dev.jakvra.ideas.persistance.repository;

import dev.jakvra.ideas.persistance.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
    
    @Query("select p from Post p inner join fetch p.author where p.id = :id")
    User findPersonWithPosts(@Param("id") Long id);

}
