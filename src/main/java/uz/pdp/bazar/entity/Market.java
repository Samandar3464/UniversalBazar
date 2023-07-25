package uz.pdp.bazar.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;

    private boolean delete;

    private boolean active;

    private long longitude;

    private long latitude;

    private LocalDate activeDay;

    public static Market from(MarketDto dto, User user) {
        return Market.builder()
                .name(dto.getName())
                .user(user)
                .longitude(dto.getLongitude())
                .latitude(dto.getLatitude())
                .activeDay(dto.getActiveDay())
                .delete(false)
                .active(true)
                .build();
    }
}