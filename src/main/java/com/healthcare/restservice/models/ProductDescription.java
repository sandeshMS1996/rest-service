package com.healthcare.restservice.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ProductDescription {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ElementCollection()
    private List<String> composition;

    @Enumerated(EnumType.STRING)
    private DoseForm doseForm;


    public enum DoseForm {
        TABLET, LOTION, ORAL, OPHTHALMIC, INHALATION
    }
}
