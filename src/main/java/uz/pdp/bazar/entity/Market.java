package uz.pdp.bazar.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import uz.pdp.bazar.model.request.MarketDto;

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
    private User user;

    private boolean delete;

    private boolean active;

    public static Market from(MarketDto branch, User user) {
        return Market.builder()
                .name(branch.getName())
                .user(user)
                .delete(false)
                .active(true)
                .build();
    }
}