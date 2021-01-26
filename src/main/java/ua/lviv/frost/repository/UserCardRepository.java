package ua.lviv.frost.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.lviv.frost.entity.UserCard;

import java.util.Optional;

@Repository
public interface UserCardRepository extends JpaRepository<UserCard, Integer> {

    Optional<UserCard> findByCardCode(String cardCode);

    Optional<UserCard> findById(Integer id);
}
