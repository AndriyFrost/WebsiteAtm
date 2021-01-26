package ua.lviv.frost.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ua.lviv.frost.dto.UserTransactionsDto;
import ua.lviv.frost.entity.UserTransactions;
import ua.lviv.frost.mapper.UserTransactionsMapper;
import ua.lviv.frost.security.UserPrincipalCard;
import ua.lviv.frost.services.implementation.UserTransactionsServiceImpl;

import javax.validation.constraints.Min;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class UserTransactionsController {

    private final UserTransactionsServiceImpl userTransactionsServiceImpl;
    private final UserTransactionsMapper userTransactionsMapper;

    @GetMapping
    public ResponseEntity<List<UserTransactionsDto>>
    getAllUserTransactions(@AuthenticationPrincipal UserPrincipalCard userPrincipalCard,
                           @RequestParam(name = "page", defaultValue = "0") @Min(0) int pageNumber,
                           @RequestParam(name = "page_size", defaultValue = "15") @Min(1) int pageSize) {
        PageRequest pageable = PageRequest.of(pageNumber, pageSize);

        List<UserTransactionsDto> userTransactionsDtos = userTransactionsServiceImpl
                .findAllTransactionsByUser(userPrincipalCard.getId(), pageable).stream()
                .map(userTransactionsMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userTransactionsDtos);
    }

    @GetMapping(path = "/{transactionId}")
    public ResponseEntity<UserTransactionsDto> getTransaction(@AuthenticationPrincipal UserPrincipalCard userPrincipalCard,
                                                              @PathVariable Integer transactionId) {
        UserTransactions userTransactions = userTransactionsServiceImpl.findTransaction(userPrincipalCard.getId(), transactionId);
        UserTransactionsDto userTransactionsDto = userTransactionsMapper.toDto(userTransactions);
        return new ResponseEntity<>(userTransactionsDto, HttpStatus.OK);
    }
}
