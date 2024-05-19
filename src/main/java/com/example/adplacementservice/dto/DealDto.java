package com.example.adplacementservice.dto;

import com.example.adplacementservice.model.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DealDto {
    private Integer adId;
    private Integer sellerId;
    private Integer buyerId;
    private PaymentMethod paymentMethod;
}
