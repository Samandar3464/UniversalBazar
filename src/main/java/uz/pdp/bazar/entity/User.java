package uz.pdp.bazar.entity;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.pdp.bazar.enums.Gender;
import uz.pdp.bazar.model.request.UserRegisterDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    private String fullName;

    @NotBlank
    @Size(min = 9, max = 9)
    private String phoneNumber;

    @NotBlank
    @Size(min = 6)
    private String password;

    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate birthDate;

    private String fireBaseToken;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @ManyToOne(fetch = FetchType.EAGER)
    private Role role;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime registeredDate;

    private boolean isBlocked;

    private boolean deleted;

    private Integer verificationCode;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Market market;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        return authorityList;
    }

    @Override
    public String getUsername() {
        return phoneNumber;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isBlocked;
    }

    public static User from(UserRegisterDto userRegisterDto) {
        return User.builder()
                .fullName(userRegisterDto.getFullName())
                .phoneNumber(userRegisterDto.getPhoneNumber())
                .registeredDate(LocalDateTime.now())
                .gender(userRegisterDto.getGender())
                .birthDate(userRegisterDto.getBirthDate())
                .isBlocked(true)
                .deleted(false)
                .build();
    }
}
