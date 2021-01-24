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
import java.util.List;

@Entity(name = "company")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductCompany {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String name;

    @CreationTimestamp
    private LocalDateTime dateAdded;

    @NotNull
    @Column(nullable = false)
    private String GSTNumber;

    @ColumnDefault("true")
    @org.hibernate.annotations.Generated(GenerationTime.INSERT)
    private Boolean isEnabled;


    private double discount;

    @ManyToMany(mappedBy = "productCompanyList")
    @JsonIgnore
    private List<Category> categories;

    public ProductCompany(Long companyID) {
        this.id = companyID;
    }


}
