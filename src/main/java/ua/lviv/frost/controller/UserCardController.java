package ua.lviv.frost.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ua.lviv.frost.dto.UserCardDto;
import ua.lviv.frost.entity.UserCard;
import ua.lviv.frost.mapper.UserCardMapper;
import ua.lviv.frost.security.UserPrincipalCard;
import ua.lviv.frost.services.implementation.UserCardServiceImpl;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/user-card")
@RequiredArgsConstructor
@Validated
public class UserCardController {

    private final UserCardServiceImpl userCardServiceImpl;
    private final UserCardMapper userCardMapper;

    @GetMapping
    public ResponseEntity<UserCardDto> getInfoAboutUserCard(@AuthenticationPrincipal UserPrincipalCard userPrincipalCard) {
        UserCard userCard = userCardServiceImpl.findUserCard(userPrincipalCard.getId());
        UserCardDto userCardDto = userCardMapper.toDto(userCard);
        return new ResponseEntity<>(userCardDto, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<UserCardDto> updateBasicInfo(@AuthenticationPrincipal UserPrincipalCard userPrincipalCard,
                                                       @RequestBody @Valid UserCardDto userCardDto) {
        UserCard userCard = userCardServiceImpl.updateBasicInfo(userPrincipalCard.getId(), userCardMapper.toEntity(userCardDto));
        UserCardDto newUserCardDto = userCardMapper.toDto(userCard);
        return new ResponseEntity<>(newUserCardDto, HttpStatus.OK);
    }

    @PutMapping("/put-money")
    public ResponseEntity<UserCardDto> putMoneyOnTheCard(@AuthenticationPrincipal UserPrincipalCard userPrincipalCard,
                                                         @RequestParam BigDecimal putMoney) {
        UserCard userCard = userCardServiceImpl.putMoneyOnTheCard(userPrincipalCard.getId(), putMoney);
        UserCardDto userCardDto = userCardMapper.toDto(userCard);
        return new ResponseEntity<>(userCardDto, HttpStatus.OK);
    }

    @PutMapping("/get-money")
    public ResponseEntity<UserCardDto> getMoneyFromCard(@AuthenticationPrincipal UserPrincipalCard userPrincipalCard,
                                                        @RequestParam BigDecimal getMoney) {
        UserCard userCard = userCardServiceImpl.getMoneyFromCard(userPrincipalCard.getId(), getMoney);
        UserCardDto userCardDto = userCardMapper.toDto(userCard);
        return new ResponseEntity<>(userCardDto, HttpStatus.OK);
    }

    @PutMapping("/put-money-to-card")
    public ResponseEntity<UserCardDto> putMoneyToAnotherCard(@AuthenticationPrincipal UserPrincipalCard userPrincipalCard,
                                                             @RequestParam BigDecimal putMoney,
                                                             @RequestParam @Valid
                                                             @Pattern(regexp = "^\\d{16}$", message = "Invalid card code") String cardCode) {
        UserCard userCard = userCardServiceImpl.depositMoneyToCard(userPrincipalCard.getId(), putMoney, cardCode);
        UserCardDto userCardDto = userCardMapper.toDto(userCard);
        return new ResponseEntity<>(userCardDto, HttpStatus.OK);
    }
}
