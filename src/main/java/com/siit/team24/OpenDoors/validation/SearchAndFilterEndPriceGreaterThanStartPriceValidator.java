package com.siit.team24.OpenDoors.validation;

import com.siit.team24.OpenDoors.dto.searchAndFilter.SearchAndFilterDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

class SearchAndFilterEndPriceGreaterThanStartPriceValidator implements ConstraintValidator<SearchAndFilterEndPriceGreaterThanStartPrice, SearchAndFilterDTO> {
    @Override
    public boolean isValid(SearchAndFilterDTO dto, ConstraintValidatorContext context) {
        if (dto == null) {
            return true; // null values are handled by @NotNull
        }

        Double startPrice = dto.getStartPrice();
        Double endPrice = dto.getEndPrice();

        return startPrice == null || endPrice == null || endPrice > startPrice;
    }
}