package uz.pdp.bazar.entity;

import jakarta.persistence.*;
import lombok.*;

@SqlResultSetMapping(
        name = "EmployeeDtoMapping",
        classes = {
                @ConstructorResult(
                        targetClass = Test.class,
                        columns = {
                                @ColumnResult(name = "productId", type = Integer.class),
                                @ColumnResult(name = "quantity", type = Double.class),
                                @ColumnResult(name = "price", type = Double.class)
                        }
                )
        }
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Test {

    @Id
    private Integer productId;

    private Double quantity;

    private Double price;

}
