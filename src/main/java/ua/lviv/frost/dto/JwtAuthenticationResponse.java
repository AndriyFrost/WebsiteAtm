package ua.lviv.frost.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtAuthenticationResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private Integer userId;

    public JwtAuthenticationResponse(String accessToken, Integer id) {
        this.accessToken = accessToken;
        this.userId = id;
    }
}
