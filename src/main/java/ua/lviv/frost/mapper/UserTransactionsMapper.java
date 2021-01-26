package ua.lviv.frost.mapper;

import org.springframework.stereotype.Component;
import ua.lviv.frost.dto.UserTransactionsDto;
import ua.lviv.frost.entity.UserTransactions;

@Component
public class UserTransactionsMapper extends AbstractMapper<UserTransactions, UserTransactionsDto> {

    @Override
    public UserTransactionsDto toDto(UserTransactions userTransactions) {
        return new UserTransactionsDto().setId(userTransactions.getId())
                .setMessage(userTransactions.getMessage())
                .setTransactionMoneyInUaH(userTransactions.getTransactionMoneyInUaH())
                .setTransactionTime(userTransactions.getTransactionTime());
    }

    @Override
    public UserTransactions toEntity(UserTransactionsDto userTransactionsDto) {
        return new UserTransactions();
    }
}
