package com.idris.transaction.repository;

import com.idris.transaction.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {

    @Query("SELECT s FROM Service s WHERE s.serviceCode = ?1")
    Optional<Service> getServiceByCode(String serviceCode);
}
