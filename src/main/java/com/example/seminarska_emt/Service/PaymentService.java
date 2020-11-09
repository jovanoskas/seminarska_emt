package com.example.seminarska_emt.Service;

import com.example.seminarska_emt.constraint.ChargeRequest;
import com.stripe.exception.*;
import com.stripe.model.Charge;


public interface PaymentService {
    Charge pay(ChargeRequest chargeRequest) throws CardException, APIException, AuthenticationException, InvalidRequestException, APIConnectionException;
}
