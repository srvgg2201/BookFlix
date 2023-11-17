package com.project.bookflix.dtos;

import com.project.bookflix.models.ResponseStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookMovieResponseDto {
    private ResponseStatus responseStatus;
    private int amount;
    private Long bookingId;
}
