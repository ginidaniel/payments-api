package payments.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import payments.model.HttpResponse;
import payments.exceptions.NoDataFoundException;
import payments.model.Payment;
import payments.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
public class PaymentAPI {

    @Autowired
    private PaymentService paymentService;

    @GetMapping(value = "/payments", produces = "application/json")
    public HttpResponse listPayments() {
        return new HttpResponse(HttpStatus.OK, paymentService.getPayments());
    }

    @GetMapping(value = "/payments/{paymentID}", produces = "application/json")
    public HttpResponse fetchPayment(@PathVariable("paymentID") long paymentID) {
        try {
            return new HttpResponse(HttpStatus.OK, paymentService.getPayment(paymentID));
        } catch (NoDataFoundException e) {
            return new HttpResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping(path = "/payments", consumes = "application/json", produces = "application/json")
    public HttpResponse createPayment(@RequestBody Payment payment) {
        try {
            paymentService.createPayment(payment);
            return new HttpResponse(HttpStatus.CREATED, "Payment Created: " + payment.getPaymentID());
        } catch (IllegalArgumentException e) {
            return new HttpResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping(path = "/payments", consumes = "application/json", produces = "application/json")
    public HttpResponse updatePayment(@RequestBody Payment payment) {
        try {
            paymentService.updatePayment(payment);
            return new HttpResponse(HttpStatus.OK, "Payment Updated: " + payment.getPaymentID());
        } catch (Exception e) {
            return new HttpResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping(path = "/payments/{paymentID}", produces = "application/json")
    public HttpResponse deletePayment(@PathVariable("paymentID") long paymentID) {
        try {
            paymentService.deletePayment(paymentID);
            return new HttpResponse(HttpStatus.OK, "Payment Deleted: " + paymentID);
        } catch (NoDataFoundException e) {
            return new HttpResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
