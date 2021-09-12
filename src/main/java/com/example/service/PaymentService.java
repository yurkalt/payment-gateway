package com.example.service;

import com.example.dto.PaymentDto;
import com.example.model.Card;
import com.example.model.CardHolder;
import com.example.model.Payment;
import com.example.repository.PaymentRepository;
import com.example.utils.Encoder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public void processPayment(PaymentDto payment){
        Payment paymentModel = modelMapper.map(payment, Payment.class);
        Card card = encodeCard(paymentModel);
        CardHolder cardHolder = encodeCardHolder(paymentModel);
        paymentModel.setCard(card);
        paymentModel.setCardholder(cardHolder);
        paymentRepository.save(paymentModel);
    }

    private CardHolder encodeCardHolder(Payment paymentModel) {
        CardHolder cardHolder = paymentModel.getCardholder();
        cardHolder.setName(Encoder.encode(cardHolder.getName()));
        return cardHolder;
    }

    private Card encodeCard(Payment paymentModel) {
        Card card = paymentModel.getCard();
        card.setPan(Encoder.encodeSubString(12, card.getPan()));
        card.setExpiry(Encoder.encode(card.getExpiry()));
        return card;
    }

    public PaymentDto getPaymentById(String id){
        Optional<Payment> byId = paymentRepository.findById(Integer.valueOf(id));
        return byId.map(payment -> modelMapper.map(payment, PaymentDto.class)).orElse(null);

    }
}
