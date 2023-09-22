package dev.jakvra.ideas.graphql;

import java.time.LocalDate;

public record UserInput(
        String firstName,
        String lastName,
        LocalDate dateOfBird
) {
}
