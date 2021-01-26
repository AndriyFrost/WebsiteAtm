package ua.lviv.frost.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class LoginException extends RuntimeException {

    public LoginException() {
        super("Invalid card code or PIN-code");
    }
}
