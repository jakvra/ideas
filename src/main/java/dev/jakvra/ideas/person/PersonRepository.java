package dev.jakvra.ideas.person;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PersonRepository extends JpaRepository<Person, Long> {
    
    @Query("select p from Post p inner join fetch p.author where p.id = :id")
    Person findPersonWithPosts(@Param("id") Long id);

}
