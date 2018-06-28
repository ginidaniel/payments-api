package payments.enums;

/**
 * Created by daniel.gini on 22/06/2018.
 */
public enum PaymentType {

    DEBIT ("DEBIT"),
    CREDIT ("CREDIT"),
    CASH ("CASH"),
    PAYPAL ("PAYPAL");

    PaymentType(String name) {
    }
}
