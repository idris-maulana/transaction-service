package com.idris.transaction.repository;

import com.idris.transaction.model.Sequence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SequenceRepository extends JpaRepository<Sequence, Long> {

    @Query("SELECT s FROM Sequence s WHERE s.sequenceName = ?1")
    Sequence getSequenceByName(String sequenceName);
}
