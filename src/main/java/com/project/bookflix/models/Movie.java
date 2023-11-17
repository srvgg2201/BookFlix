package com.project.bookflix.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@Entity
public class Movie extends BaseModel {
    private String name;
    @ManyToMany
    private List<Actor> actors;
    private String duration;
    private double rating;
    @Enumerated(EnumType.ORDINAL)
    private List<Genre> genre;
}
