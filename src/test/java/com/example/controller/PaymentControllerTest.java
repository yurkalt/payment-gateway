package com.example.controller;

import com.example.dto.CardDto;
import com.example.dto.CardHolderDto;
import com.example.dto.PaymentDto;
import com.example.service.PaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = PaymentController.class)
class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentService paymentService;

    @Test
    void submitPayment() throws Exception {
        CardDto card = CardDto.builder().cvv("333").pan("4100000000000001").expiry("0122").build();
        CardHolderDto holder = CardHolderDto.builder().email("test@gmail.com").name("J J").build();
        PaymentDto payment = PaymentDto.builder().amount(22341).cardholder(holder).card(card).invoice(1234).currency("USD").build();
        ObjectMapper objectMapper = new ObjectMapper();
        String stringPayment = objectMapper.writeValueAsString(payment);
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/submit")
                                            .accept(MediaType.APPLICATION_JSON_VALUE)
                                            .content(stringPayment)
                                            .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.approved").value(true))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void submitPaymentWithBadPAN() throws Exception {
        CardDto card = CardDto.builder().cvv("333").pan("4200000000000001").expiry("0122").build();
        CardHolderDto holder = CardHolderDto.builder().email("test@gmail.com").name("J J").build();
        PaymentDto payment = PaymentDto.builder().amount(22341).cardholder(holder).card(card).invoice(1234).currency("USD").build();
        ObjectMapper objectMapper = new ObjectMapper();
        String stringPayment = objectMapper.writeValueAsString(payment);
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/submit")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(stringPayment)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.approved").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors.pan").value("PAN is not valid."))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    void getPaymentByInvoiceId() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                .get("/v1/{invoiceId}",1234)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}