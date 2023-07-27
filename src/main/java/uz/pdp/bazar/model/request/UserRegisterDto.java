package uz.pdp.bazar.model.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.bazar.enums.Gender;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterDto {

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
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate birthDate;

    private Gender gender;

    private String romeName;

    private Integer marketId;

}
