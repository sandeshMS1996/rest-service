package com.healthcare.restservice.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenerationTime;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long ID;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "is_Active", nullable = false)
    @ColumnDefault("true")
    @org.hibernate.annotations.Generated(GenerationTime.INSERT)
    private boolean activeCategory;

    @ManyToMany
    @Column(nullable = false)
    @JsonIgnore
    private List<ProductCompany> productCompanyList;

    private double discount;

    @CreationTimestamp
    private LocalDateTime dateAdded;

    public Category(Long categoryId) {
        this.ID = categoryId;
    }
}