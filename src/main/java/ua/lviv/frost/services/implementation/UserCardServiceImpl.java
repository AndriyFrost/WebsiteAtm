package ua.lviv.frost.services.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.lviv.frost.entity.UserCard;
import ua.lviv.frost.exception.HaveNotEnoughMoneyException;
import ua.lviv.frost.exception.ResourceNotFoundException;
import ua.lviv.frost.repository.UserCardRepository;
import ua.lviv.frost.services.UserCardService;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class UserCardServiceImpl implements UserCardService {

    private final UserCardRepository userCardRepository;
    private final UserTransactionsServiceImpl userTransactionsServiceImpl;

    @Override
    public UserCard findUserCard(Integer userCardId) {
        return userCardRepository.findById(userCardId)
                .orElseThrow(ResourceNotFoundException.userCardSupplier(userCardId));
    }

    @Override
    public UserCard findUserCardByCodeCard(String codeCard) {
        return userCardRepository.findByCardCode(codeCard)
                .orElseThrow(ResourceNotFoundException.cardCodeSupplier(codeCard));
    }

    @Override
    public UserCard updateBasicInfo(Integer userCardId, UserCard updateUserCard) {
        return userCardRepository.findById(userCardId)
                .map(userCard -> updateUserCardFields(userCard, updateUserCard))
                .map(userCardRepository::saveAndFlush)
                .orElseThrow(ResourceNotFoundException.userCardSupplier(userCardId));
    }

    @Override
    public UserCard putMoneyOnTheCard(Integer userCardId, BigDecimal putMoney) {
        UserCard userCard = findUserCard(userCardId);
        BigDecimal newMoneyInUah = addMoney(userCard.getMoneyInUaH(), putMoney);
        userTransactionsServiceImpl.putMoneyToCard(userCard, putMoney);
        return userCardRepository.saveAndFlush(userCard.setMoneyInUaH(newMoneyInUah));
    }

    @Override
    public UserCard getMoneyFromCard(Integer userCardId, BigDecimal getMoney) {
        UserCard userCard = findUserCard(userCardId);
        BigDecimal newMoneyInUah = subtractMoney(userCard.getMoneyInUaH(), getMoney);
        crashIfThereIsNotEnoughMoneyOnTheCard(newMoneyInUah);
        userTransactionsServiceImpl.getMoneyFromCard(userCard, getMoney);
        return userCardRepository.saveAndFlush(userCard.setMoneyInUaH(newMoneyInUah));
    }

    /**
     * Deposit money to card using recipient card code.
     *
     * @param senderCardId      card id to withdraw money
     * @param moneyToDeposit    amount of money to transfer
     * @param recipientCardCode card code to deposit
     * @return sender's card with updated amount of money
     */
    @Override
    public UserCard depositMoneyToCard(Integer senderCardId, BigDecimal moneyToDeposit, String recipientCardCode) {
        UserCard userCard = findUserCard(senderCardId);
        UserCard userCardToPutMoney = findUserCardByCodeCard(recipientCardCode);

        BigDecimal newMoneyInUah = subtractMoney(userCard.getMoneyInUaH(), moneyToDeposit);
        crashIfThereIsNotEnoughMoneyOnTheCard(newMoneyInUah);

        BigDecimal newMoneyInUahInAntherCard = addMoney(userCardToPutMoney.getMoneyInUaH(), moneyToDeposit);

        userTransactionsServiceImpl.putMoneyToAnotherCard(userCard, moneyToDeposit, recipientCardCode);
        userCardRepository.saveAndFlush(userCardToPutMoney.setMoneyInUaH(newMoneyInUahInAntherCard));
        return userCardRepository.saveAndFlush(userCard.setMoneyInUaH(newMoneyInUah));
    }

    private UserCard updateUserCardFields(UserCard userCard, UserCard updateUserCard) {
        userCard.setPhoneNumber(Objects.requireNonNullElse(updateUserCard.getPhoneNumber(), userCard.getPhoneNumber()));
        userCard.setFirstName(Objects.requireNonNullElse(updateUserCard.getFirstName(), userCard.getFirstName()));
        userCard.setLastName(Objects.requireNonNullElse(updateUserCard.getLastName(), userCard.getLastName()));
        return userCard;
    }

    private BigDecimal addMoney(BigDecimal oldMoneyInUah, BigDecimal moneyToAdd) {
        return oldMoneyInUah.add(moneyToAdd);
    }

    private BigDecimal subtractMoney(BigDecimal oldMoneyInUah, BigDecimal moneyToMinus) {
        return oldMoneyInUah.subtract(moneyToMinus);
    }

    private void crashIfThereIsNotEnoughMoneyOnTheCard(BigDecimal moneyInCard) {
        if (moneyInCard.intValue() < 0.01) throw new HaveNotEnoughMoneyException();
    }
}
