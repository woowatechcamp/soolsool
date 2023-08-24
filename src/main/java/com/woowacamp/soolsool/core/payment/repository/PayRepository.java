package com.woowacamp.soolsool.core.payment.repository;

import com.woowacamp.soolsool.core.payment.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PayRepository extends JpaRepository<Payment, Long> {

}
