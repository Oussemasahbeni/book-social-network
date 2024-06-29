package com.oussema.bsn.history;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookTransactionHistoryRepository extends JpaRepository<BookTransactionHistory, Integer>{


    @Query("""
            SELECT history FROM BookTransactionHistory history
            where history.user.id = :userId
    """)
    Page<BookTransactionHistory> findAllBorrowedBooks(Pageable pageable, Integer userId);


    @Query("""
            SELECT history FROM BookTransactionHistory history
            where history.book.owner.id = :userId
    """)
    Page<BookTransactionHistory> findAllReturnedBooks (Pageable pageable, Integer userId);

    // count is used to check if the book is already borrowed by the user
    // if the count is greater than 0, then the book is already borrowed by the user
    @Query("""
            SELECT (COUNT(*) > 0) As isBorrowed
            FROM BookTransactionHistory history
            WHERE history.user.id = :id
            AND history.book.id = :bookId
            AND history.returnApproved = false
    """)
    boolean isAlreadyBorrowedByUser(Integer bookId, Integer id);

    @Query("""
            SELECT transaction FROM BookTransactionHistory transaction
            where transaction.book.id = :bookId
            AND transaction.user.id = :id
            AND transaction.returned = false
            AND transaction.returnApproved = false
    """)
    Optional<BookTransactionHistory> findByBookIdAndUserId(Integer bookId, Integer id);

    @Query("""
            SELECT transaction FROM BookTransactionHistory transaction
            where transaction.book.id = :bookId
            AND transaction.book.owner.id = :id
            AND transaction.returned = true
            AND transaction.returnApproved = false
    """)
    Optional<BookTransactionHistory> findByBookIdAndOwnerId(Integer bookId, Integer id);
}
