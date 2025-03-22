package com.archi.Ecomm_Backend.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Long orderId;
    private LocalDate orderDate;
    private List<OrderItemDTO> orderItems = new ArrayList<>();
    private String orderStatus;
    private Double totalAmount;
    private PaymentDTO paymentDTO;



}
