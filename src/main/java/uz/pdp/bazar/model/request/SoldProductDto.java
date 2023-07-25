package uz.pdp.bazar.model.request;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SoldProductDto {

    private UUID id;

    private Integer productId;

    private double quantity;

    private double price;

    private LocalDateTime soldDate;

    private Integer marketId;

    private Integer soldUserId;
}
