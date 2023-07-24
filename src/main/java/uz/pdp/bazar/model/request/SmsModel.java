package uz.pdp.bazar.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SmsModel {

    @NotBlank
    @Size(min = 9,max = 9)
    private String mobilePhone;

    private String message;

    private int from;

    public  String callBackUrl;

}
