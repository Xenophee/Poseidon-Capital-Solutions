package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "rating")
@Data
@NoArgsConstructor
public class RatingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Size(max = 125, message = "Moody's rating must be at most 125 characters.")
    private String moodysRating;

    @Size(max = 125, message = "S&P rating must be at most 125 characters.")
    private String sandPRating;

    @Size(max = 125, message = "Fitch rating must be at most 125 characters.")
    private String fitchRating;

    @NotNull(message = "Order number is a mandatory.")
    @Min(value = 1, message = "Order number must be greater than or equal to 1.")
    @Max(value = 255, message = "Order number must be less than or equal to 255.")
    private Integer orderNumber;


    public RatingEntity(String moodysRating, String sandPRating, String fitchRating, Integer orderNumber) {
        this.moodysRating = moodysRating;
        this.sandPRating = sandPRating;
        this.fitchRating = fitchRating;
        this.orderNumber = orderNumber;
    }
}