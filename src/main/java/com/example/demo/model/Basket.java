// src/main/java/com/example/demo/model/Basket.java
package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Basket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    private List<User> users = new ArrayList<>();

    @OneToMany(mappedBy = "basket", cascade = CascadeType.ALL)
    private List<BasketItem> items = new ArrayList<>();

    public User getUser() {
        return this.users.get(0);
    }
}