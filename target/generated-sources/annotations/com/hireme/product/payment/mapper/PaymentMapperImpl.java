package com.hireme.product.payment.mapper;

import com.hireme.product.payment.Entity.Payment;
import com.hireme.product.payment.dto.PaymentDto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-10-12T23:05:51+0800",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.2 (Oracle Corporation)"
)
@Component
public class PaymentMapperImpl implements PaymentMapper {

    @Override
    public PaymentDto toDto(Payment payment) {
        if ( payment == null ) {
            return null;
        }

        PaymentDto paymentDto = new PaymentDto();

        paymentDto.setId( payment.getId() );
        paymentDto.setUserId( payment.getUserId() );
        paymentDto.setSessionId( payment.getSessionId() );
        paymentDto.setTotalPrice( payment.getTotalPrice() );
        paymentDto.setDteTimeCr( payment.getDteTimeCr() );
        paymentDto.setTransactionDesc( payment.getTransactionDesc() );

        return paymentDto;
    }
}
