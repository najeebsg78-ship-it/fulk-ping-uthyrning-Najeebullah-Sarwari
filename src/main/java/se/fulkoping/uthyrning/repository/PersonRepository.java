package se.fulkoping.uthyrning.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.fulkoping.uthyrning.dto.PersonResponse;
import se.fulkoping.uthyrning.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {

    boolean existsBySakerId(Long id);

    PersonResponse findPersonById(Long id);
}
