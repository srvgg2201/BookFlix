package com.project.bookflix.services;

import com.project.bookflix.models.Show;
import com.project.bookflix.models.ShowSeat;
import com.project.bookflix.models.ShowSeatType;
import com.project.bookflix.repositories.ShowSeatTypeRepository;

import java.util.List;

public class PriceCalculator {
    private ShowSeatTypeRepository showSeatTypeRepository;
    PriceCalculator(ShowSeatTypeRepository showSeatTypeRepository) {
        this.showSeatTypeRepository = showSeatTypeRepository;
    }
    public double calculatePrice(List<ShowSeat> showSeats, Show show) {
        List<ShowSeatType> showSeatTypes = showSeatTypeRepository.findAllByShow(show);
        double amount = 0;
        for(ShowSeat showSeat : showSeats) {
            for(ShowSeatType showSeatType : showSeatTypes) {
                if(showSeat.getSeat().getSeatType().equals(showSeatType.getSeatType())) {
                    amount += showSeatType.getPrice();
                }
            }
        }
        return amount;
    }
}
