package uz.pdp.bazar.model.request;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDto {


    private Integer id;

    @Column(nullable = false)
    private String name;

    private Integer parentCategoryId;
}
