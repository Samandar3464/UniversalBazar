package uz.pdp.bazar.model.response;

import lombok.*;
import uz.pdp.bazar.entity.Market;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MarketResponseDto {

    private Integer id;

    private String name;

    public static MarketResponseDto from(Market market) {
        return MarketResponseDto.builder()
                .id(market.getId())
                .name(market.getName())
                .build();
    }
}