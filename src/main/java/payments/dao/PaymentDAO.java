package payments.dao;

import payments.exceptions.NoDataFoundException;
import payments.model.Payment;

import java.util.List;

/**
 * <p>Title: PaymentDAO.java</p>
 * Retrieves/Persists information from/to the data source (files, database, etc)
 *
 * @author Daniel Gini
 * @version 1.0
 */
public interface PaymentDAO {

    /**
     * Retrieves all the payment persisted on this data source
     *
     * @return list of Payments
     */
    List<Payment> findPayments();

    /**
     * Retrieves a Payment persisted on this data source by the ID given
     *
     * @param paymentID payment to be fetched
     * @throws NoDataFoundException when the payment to be updated does not exist on this data source
     * @return Payment
     */
    Payment findPayment(long paymentID) throws NoDataFoundException;

    /**
     * Inserts a Payment on this data source. The ID can not be duplicated.
     *
     * @param payment data to be persisted on this data source
     */
    void insertPayment(Payment payment);

    /**
     * Updates a Payment on the data source.
     *
     * @param payment data to be updated. The payment ID has to exist on this data source
     * @throws NoDataFoundException when the payment to be updated does not exist on this data source
     */
    void updatePayment(Payment payment) throws NoDataFoundException;

    /**
     * Finds a Payment by ID and deletes it from this data source
     *
     * @param paymentID payment to be fetch and deleted
     * @throws NoDataFoundException when the payment ID does not exist on the data source
     */
    void removePayment(long paymentID) throws NoDataFoundException;
}
