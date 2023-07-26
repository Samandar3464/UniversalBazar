package uz.pdp.bazar.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.bazar.entity.Product;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SoldProductTotalResponse {

    private Integer productId;

    private String  productName;

    private double totalSumma;

    private double totalQuantity;

    public static SoldProductTotalResponse from(Product product , double totalSumma ,double totalQuantity){
        return SoldProductTotalResponse.builder()
                .productId(product.getId())
                .productName(product.getName())
                .totalSumma(totalSumma)
                .totalQuantity(totalQuantity)
                .build();
    }
}
