package com.example.service;

import com.example.model.Payment;
import com.example.repository.CardHolderRepository;
import com.example.repository.PaymentRepository;
import com.sun.xml.bind.v2.model.core.ID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Transactional
    public void processPayment(Payment payment){
        paymentRepository.save(payment);
    }

    public Payment getPaymentById(String id){
        Optional<Payment> byId = paymentRepository.findById(Integer.valueOf(id));
        return byId.orElse(null);
    }
}
