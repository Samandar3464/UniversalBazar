package uz.pdp.bazar.model.request;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MarketDto {

    private Integer id;

    @Column(nullable = false ,unique = true)
    private String name;

    private Integer userId;

    private boolean active;
    private long longitude;
    private long latitude;

}
