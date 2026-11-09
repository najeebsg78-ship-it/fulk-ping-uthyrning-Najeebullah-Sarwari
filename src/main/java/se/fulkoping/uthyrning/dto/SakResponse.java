package se.fulkoping.uthyrning.dto;

import java.math.BigDecimal;

public record SakResponse(

        Long id,
        String name,
        String description,
        BigDecimal price


) {


}
