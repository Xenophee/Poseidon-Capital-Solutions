package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "rulename")
@Getter
@Setter
@NoArgsConstructor
public class RuleName {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;


    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Description is mandatory")
    private String description;

    @NotBlank(message = "Json is mandatory")
    private String json;

    @NotBlank(message = "Template is mandatory")
    private String template;

    @NotBlank(message = "SqlStr is mandatory")
    private String sqlStr;

    @NotBlank(message = "SqlPart is mandatory")
    private String sqlPart;


    public RuleName(String name, String description, String json, String template, String sqlStr, String sqlPart) {
        this.name = name;
        this.description = description;
        this.json = json;
        this.template = template;
        this.sqlStr = sqlStr;
        this.sqlPart = sqlPart;
    }
}