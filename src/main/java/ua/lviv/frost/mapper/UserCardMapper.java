package ua.lviv.frost.mapper;

import org.springframework.stereotype.Component;
import ua.lviv.frost.dto.UserCardDto;
import ua.lviv.frost.entity.UserCard;

@Component
public class UserCardMapper extends AbstractMapper<UserCard, UserCardDto> {

    @Override
    public UserCardDto toDto(UserCard userCard) {
        return new UserCardDto().setId(userCard.getId())
                .setCardCode(userCard.getCardCode())
                .setName(userCard.getFirstName())
                .setSurname(userCard.getLastName())
                .setPhoneNumber(userCard.getPhoneNumber())
                .setMoneyInUaH(userCard.getMoneyInUaH());
    }

    @Override
    public UserCard toEntity(UserCardDto userCardDto) {
        return new UserCard().setFirstName(userCardDto.getName())
                .setLastName(userCardDto.getSurname())
                .setPhoneNumber(userCardDto.getPhoneNumber());
    }
}
