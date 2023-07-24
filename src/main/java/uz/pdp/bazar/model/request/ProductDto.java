package uz.pdp.bazar.model.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ProductDto {


    private Integer id;

    private String name;

    private String description;

    private Integer measurementId;

    private Integer branchId;

    private double price;
}
