package com.healthcare.restservice.models;

import com.sun.istack.NotNull;
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

   @Column(nullable = false)
   private String username;

   private Double appliedDiscount;

   @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
   private Product product;

   private Integer count;

   @CreationTimestamp
   private LocalDateTime purchaseDate;

   private Double cost;

   @Enumerated(EnumType.STRING)
   private PaymentModes paymentMode;

   public Purchase updateProduct(Product newProduct){
      if(newProduct != null) {
         System.out.println(newProduct);
         System.out.println(newProduct);
         this.product = newProduct;
         this.appliedDiscount = newProduct.getDiscount();
         this.cost = newProduct.getPrice();
      } else this.product = null;

      return this;
   }

   public Purchase(Long id) {
      this.id = id;
   }
}
