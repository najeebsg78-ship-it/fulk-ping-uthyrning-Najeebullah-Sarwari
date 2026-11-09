package se.fulkoping.uthyrning.dto;

import jakarta.validation.constraints.NotBlank;

public record PersonResponse (
        Long id,

        @NotBlank
        String name,

        @NotBlank
        String mail

){
}
