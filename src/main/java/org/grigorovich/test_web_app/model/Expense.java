package org.grigorovich.test_web_app.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Expense implements Serializable {
    private int id;
    private String name;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate created_at;
    private Integer categoryInt;
    private Category category;
    private BigDecimal amount;

    public Expense(int id, String name, LocalDate created_at, BigDecimal amount) {
        this.id = id;
        this.name = name;
        this.created_at = created_at;
        this.amount = amount;
    }

    public Expense(String name, LocalDate created_at, Integer categoryInt, BigDecimal amount) {
        this.name = name;
        this.created_at = created_at;
        this.categoryInt = categoryInt;
        this.amount = amount;
    }

    public Expense(String name, LocalDate created_at, BigDecimal amount) {
        this.name = name;
        this.created_at = created_at;
        this.amount = amount;
    }

    public Expense(int id, String name, LocalDate created_at, Integer categoryInt, BigDecimal amount) {
        this.id = id;
        this.name = name;
        this.created_at = created_at;
        this.categoryInt = categoryInt;
        this.amount = amount;
    }

    public Expense withId(int id) {
        setId(id);
        return this;
    }

    public Expense withName(String name) {
        setName(name);
        return this;
    }

    public Expense withCreated_at(LocalDate created_at) {
        setCreated_at(created_at);
        return this;
    }

    public Expense withCategory(Category category) {
        setCategory(category);
        return this;
    }

    public Expense withCategoryInt(Integer categoryInt) {
        setCategoryInt(categoryInt);
        return this;
    }

    public Expense withAmount(BigDecimal amount) {
        setAmount(amount);
        return this;
    }
}
