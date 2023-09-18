package dev.jakvra.ideas.data;

import dev.jakvra.ideas.comment.Comment;
import dev.jakvra.ideas.comment.CommentReposiroty;
import dev.jakvra.ideas.person.Person;
import dev.jakvra.ideas.person.PersonRepository;
import dev.jakvra.ideas.post.Post;
import dev.jakvra.ideas.post.PostReposiroty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    static final int PERSON_COUNT_MIN = 3;
    static final int PERSON_COUNT_MAX = 5;
    static final int POST_COUNT_MIN = 0;
    static final int POST_COUNT_MAX = 10;

    private final PersonRepository personRepository;
    private final PostReposiroty postReposiroty;
    private final CommentReposiroty commentReposiroty;
    private final Faker faker = new Faker();

    @Override
    public void run(String... args) {

        Stream.generate(() -> initPosts(faker.random().nextInt(PERSON_COUNT_MIN, PERSON_COUNT_MAX), initPersons()))
                .limit(faker.random().nextInt(POST_COUNT_MIN, POST_COUNT_MAX))
                .forEach(person -> log.info("Person: {}", person));

    }

    private Person initPersons() {
        return personRepository.save(Person.builder()
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .build());
    }

    private Person initPosts(int count, Person author) {
        Stream.generate(() -> postReposiroty.save(Post.builder()
                        .title(faker.lorem().sentence())
                        .content(faker.lorem().paragraph())
                        .author(author)
                        .build())
                )
                .limit(count)
                .forEach(post -> {
                    log.info("Post: {}", post);
                    initComment(post);
                });
        return author;
    }

    // TODO
    private Post initComment(Post post) {
        Stream.generate(() -> commentReposiroty.save(Comment.builder()
                .content(faker.lorem().paragraph())
                .post(post)
                .author(post.getAuthor())
                .parent(null) // TODO:
                .build())
        ).limit(3).forEach(comment -> log.info("Comment: {}", comment));
        return post;
    }

}
