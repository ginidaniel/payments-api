package payments.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import payments.dao.PaymentDAO;
import payments.exceptions.NoDataFoundException;
import payments.model.Payment;
import payments.repositories.PaymentRepository;

import java.util.List;

/**
 * @author Daniel Gini
 * @version 1.0
 */
public class PaymentMongoImpl implements PaymentDAO {

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public List<Payment> findPayments() {
        return paymentRepository.findAll();
    }

    @Override
    public Payment findPayment(long paymentID) throws NoDataFoundException {
        Payment payment = paymentRepository.findById(paymentID).orElse(null);
        if (payment == null) throw new NoDataFoundException("The payment not found - ID: " + paymentID);
        return payment;
    }

    @Override
    public void insertPayment(Payment payment) {
        paymentRepository.insert(payment);
    }

    @Override
    public void updatePayment(Payment payment) throws NoDataFoundException {
        if (!paymentRepository.existsById(payment.getPaymentID()))
            throw new NoDataFoundException("The payment not found - ID: " + payment.getPaymentID());
        paymentRepository.save(payment);
    }

    @Override
    public void removePayment(long paymentID) throws NoDataFoundException {
        if (!paymentRepository.existsById(paymentID))
            throw new NoDataFoundException("The payment not found - ID: " + paymentID);
        paymentRepository.deleteById(paymentID);
    }

}
