package uz.pdp.bazar.model.response;

import lombok.*;
import uz.pdp.bazar.entity.Product;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponseList {

    private List<ProductResponse> products;

    private long totalElement;
    private int totalPage;
    private int size;
}
