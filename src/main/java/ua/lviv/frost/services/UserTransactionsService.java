package ua.lviv.frost.services;

import org.springframework.data.domain.PageRequest;
import ua.lviv.frost.entity.UserCard;
import ua.lviv.frost.entity.UserTransactions;

import java.math.BigDecimal;
import java.util.List;

public interface UserTransactionsService {

    List<UserTransactions> findAllTransactionsByUser(Integer userCardId, PageRequest pageable);

    UserTransactions findTransaction(Integer userCardId, Integer transactionId);

    void putMoneyToCard(UserCard userCard, BigDecimal transactionMoneyInUaH);

    void putMoneyToAnotherCard(UserCard userCard, BigDecimal transactionMoneyInUaH, String anotherCodeCard);

    void getMoneyFromCard(UserCard userCard, BigDecimal transactionMoneyInUaH);
}
