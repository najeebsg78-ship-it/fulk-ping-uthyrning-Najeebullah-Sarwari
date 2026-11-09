package se.fulkoping.uthyrning.service;

import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.fulkoping.uthyrning.dto.PersonRequest;
import se.fulkoping.uthyrning.dto.PersonResponse;
import se.fulkoping.uthyrning.dto.RentRequest;
import se.fulkoping.uthyrning.exception.ConflictException;
import se.fulkoping.uthyrning.exception.NotFoundException;
import se.fulkoping.uthyrning.model.Person;
import se.fulkoping.uthyrning.model.Sak;
import se.fulkoping.uthyrning.repository.PersonRepository;
import se.fulkoping.uthyrning.repository.SakRepository;

import java.util.List;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final SakRepository sakRepository;

    public PersonService(PersonRepository personRepository, SakRepository sakRepository) {
        this.personRepository = personRepository;
        this.sakRepository = sakRepository;
    }

    //findPerson
    private Person findPerson(Long id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("person med id " + id + " fanns inte"));

    }

    //toResponse
    private PersonResponse toResponse(Person person) {
        return new PersonResponse(
                person.getId(),
                person.getName(),
                person.getMail()
        );
    }

    //getAll
    @Transactional(readOnly = true)
    public List<PersonResponse> getAll() {
        return personRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    //getById
    @Transactional(readOnly = true)
    public PersonResponse getById(Long id) {
        Person person = findPerson(id);
        PersonResponse personResponse = toResponse(person);
        return personResponse;
    }

    //create
    @Transactional
    public PersonResponse create(PersonRequest personRequest) {
        Person person = new Person(
                personRequest.name(),
                personRequest.mail()
        );
        Person savedPerson = personRepository.save(person);
        return toResponse(savedPerson);
    }

    //update
    @Transactional
    public PersonResponse update(Long id, PersonRequest personRequest) {
        Person person = findPerson(id);
        person.setName(personRequest.name());
        person.setMail(personRequest.mail());
        Person savedPerson = personRepository.save(person);
        return toResponse(savedPerson);
    }

    //delete
    @Transactional
    public void delete(Long id) {
        Person person = findPerson(id);

        if (!person.getSaker().isEmpty()) {
            throw new ConflictException("Personen med id " + id + " har saker kopplade till sig och kan inte tas bort");
        }

        personRepository.delete(person);
    }

    //rentSak
    @Transactional
    public PersonResponse rentSak(Long personId, Long sakId, @Valid RentRequest rentRequest) {

        Person person = findPerson(personId);

        Sak sak = sakRepository.findById(sakId)
                .orElseThrow(() -> new NotFoundException("saken med id: " + sakId + " fanns inte"));

        if (personRepository.existsBySakerId(sakId)) {
            throw new ConflictException("Saken med id: " + sakId + " är uthyrd till person med id: " + personId);
        } else {
            person.getSaker().add(sak);
        }

        return toResponse(person);

    }

    //removeSak
    @Transactional
    public void removeSak(Long personId, Long sakId) {
        Person person = findPerson(personId);
        Sak sak = sakRepository.findById(sakId).orElseThrow(
                () -> new NotFoundException("(\"saken med id: \" + sakId + \" fanns inte\")")
        );

        if (!person.getSaker().contains(sak)) {
            throw new NotFoundException("saken med id: " + sakId + " fanns inte hos personen");
        }

        person.getSaker().remove(sak);

    }



}
