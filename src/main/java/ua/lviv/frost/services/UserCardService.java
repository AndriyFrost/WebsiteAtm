package ua.lviv.frost.services;

import ua.lviv.frost.entity.UserCard;

import java.math.BigDecimal;

public interface UserCardService {

    UserCard findUserCard(Integer userCardId);

    UserCard findUserCardByCodeCard(String codeCard);

    UserCard updateBasicInfo(Integer userCardId, UserCard updateUserCard);

    UserCard putMoneyOnTheCard(Integer userCardId, BigDecimal putMoney);

    UserCard getMoneyFromCard(Integer userCardId, BigDecimal getMoney);

    UserCard depositMoneyToCard(Integer senderCardId, BigDecimal moneyToDeposit, String recipientCardCode);
}
