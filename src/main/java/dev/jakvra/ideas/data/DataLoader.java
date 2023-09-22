package dev.jakvra.ideas.data;

import dev.jakvra.ideas.persistance.domain.Comment;
import dev.jakvra.ideas.persistance.domain.Post;
import dev.jakvra.ideas.persistance.domain.User;
import dev.jakvra.ideas.persistance.repository.CommentReposiroty;
import dev.jakvra.ideas.persistance.repository.PostReposiroty;
import dev.jakvra.ideas.persistance.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Slf4j
@ConditionalOnProperty(prefix = "ideas", name = "random.data", havingValue = "true")
@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    static final int AUTHOR_COUNT_MIN = 3;
    static final int AUTHOR_COUNT_MAX = 5;
    static final int POST_COUNT_MIN = 0;
    static final int POST_COUNT_MAX = 10;

    private final UserRepository userRepository;
    private final PostReposiroty postReposiroty;
    private final CommentReposiroty commentReposiroty;
    private final Faker faker = new Faker();

    @Override
    public void run(String... args) {

        log.info("Generating random dummy data");

        Stream.generate(() -> initPosts(faker.random().nextInt(AUTHOR_COUNT_MIN, AUTHOR_COUNT_MAX), initUsers()))
                .limit(faker.random().nextInt(POST_COUNT_MIN, POST_COUNT_MAX))
                .forEach(user -> log.info("User: {}", user));

        log.info("Random data successfully generated.");
    }

    private User initUsers() {
        return userRepository.save(User.builder()
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .dateOfBird(faker.date().birthday(18, 60).toLocalDateTime().toLocalDate())
                .build());
    }

    private User initPosts(int count, User author) {
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
        ).limit(3).forEach(comment -> {
            Comment childComment = Comment.builder()
                    .content(faker.lorem().paragraph())
                    .post(post)
                    .author(post.getAuthor())
                    .parent(comment)
                    .build();
            commentReposiroty.save(childComment);
            comment.getChildren().add(childComment);
            log.info("Sub-Comment: {}", comment);
        });
        return post;
    }

}
