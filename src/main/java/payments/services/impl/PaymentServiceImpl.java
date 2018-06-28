package payments.services.impl;

import payments.dao.PaymentDAO;
import payments.exceptions.NoDataFoundException;
import payments.model.Payment;
import payments.services.PaymentService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * @author Daniel Gini
 */
@Component
@Service
public class PaymentServiceImpl implements PaymentService {

	private PaymentDAO paymentDAO;

	public PaymentServiceImpl(PaymentDAO paymentDAO) {
		this.paymentDAO = paymentDAO;
	}

	@Override
	public List<Payment> getPayments() {
		return paymentDAO.findPayments();
	}

	@Override
	public Payment getPayment(long paymentID) throws NoDataFoundException {
		return paymentDAO.findPayment(paymentID);
	}

	@Override
	public void createPayment(Payment payment) throws IllegalArgumentException {
		validatePayment(payment);
		paymentDAO.insertPayment(payment);
	}

	@Override
	public void updatePayment(Payment payment) throws NoDataFoundException, IllegalArgumentException {
		validatePayment(payment);
		paymentDAO.updatePayment(payment);
	}

	@Override
	public void deletePayment(long paymentID) throws NoDataFoundException {
		paymentDAO.removePayment(paymentID);
	}

	private void validatePayment(Payment payment) throws IllegalArgumentException {
		if (payment.getPaymentID()==0)
			throw new IllegalArgumentException("Payment ID is missing.");
		if (payment.getAmount()==null)
			throw new IllegalArgumentException("Amount is missing.");
		if (payment.getBeneficiaryParty()!=null && payment.getBeneficiaryParty().getAccountNumber()==null)
			throw new IllegalArgumentException("Beneficiary Account Number is missing.");
		if (payment.getDebtorParty()!=null && payment.getDebtorParty().getAccountNumber()==null)
			throw new IllegalArgumentException("Debtor Account Number is missing.");
		if (payment.getSponsorParty()!=null && payment.getSponsorParty().getAccountNumber()==null)
			throw new IllegalArgumentException("Sponsor Account Number is missing.");
	}

}