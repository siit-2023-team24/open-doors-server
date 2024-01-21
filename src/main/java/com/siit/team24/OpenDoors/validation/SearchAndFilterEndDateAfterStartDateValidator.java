package com.siit.team24.OpenDoors.validation;

import com.siit.team24.OpenDoors.dto.searchAndFilter.SearchAndFilterDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.sql.Timestamp;

public class SearchAndFilterEndDateAfterStartDateValidator implements ConstraintValidator<SearchAndFilterEndDateAfterStartDate, SearchAndFilterDTO> {
    @Override
    public boolean isValid(SearchAndFilterDTO dto, ConstraintValidatorContext context) {
        if (dto == null) {
            return true; // null values are handled by @NotNull
        }

        Timestamp startDate = dto.getStartDate();
        Timestamp endDate = dto.getEndDate();

        return startDate == null || endDate == null || endDate.after(startDate);
    }
}