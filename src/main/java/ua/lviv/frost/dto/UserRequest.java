package ua.lviv.frost.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.Pattern;

@Data
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserRequest {

    @ToString.Exclude
    @Pattern(regexp = "^\\d{4}$", message = "Invalid pin code format")
    @JsonProperty(value = "pin_code",access = JsonProperty.Access.WRITE_ONLY)
    private String pinCode;

    private String name;

    private String surname;

    @JsonProperty(value = "phone_number")
    @Pattern(regexp = "^\\d{10}$", message = "Invalid phone number format")
    private String phoneNumber;
}
