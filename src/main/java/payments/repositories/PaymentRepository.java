package payments.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import payments.model.Payment;

/**
 * <p>Title: PaymentRepository.java</p>
 * No implementation needed: this interface is implemented by Spring Data, which provides CRUD methods.
 *
 * @author Daniel Gini
 */
public interface PaymentRepository extends MongoRepository<Payment, Long> {

}

