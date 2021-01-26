package ua.lviv.frost.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class HaveNotEnoughMoneyException extends RuntimeException {

    public HaveNotEnoughMoneyException() {
        super("Not enough money on the card to make a transaction.");
    }
}
