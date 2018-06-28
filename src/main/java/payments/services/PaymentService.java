package payments.services;

import payments.exceptions.NoDataFoundException;
import payments.model.Payment;

import java.util.List;

/**
 * <p>Title: PaymentService.java</p>
 * Interface that provides services to the Payment API
 *
 * @author Daniel Gini
 */
public interface PaymentService {

	/**
	 * Gets all the payment persisted on the data source
	 *
	 * @return list of Payments
	 */
	List<Payment> getPayments();

	/**
	 * Fetches a Payment by ID from the data source
	 *
	 * @param paymentID payment to be fetch
	 * @return Payment
	 * @throws NoDataFoundException when the payment ID does not exist on the data source
	 */
	Payment getPayment(long paymentID) throws NoDataFoundException;

	/**
	 * Adds a Payment on the data source
	 *
	 * @param payment data to be created and persisted on the data source
	 * @throws IllegalArgumentException when the payment does not contain mandatory data
	 */
	void createPayment(Payment payment) throws IllegalArgumentException;

	/**
	 * Finds a Payment on the data source and update it with the new data
	 *
	 * @param payment data to be updated. The payment ID has to exist on the data source
	 * @throws NoDataFoundException when the payment to be updated does not exist on the data source
	 * @throws IllegalArgumentException when the payment does not contain mandatory data
	 */
	void updatePayment(Payment payment) throws NoDataFoundException, IllegalArgumentException;

	/**
	 * Finds a Payment by ID and deletes it from the data source
	 *
	 * @param paymentID payment to be fetch and deleted
	 * @throws NoDataFoundException when the payment ID does not exist on the data source
	 */
	void deletePayment(long paymentID) throws NoDataFoundException;
}
