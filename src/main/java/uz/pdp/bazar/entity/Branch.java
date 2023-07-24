package uz.pdp.bazar.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import uz.pdp.bazar.model.request.BranchDto;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Branch {

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

    public static Branch from(BranchDto branch,User user) {
        return Branch.builder()
                .name(branch.getName())
                .user(user)
                .delete(false)
                .active(true)
                .build();
    }
}