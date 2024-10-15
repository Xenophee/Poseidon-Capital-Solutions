package com.nnk.springboot.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "trade")
@Getter
@Setter
@NoArgsConstructor
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer TradeId;
    private String account;
    private String type;
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

    // Custom constructor
    public Trade(String account, String type, Double buyQuantity, Double sellQuantity, Double buyPrice, Double sellPrice, LocalDateTime tradeDate, String security, String status, String trader, String benchmark, String book, String creationName, LocalDateTime creationDate, String revisionName, LocalDateTime revisionDate, String dealName, String dealType, String sourceListId, String side) {
        this.account = account;
        this.type = type;
        this.buyQuantity = buyQuantity;
        this.sellQuantity = sellQuantity;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.tradeDate = tradeDate;
        this.security = security;
        this.status = status;
        this.trader = trader;
        this.benchmark = benchmark;
        this.book = book;
        this.creationName = creationName;
        this.creationDate = creationDate;
        this.revisionName = revisionName;
        this.revisionDate = revisionDate;
        this.dealName = dealName;
        this.dealType = dealType;
        this.sourceListId = sourceListId;
        this.side = side;
    }

    public Trade(String account, String type) {
        this.account = account;
        this.type = type;
    }
}