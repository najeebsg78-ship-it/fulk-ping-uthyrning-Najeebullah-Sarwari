package se.fulkoping.uthyrning.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import se.fulkoping.uthyrning.dto.PersonRequest;
import se.fulkoping.uthyrning.dto.PersonResponse;
import se.fulkoping.uthyrning.dto.RentRequest;
import se.fulkoping.uthyrning.service.PersonService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.util.List;


@RestController
@RequestMapping("/personer")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    //Get
    @GetMapping
    public List<PersonResponse> getAllPersons(){
        return personService.getAll();
    }

    //Get by id
    @GetMapping("/{id}")
    public PersonResponse getPersonById(@PathVariable Long id){
        return personService.getById(id);
    }

    //Post
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PersonResponse createPerson(@Valid @RequestBody PersonRequest personRequest){
        return personService.create(personRequest);

    }

    //Put
    @PutMapping("/{id}")
    public PersonResponse updatePerson (@PathVariable Long id, @Valid @RequestBody PersonRequest personRequest){
        return personService.update(id, personRequest);
    }


    //Delete
    @Operation(summary = "Ta bort en person")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Personen har tagits bort"),
            @ApiResponse(responseCode = "404", description = "Personen hittades inte"),
            @ApiResponse(responseCode = "409", description = "Personen har saker kopplade")
    })
    @DeleteMapping ("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePerson (@PathVariable Long id){
        personService.delete(id);
    }

    //Post rentSak
    @Operation(summary = "Hyr en sak till en person")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Saken har hyrts ut till personen"),
            @ApiResponse(responseCode = "404", description = "Saken hittades inte"),
            @ApiResponse(responseCode = "409", description = "Saken är redan uthyrd"),
            @ApiResponse(responseCode = "201", description = "Saken har hyrts ut"),
            @ApiResponse(responseCode = "400", description = "datum saknas eller slutdatum före startdatum")
    })
    @PostMapping("/{personId}/saker/{sakId}")
    @ResponseStatus(HttpStatus.CREATED)
    public PersonResponse rentSak (@PathVariable Long personId, @PathVariable  Long sakId, @Valid @RequestBody RentRequest rentRequest){
        return personService.rentSak(personId, sakId, rentRequest);
    }

    //Delete removeSak
    @DeleteMapping("/{personId}/saker/{sakId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeSak(@PathVariable Long personId, @PathVariable  Long sakId){
        personService.removeSak(personId, sakId);
    }




}
