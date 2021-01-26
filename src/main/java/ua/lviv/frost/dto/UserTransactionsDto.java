package ua.lviv.frost.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserTransactionsDto {


    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer id;

    private String message;

    @JsonProperty(value = "transaction_money_in_uah", access = JsonProperty.Access.READ_ONLY)
    private BigDecimal transactionMoneyInUaH;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime transactionTime;

}
