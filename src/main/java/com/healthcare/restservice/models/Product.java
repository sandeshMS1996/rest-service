package com.healthcare.restservice.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Entity
@ToString
@NoArgsConstructor
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

    private double discount;

    @CreationTimestamp
    private LocalDateTime dateAdded;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category", nullable = false)
    private Category category;

    @Column(length = 1000, nullable = true)
    private String description;

    private String imageName;

    private Integer stock;

    @OneToOne(cascade = CascadeType.ALL)
    private ProductDescription productDescription;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private ProductCompany productCompany;

    @ColumnDefault("true")
    @org.hibernate.annotations.Generated(GenerationTime.INSERT)
    private boolean isNotDisabled;

    public Product(Long id) {
        this.id = id;
    }


}
