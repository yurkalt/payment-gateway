package com.example.service;

import com.example.configuration.AuditConfig;
import com.example.dto.PaymentDto;
import com.example.model.Card;
import com.example.model.CardHolder;
import com.example.model.Payment;
import com.example.repository.PaymentRepository;
import com.example.utils.Encoder;
import org.apache.commons.io.FileUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private AuditConfig auditConfig;

    @Transactional
    public void processPayment(PaymentDto payment){
        Payment paymentModel = modelMapper.map(payment, Payment.class);
        Card card = encodeCard(paymentModel);
        CardHolder cardHolder = encodeCardHolder(paymentModel);
        paymentModel.setCard(card);
        paymentModel.setCardholder(cardHolder);
        writeToAuditFile(paymentModel);
        paymentRepository.save(paymentModel);
    }

    private void writeToAuditFile(Payment paymentModel) {
        PaymentDto paymentDto = modelMapper.map(paymentModel, PaymentDto.class);
        File file = new File(auditConfig.getPath());
        try {
            FileUtils.writeStringToFile(
                    file, paymentDto.toString(), StandardCharsets.UTF_8, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
