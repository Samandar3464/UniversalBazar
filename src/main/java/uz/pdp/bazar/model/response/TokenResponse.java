package uz.pdp.bazar.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TokenResponse {
    private String accessToken;

    private UserResponseDto userResponseDto;
     public TokenResponse(String accessToken) {
          this.accessToken=accessToken;
     }
}
