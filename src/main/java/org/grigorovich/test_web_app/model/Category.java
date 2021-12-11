package org.grigorovich.test_web_app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category implements Serializable {
    private Integer id;
    private String name;

    public Category withId(Integer id) {
        setId(id);
        return this;
    }

    public Category withName(String name) {
        setName(name);
        return this;
    }
}
