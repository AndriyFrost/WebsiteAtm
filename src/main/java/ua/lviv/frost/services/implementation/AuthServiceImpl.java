package ua.lviv.frost.services.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.lviv.frost.dto.JwtAuthenticationResponse;
import ua.lviv.frost.dto.LoginRequest;
import ua.lviv.frost.dto.UserRequest;
import ua.lviv.frost.dto.UserResponse;
import ua.lviv.frost.entity.UserCard;
import ua.lviv.frost.entity.enumeration.Role;
import ua.lviv.frost.exception.LoginException;
import ua.lviv.frost.repository.UserCardRepository;
import ua.lviv.frost.security.JwtTokenProvider;
import ua.lviv.frost.security.UserPrincipalCard;
import ua.lviv.frost.services.AuthService;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserCardRepository userCardRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    @Override
    public UserResponse registerUser(UserRequest userRequest) {

        String randomCardCode = generateRandomCardCode();
        while (userCardRepository.findByCardCode(randomCardCode).isPresent()) {
            randomCardCode = generateRandomCardCode();
        }

        UserCard userCard = UserCard.builder()
                .cardCode(randomCardCode)
                .pinCode(passwordEncoder.encode(userRequest.getPinCode()))
                .role(Role.ROLE_USER)
                .phoneNumber(userRequest.getPhoneNumber())
                .moneyInUaH(new BigDecimal(0))
                .firstName(userRequest.getName())
                .lastName(userRequest.getSurname())
                .build();

        UserCard save = userCardRepository.save(userCard);

        log.info("Successfully registered user with [card code: {}]", userCard.getCardCode());

        return UserResponse.builder()
                .cardCode(save.getCardCode())
                .phoneNumber(save.getPhoneNumber())
                .name(save.getFirstName())
                .surname(save.getLastName())
                .id(save.getId().toString())
                .priceInUaH(save.getMoneyInUaH().toString())
                .build();
    }

    @Override
    public JwtAuthenticationResponse loginUser(LoginRequest loginRequest) {
        if (!userCardRepository.findByCardCode(loginRequest.getCardCode()).isPresent()) {
            throw new LoginException();
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getCardCode(),
                        loginRequest.getPinCode()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);
        UserPrincipalCard userPrincipal = (UserPrincipalCard) authentication.getPrincipal();

        log.info("User with [username: {}] has logged in", userPrincipal.getUsername());

        return new JwtAuthenticationResponse(jwt, userPrincipal.getId());
    }

    private String generateRandomCardCode() {
        StringBuilder randomCardCode = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            int randomNumber = (int) (Math.random() * 10);
            randomCardCode.append(randomNumber);
        }
        return randomCardCode.toString();
    }
}
