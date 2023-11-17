package com.project.bookflix.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ShowSeatType extends BaseModel {
    @ManyToOne
    private Show show;
    @ManyToOne
    @Enumerated(EnumType.ORDINAL)
    private SeatType seatType;
    private int price;


}
