package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "trade")
@Data
@NoArgsConstructor
public class TradeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer TradeId;

    @NotEmpty(message = "Account is mandatory")
    @Size(min = 1, max = 30, message = "Account must be between 1 and 30 characters")
    private String account;

    @NotEmpty(message = "Type is mandatory")
    @Size(min = 1, max = 30, message = "Type must be between 1 and 30 characters")
    private String type;

    @Min(value = 1, message = "Buy quantity must be greater than zero")
    private Double buyQuantity;


    private Double sellQuantity;
    private Double buyPrice;
    private Double sellPrice;
    private LocalDateTime tradeDate;
    private String security;
    private String status;
    private String trader;
    private String benchmark;
    private String book;
    private String creationName;
    private LocalDateTime creationDate;
    private String revisionName;
    private LocalDateTime revisionDate;
    private String dealName;
    private String dealType;
    private String sourceListId;
    private String side;

    public TradeEntity(String account, String type, Double buyQuantity) {
        this.account = account;
        this.type = type;
        this.buyQuantity = buyQuantity;
    }
}