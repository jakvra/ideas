package dev.jakvra.ideas.person;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class PersonController {

    private final PersonRepository personRepository;

    @QueryMapping
    public List<Person> persons() {
        return personRepository.findAll();
    }

    @QueryMapping
    Optional<Person> person(@Argument Long id) {
        return personRepository.findById(id);
    }

    @MutationMapping
    public Person createPerson(@Argument PersonInput person) {
        return personRepository.save(Person.builder()
                .firstName(person.firstName())
                .lastName(person.lastName())
                .build());
    }

    @MutationMapping
    public Person deletePerson(@Argument Long id) {
        final Optional<Person> person = personRepository.findById(id);
        person.ifPresent(personRepository::delete);
        return person.orElse(null);
    }

}
