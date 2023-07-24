package uz.pdp.bazar.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.bazar.entity.Role;
import uz.pdp.bazar.entity.User;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {

    private Integer id;

    private String fullName;

    private String phoneNumber;

    private Role role;

    private String birthDate;

    private String gender;

    private String registeredDate;


    public static UserResponseDto from(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole())
                .birthDate(user.getBirthDate().toString())
                .registeredDate(user.getRegisteredDate().toString())
                .gender(user.getGender().toString())
                .build();
    }
}
