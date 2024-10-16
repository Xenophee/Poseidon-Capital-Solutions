package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "bidlist")
@Getter
@Setter
@NoArgsConstructor
public class BidList {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer bidListId;

    @NotEmpty(message = "Account is mandatory")
    @Size(min = 1, max = 30, message = "Account must be between 1 and 30 characters")
    private String account;

    @NotEmpty(message = "Type is mandatory")
    @Size(min = 1, max = 30, message = "Type must be between 1 and 30 characters")
    private String type;

    @NotNull(message = "Bid quantity is mandatory") // Assure que la quantité n'est pas nulle
    @Min(value = 0, message = "Bid quantity must be greater than or equal to zero")
    private Double bidQuantity;


    private Double askQuantity;
    private Double bid;
    private Double ask;
    private String benchmark;
    private LocalDateTime bidListDate;
    private String commentary;
    private String security;
    private String status;
    private String trader;
    private String book;
    private String creationName;
    private LocalDateTime creationDate;
    private String revisionName;
    private LocalDateTime revisionDate;
    private String dealName;
    private String dealType;
    private String sourceListId;
    private String side;


    public BidList(String account, String type, Double bidQuantity) {
        this.account = account;
        this.type = type;
        this.bidQuantity = bidQuantity;
    }
}