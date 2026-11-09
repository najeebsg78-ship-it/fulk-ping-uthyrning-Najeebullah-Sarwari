package se.fulkoping.uthyrning.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record RentRequest(

        @FutureOrPresent
        @NotNull
        LocalDate startDate,

        @NotNull
        LocalDate endDate



) {

    @AssertTrue(message = "slutdatum får ej vara före startdatum")
    @JsonIgnore
    public boolean isEndDateValid () {

        if (startDate == null || endDate == null) {
            return true;
        }
        return !endDate.isBefore(startDate);

    }


}


