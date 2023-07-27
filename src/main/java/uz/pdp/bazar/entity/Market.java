package uz.pdp.bazar.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import uz.pdp.bazar.model.request.MarketDto;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Market {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false,unique = true)
    private String name;

    private boolean delete;

    private boolean active;

    private long longitude;

    private long latitude;

    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate activeDay;

    public static Market from(MarketDto dto) {
        return Market.builder()
                .name(dto.getName())
                .longitude(dto.getLongitude())
                .latitude(dto.getLatitude())
                .activeDay(dto.getActiveDay())
                .delete(false)
                .active(true)
                .build();
    }
}