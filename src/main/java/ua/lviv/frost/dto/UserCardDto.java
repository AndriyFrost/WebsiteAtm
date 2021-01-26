package ua.lviv.frost.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserCardDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer id;

    @JsonProperty(value = "card_code")
    private String cardCode;

    private String name;

    private String surname;

    @JsonProperty(value = "phone_number")
    private String phoneNumber;

    @JsonProperty(value = "money_in_uah",access = JsonProperty.Access.READ_ONLY)
    private BigDecimal moneyInUaH;
}
