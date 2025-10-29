package com.travel.travelapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.travel.travelapp.entity.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByTripId(String tripId);
    Optional<Payment> findByPaymentId(Long paymentId);
    List<Payment> findByUserId(String userId);
}