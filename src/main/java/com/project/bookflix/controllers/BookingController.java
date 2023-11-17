package com.project.bookflix.controllers;

import com.project.bookflix.dtos.BookMovieRequestDto;
import com.project.bookflix.dtos.BookMovieResponseDto;
import com.project.bookflix.models.Booking;
import com.project.bookflix.models.BookingStatus;
import com.project.bookflix.models.ResponseStatus;
import com.project.bookflix.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class BookingController {
    private BookingService bookingService;
    @Autowired
    BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }
    BookMovieResponseDto bookMovie(BookMovieRequestDto requestDto) {
        BookMovieResponseDto responseDto = new BookMovieResponseDto();
        try {
            Booking booking = bookingService.bookMovie(requestDto.getUserId(),
                    requestDto.getShowId(),
                    requestDto.getShowSeatIds());
            booking.setBookingStatus(BookingStatus.CONFIRMED);
            responseDto.setBookingId(booking.getId());
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);
        } catch (Exception e) {
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
            throw new RuntimeException(e);
        }
        return responseDto;
    }
}
