package payments.context;

import payments.dao.PaymentDAO;
import payments.dao.impl.PaymentMongoImpl;
import payments.services.PaymentService;
import payments.services.impl.PaymentServiceImpl;
import org.springframework.context.annotation.*;

@Configuration
@ComponentScan
public class AppConfig {

    @Bean
    public PaymentDAO getPaymentDAO() {
        return new PaymentMongoImpl();
    }

    @Bean
    public PaymentService getPaymentService() {
        return new PaymentServiceImpl(getPaymentDAO());
    }

}
