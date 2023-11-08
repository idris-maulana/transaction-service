package com.idris.transaction.repository;

import com.idris.transaction.model.History;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {

    @Query("SELECT h FROM History h WHERE h.userId = ?1 ORDER BY h.createdOn DESC")
    Page<History> getHistoryTransactionByUserId(Long userId, Pageable pageable);
}
