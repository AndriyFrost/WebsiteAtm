package ua.lviv.frost.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class LoginRequest {

    @NotBlank
    @JsonProperty(value = "card_code")
    @Pattern(regexp = "^\\d{16}$", message = "Invalid card code format")
    private String cardCode;

    @NotBlank
    @JsonProperty(value = "pin_code")
    @Pattern(regexp = "^\\d{4}$", message = "Invalid pin code format")
    private String pinCode;

}
