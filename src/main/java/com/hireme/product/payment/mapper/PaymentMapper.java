package com.hireme.product.payment.mapper;


import com.hireme.product.payment.Entity.Payment;
import com.hireme.product.payment.dto.PaymentDto;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring", uses = {}
)
public interface PaymentMapper{
    PaymentDto toDto(Payment payment);
}
