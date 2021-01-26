package ua.lviv.frost.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.lviv.frost.entity.UserTransactions;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserTransactionsRepository extends JpaRepository<UserTransactions, Integer> {

    @Query(value = "SELECT * FROM user_transactions " +
            " WHERE user_transactions.user_card_id = :userCardId", nativeQuery = true)
    List<UserTransactions> findAllByUserCard(@Param("userCardId") Integer userCardId, Pageable pageable);

    @Query(value = "SELECT * FROM user_transactions " +
            " WHERE user_transactions.user_card_id = :userCardId" +
            " AND user_transactions.id = :transactionId", nativeQuery = true)
    Optional<UserTransactions> findByUserCardAndId(@Param("userCardId") Integer userCardId,
                                              @Param("transactionId") Integer transactionId);
}
