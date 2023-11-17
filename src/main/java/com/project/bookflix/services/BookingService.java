package com.project.bookflix.services;

import com.project.bookflix.exceptions.InvalidShowException;
import com.project.bookflix.exceptions.InvalidUserException;
import com.project.bookflix.exceptions.ShowSeatNotAvailableException;
import com.project.bookflix.models.*;
import com.project.bookflix.repositories.ShowRepository;
import com.project.bookflix.repositories.ShowSeatRepository;
import com.project.bookflix.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
    private UserRepository userRepository;
    private ShowRepository showRepository;
    private ShowSeatRepository showSeatRepository;
    private PriceCalculator priceCalculator;
    @Autowired
    BookingService(UserRepository userRepository, ShowRepository showRepository, ShowSeatRepository showSeatRepository) {
        this.userRepository = userRepository;
        this.showRepository = showRepository;
        this.showSeatRepository = showSeatRepository;
        this.priceCalculator = priceCalculator;
    }
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Booking bookMovie(Long userId, Long showId, List<Long> showSeatIds) throws Exception {
        /*
        ADVANCED WAY:
        1. get user from user-id
        2. get show from show-id
        3. get show-seats from showseatsid
        4. check if all show seats are available.
        -------TAKE DB LOCK---------
        5. If yes, mark show seat status as BLOCKED.
        6. Save the status in db.
        -------RELEASE DB LOCK--------
        7. Create Booking Object (go to Payment page)
        8. Return the booking Object

        MORE NAIVE :
        -------TAKE DB LOCK------
        1. get user from user-id
        2. get show from show-id
        3. get show-seats from showseatsid
        4. check if all show seats are available.
        -------TAKE DB LOCK---------
        5. If yes, mark show seat status as BLOCKED.
        6. Save the status in db.
        -------RELEASE DB LOCK--------
        7. Create Booking Object (go to Payment page)
        8. Return the booking Object
        -------RELEASE LOCK------
         */
        // 1.
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isEmpty()) throw new InvalidUserException("User Not Found");
        User user = optionalUser.get();
        //2.
        Optional<Show> optionalShow = showRepository.findById(showId);
        if(optionalShow.isEmpty()) throw new InvalidShowException("Invalid Show");
        Show show = optionalShow.get();
        //3.
        List<ShowSeat> showSeats = showSeatRepository.findAllById(showSeatIds);
        //4.
        for(ShowSeat showSeat : showSeats) {
            if(!showSeat.getShowSeatStatus().equals(ShowSeatStatus.AVAILABLE)) {
                throw new ShowSeatNotAvailableException("Show Seat Not Available");
            }
        }
        List<ShowSeat> savedShowSeats = new ArrayList<>();
        //5. and 6.
        for(ShowSeat showSeat : showSeats) {
            showSeat.setShowSeatStatus(ShowSeatStatus.BLOCKED);
            savedShowSeats.add(showSeatRepository.save(showSeat));
        }

        //7.
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setShow(show);
        booking.setCreatedAt(new Date());
        booking.setLastModified(new Date());
        booking.setBookingStatus(BookingStatus.PENDING);
        booking.setPayments(new ArrayList<>());
        booking.setAmount(priceCalculator.calculatePrice(savedShowSeats, show));

        return booking;
    }
}
