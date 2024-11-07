package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "curvepoint")
@Data
@NoArgsConstructor
public class CurvePointEntity {

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


    public CurvePointEntity(Integer curveId, Double term, Double value) {
        this.curveId = curveId;
        this.term = term;
        this.value = value;
    }
}