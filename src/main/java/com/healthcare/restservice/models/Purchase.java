package com.healthcare.restservice.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Purchase {
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Long id;

   private String username;

   @OneToOne
   private Product product;

   private Integer count;

   @CreationTimestamp
   private LocalDateTime purchaseDate;

   private Double cost;

   @Enumerated(EnumType.STRING)
   private PaymentModes paymentMode;
}
