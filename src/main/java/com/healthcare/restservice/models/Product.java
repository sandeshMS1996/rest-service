package com.healthcare.restservice.models;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    @Column(nullable = false)
    private String name;

    @NotNull
    @Column(nullable = false)
    private double price;

    @CreationTimestamp
    private LocalDateTime dateAdded;

    @ManyToOne
    @JoinColumn(name = "category", nullable = false)
    private Category category;

    @Column(length = 1000, nullable = true)
    private String description;

    private String imageName;

    @ManyToOne
    private ProductCompany productCompany;

    private Integer stock;

    private boolean isNotDisabled;

    public Product(Long id) {
        this.id = id;
    }

    @PrePersist
    public void enableProduct() {
        this.isNotDisabled = true;
    }
}
