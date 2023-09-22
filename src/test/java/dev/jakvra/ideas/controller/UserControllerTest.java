package dev.jakvra.ideas.controller;

import dev.jakvra.ideas.conig.GraphQlConfig;
import dev.jakvra.ideas.persistance.domain.User;
import dev.jakvra.ideas.persistance.repository.CommentReposiroty;
import dev.jakvra.ideas.persistance.repository.PostReposiroty;
import dev.jakvra.ideas.persistance.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.graphql.test.tester.GraphQlTester;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@DisplayName("User Controller Test")
@Import(GraphQlConfig.class)
@GraphQlTest(UserController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserControllerTest {

    @Autowired
    GraphQlTester graphQlTester;

    @MockBean
    private UserRepository userRepository;
    @MockBean
    private PostReposiroty postReposiroty;
    @MockBean
    private CommentReposiroty commentReposiroty;

    @Test
    void users() {

        // given
        when(userRepository.findAll()).thenReturn(userList());

        // language=GraphQL
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
                .hasSize(3);
    }

    private static List<User> userList() {
        return List.of(
                new User(0L, "John", "Doe", LocalDate.of(1990, 1, 1), null, null),
                new User(1L, "Jane", "Doe", LocalDate.of(1989, 2, 21), null, null),
                new User(2L, "Jack", "Doe", LocalDate.of(1970, 3, 15), null, null));
    }

    @Test
    void user() {

        // given
        final var id = 2L;
        when(userRepository.findById(id)).thenReturn(Optional.of(userList().get((int) id)));

        // language=GraphQL
        final var document = """
                query user($id: ID!) {
                    user(id: $id) {
                        id
                        firstName
                        lastName
                        dateOfBird
                    }
                }
                """;

        // when | then
        graphQlTester.document(document)
                .variable("id", id)
                .execute()
                .path("user")
                .entity(User.class)
                .satisfies(user -> {
                    assertThat(user.getFirstName()).isEqualTo("Jack");
                    assertThat(user.getLastName()).isEqualTo("Doe");
                    assertThat(user.getDateOfBird()).isEqualTo(LocalDate.of(1970, 3, 15));
                    assertThat(user.getPosts()).isEmpty();
                    assertThat(user.getComments()).isEmpty();
                });
    }

    // TBD: implement missing tests
}