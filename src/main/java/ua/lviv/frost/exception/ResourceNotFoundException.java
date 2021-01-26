package ua.lviv.frost.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.function.Supplier;

@Getter
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    private String resource;
    private String fieldName;
    private Object fieldValue;

    public ResourceNotFoundException(String resource, String fieldName, Object fieldValue) {
        super(String.format("%s with %s '%s' not found", resource, fieldName, fieldValue));
        this.resource = resource;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public static Supplier<? extends ResourceNotFoundException> userCardSupplier(Integer userCardId) {
        return () -> new ResourceNotFoundException("user card", "id", userCardId);
    }

    public static Supplier<? extends ResourceNotFoundException> cardCodeSupplier(String codeCard) {
        return () -> new ResourceNotFoundException("card", "code", codeCard);
    }

    public static Supplier<? extends ResourceNotFoundException> transactionSupplier(Integer transactionId) {
        return () -> new ResourceNotFoundException("transaction", "id", transactionId);
    }
}
