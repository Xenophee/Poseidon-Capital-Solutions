package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "curvepoint")
@Getter
@Setter
@NoArgsConstructor
public class CurvePoint {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull(message = "Curve ID is a mandatory")
    @Min(value = 1, message = "Curve ID must be greater than or equal to 1")
    private Integer curveId;

    private LocalDateTime asOfDate;

    @NotNull(message = "Term is a mandatory")
    @Positive(message = "Term must be greater than 0")
    private Double term;

    @NotNull(message = "Value is a mandatory")
    private Double value;

    private LocalDateTime creationDate;


    public CurvePoint(Integer curveId, Double term, Double value) {
        this.curveId = curveId;
        this.term = term;
        this.value = value;
    }
}