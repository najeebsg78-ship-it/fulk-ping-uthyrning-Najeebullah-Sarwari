package se.fulkoping.uthyrning.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import se.fulkoping.uthyrning.dto.SakRequest;
import se.fulkoping.uthyrning.dto.SakResponse;
import se.fulkoping.uthyrning.service.SakService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.util.List;

@RestController
@RequestMapping("/saker")
public class SakController {

    private final SakService sakService;

    public SakController(SakService sakService) {
        this.sakService = sakService;
    }

    //Get
    @GetMapping
    public List<SakResponse> getAllSaker(){
        return sakService.getAll();
    }

    //Get by id
    @GetMapping("/{id}")
    public SakResponse getSakById (@PathVariable Long id){
        return sakService.getById(id);
    }

    //Post
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SakResponse createSak (@Valid @RequestBody SakRequest sakRequest) {
        return sakService.create(sakRequest);
    }

    //Put
    @PutMapping ("/{id}")
    public SakResponse updateSak (@PathVariable Long id, @Valid @RequestBody SakRequest sakRequest){
        return sakService.update(id, sakRequest);
    }

    //Delete
    @Operation(summary = "Ta bort en sak")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Saken har tagits bort"),
            @ApiResponse(responseCode = "404", description = "Saken hittades inte"),
            @ApiResponse(responseCode = "409", description = "Saken är kopplad till en person")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSak(@PathVariable Long id ){
        sakService.delete(id);
    }


}
