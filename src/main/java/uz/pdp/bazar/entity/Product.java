package uz.pdp.bazar.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String description;

    private boolean deleted;

    private double price;

    @ManyToOne
    private Measurement measurement;

    @JsonIgnore
    @ManyToOne
    private Branch branch;
}
