package uz.pdp.bazar.model.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
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

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime soldDate;

    private Integer marketId;

    private Integer soldUserId;
}
