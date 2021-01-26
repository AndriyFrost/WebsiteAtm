package ua.lviv.frost.services.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ua.lviv.frost.entity.UserCard;
import ua.lviv.frost.entity.UserTransactions;
import ua.lviv.frost.exception.ResourceNotFoundException;
import ua.lviv.frost.repository.UserTransactionsRepository;
import ua.lviv.frost.services.UserTransactionsService;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserTransactionsServiceImpl implements UserTransactionsService {

    private static final String PUT_MONEY_TO_CARD = "Card replenishment.";
    private static final String PUT_MONEY_TO__ANOTHER_CARD = "Replenished card: %s";
    private static final String GET_MONEY_FROM_CARD = "Get money from card.";

    private final UserTransactionsRepository userTransactionsRepository;

    @Override
    public List<UserTransactions> findAllTransactionsByUser(Integer userCardId, PageRequest pageable) {
        return userTransactionsRepository.findAllByUserCard(userCardId, pageable);
    }

    @Override
    public UserTransactions findTransaction(Integer userCardId, Integer transactionId) {
        return userTransactionsRepository.findByUserCardAndId(userCardId, transactionId)
                .orElseThrow(ResourceNotFoundException.transactionSupplier(transactionId));
    }

    @Override
    public void putMoneyToCard(UserCard userCard, BigDecimal transactionMoneyInUaH) {
        addNewTransaction(userCard, transactionMoneyInUaH, PUT_MONEY_TO_CARD);
    }

    @Override
    public void putMoneyToAnotherCard(UserCard userCard, BigDecimal transactionMoneyInUaH, String anotherCodeCard) {
        addNewTransaction(userCard, transactionMoneyInUaH, String.format(PUT_MONEY_TO__ANOTHER_CARD, anotherCodeCard));
    }

    @Override
    public void getMoneyFromCard(UserCard userCard, BigDecimal transactionMoneyInUaH) {
        addNewTransaction(userCard, transactionMoneyInUaH, GET_MONEY_FROM_CARD);
    }

    private void addNewTransaction(UserCard userCard, BigDecimal transactionMoneyInUaH, String transactionMessage) {
        userTransactionsRepository.save(new UserTransactions().setTransactionMoneyInUaH(transactionMoneyInUaH)
                .setUserCard(userCard)
                .setTransactionTime(LocalDateTime.now())
                .setMessage(transactionMessage));
    }
}
