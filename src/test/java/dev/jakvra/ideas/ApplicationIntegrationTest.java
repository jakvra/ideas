package dev.jakvra.ideas;

import dev.jakvra.ideas.persistance.domain.Comment;
import dev.jakvra.ideas.persistance.domain.Post;
import dev.jakvra.ideas.persistance.domain.User;
import dev.jakvra.ideas.persistance.repository.CommentReposiroty;
import dev.jakvra.ideas.persistance.repository.PostReposiroty;
import dev.jakvra.ideas.persistance.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.graphql.test.tester.HttpGraphQlTester;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.PostgreSQLContainer;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationIntegrationTest {

    static PostgreSQLContainer<?> database =
            new PostgreSQLContainer<>("postgres:15.3")
                    .withDatabaseName("ideas")
                    .withUsername("jakvra")
                    .withPassword("s3cret");

    static {
        database.start();
    }

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", database::getJdbcUrl);
        registry.add("spring.datasource.username", database::getUsername);
        registry.add("spring.datasource.password", database::getPassword);
    }

    @LocalServerPort
    int port;
    private HttpGraphQlTester graphQlTester;
    @Autowired
    private PostReposiroty postReposiroty;
    @Autowired
    private CommentReposiroty commentReposiroty;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void init() {
        graphQlTester = HttpGraphQlTester.create(WebTestClient.bindToServer().baseUrl("http://localhost:%s/graphql".formatted(port)).build());
        this.postReposiroty.deleteAll();
        this.commentReposiroty.deleteAll();
        this.userRepository.deleteAll();
    }

    @AfterEach
    void cleanUp() {
        this.postReposiroty.deleteAll();
        this.commentReposiroty.deleteAll();
        this.userRepository.deleteAll();
    }

    @Test
    void shouldRetriveUserWithPostsAndComments() {

        // given
        final var jack = User.builder().firstName("Jack").lastName("Daniels").dateOfBird(LocalDate.of(1950, 5, 13)).build();
        final var john = User.builder().firstName("John").lastName("Daniels").dateOfBird(LocalDate.of(1955, 7, 22)).build();
        final var post = Post.builder().author(jack).title("Happy coding").content("Have some fung with spring boot support for graphql ...").build();
        final var comment = Comment.builder().author(john).post(post).content("Lets agree to disagree :D").build();

        // TODO: should be part of another layer - service/manager
        userRepository.save(john);
        userRepository.save(jack);
        postReposiroty.save(post);
        commentReposiroty.save(comment);

        assertThat(graphQlTester).isNotNull();

        final var document = """
                query users {
                     users {
                         id
                         firstName
                         lastName
                         dateOfBird
                     }
                 }
                """;

        // when | then
        graphQlTester.document(document)
                .execute()
                .path("users")
                .entityList(User.class)
                .hasSize(2)
                .satisfies(users -> {
                    User u = users.stream().filter(user -> user.getFirstName().equals("Jack")).findFirst().get();
                    assertThat(u.getLastName()).isEqualTo("Daniels");
                    assertThat(u.getDateOfBird()).isEqualTo(LocalDate.of(1950, 5, 13));
                    assertThat(u.getPosts()).isEmpty();
                    assertThat(u.getComments()).isEmpty();
                });
    }

    // TBD: implement missing test, including pagination

}
