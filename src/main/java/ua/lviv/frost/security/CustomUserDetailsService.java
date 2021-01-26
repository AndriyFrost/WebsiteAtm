package ua.lviv.frost.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ua.lviv.frost.entity.UserCard;
import ua.lviv.frost.repository.UserCardRepository;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserCardRepository userCardRepository;

    @Autowired
    public CustomUserDetailsService(UserCardRepository userCardRepository) {
        this.userCardRepository = userCardRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String cardCode) {
        UserCard userCard = userCardRepository.findByCardCode(cardCode)
                .orElseThrow(() ->
                        new RuntimeException("User card not found [card code: " + cardCode + "]")
                );
        return UserPrincipalCard.create(userCard);
    }

    @Transactional
    public UserDetails loadUserByIdToUserDetails(Integer id) {
        return UserPrincipalCard.create(loadUserById(id));
    }

    @Transactional
    public UserCard loadUserById(Integer id) {
        return userCardRepository.findById(id).orElseThrow(
                () -> new RuntimeException("User not found [id: " + id + "]")
        );
    }
}
