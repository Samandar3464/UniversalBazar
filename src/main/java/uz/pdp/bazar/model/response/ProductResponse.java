package uz.pdp.bazar.model.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;
import uz.pdp.bazar.entity.Market;
import uz.pdp.bazar.entity.Product;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {

    private Integer id;

    private String name;

    private double quantity;

    private String description;

    private boolean active;

    private double price;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createdDate;

    private String measurement;

    private Market market;

    private List<String> photos;

    public static ProductResponse from(Product product,List<String> photos) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .quantity(product.getQuantity())
                .description(product.getDescription())
                .active(product.isActive())
                .price(product.getPrice())
                .createdDate(product.getCreatedDate())
                .measurement(product.getMeasurement().getName())
                .market(product.getMarket())
                .photos(photos)
                .build();
    }
}
