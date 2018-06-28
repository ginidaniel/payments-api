import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import payments.context.Application;
import payments.enums.CurrencyType;
import payments.exceptions.NoDataFoundException;
import payments.model.Payment;
import payments.model.PaymentParty;
import payments.repositories.PaymentRepository;
import payments.services.PaymentService;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={Application.class})
public class PaymentServicesTest {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private PaymentRepository paymentRepository;

    @Test
    public void testGetEmptyPaymentsRepository() {
        assertEquals(0, paymentRepository.findAll().size());
    }

    @Test
    public void testGetEmptyPaymentsServices() {
        assertEquals(0, paymentService.getPayments().size());
    }

    @Test
    public void testCreatePaymentFail_NoID() {
        try {
            Payment payment = new Payment();
            paymentService.createPayment(payment);
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
        paymentRepository.deleteAll();
    }

    @Test
    public void testCreatePaymentFail_NoAmount() {
        try {
            Payment payment = new Payment();
            payment.setPaymentID(12345);
            paymentService.createPayment(payment);
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
        paymentRepository.deleteAll();
    }

    @Test
    public void testCreatePayment_success() {
        try {
            Payment payment = createPayment(12345);
            paymentService.createPayment(payment);
            assertEquals(1, paymentService.getPayments().size());
            assertEquals(payment.getAmount(), paymentService.getPayment(12345).getAmount());

            paymentRepository.deleteAll();
            assertEquals(0, paymentService.getPayments().size());
        } catch (NoDataFoundException e) {
            fail();
        }
        paymentRepository.deleteAll();
    }

    @Test
    public void testCreatePayment_fail() {
        try {
            Payment payment = new Payment();
            payment.setPaymentID(12345);
            paymentService.createPayment(payment);
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
        paymentRepository.deleteAll();
    }

    @Test
    public void testUpdatePayment_success() {
        try {
            Payment payment = createPayment(12345);
            paymentService.createPayment(payment);
            payment.setReference("Updated");
            paymentService.updatePayment(payment);
            assertEquals(1, paymentService.getPayments().size());
            assertEquals("Updated", paymentService.getPayment(12345).getReference());
        } catch (NoDataFoundException e) {
            fail();
        }
        paymentRepository.deleteAll();
    }

    @Test
    public void testUpdatePayment_fail_illegal() {
        try {
            Payment payment = createPayment(12345);
            paymentService.createPayment(payment);
            payment.setPaymentID(0);
            paymentService.updatePayment(payment);
            fail();
        } catch (NoDataFoundException e) {
            fail();
        }
        catch (IllegalArgumentException e) {
            assertTrue(true);
        }
        paymentRepository.deleteAll();
    }

    @Test
    public void testUpdatePayment_fail_not_found() {
        try {
            Payment payment = createPayment(12345);
            paymentService.updatePayment(payment);
            fail();
        } catch (NoDataFoundException e) {
            assertTrue(true);
        }
        catch (IllegalArgumentException e) {
            fail();
        }
        paymentRepository.deleteAll();
    }

    @Test
    public void testCRUDPayment() {
        try {
            Payment payment = createPayment(12345);
            paymentService.createPayment(payment);
            assertEquals(1, paymentService.getPayments().size());
            payment = createPayment(67890);
            paymentService.createPayment(payment);
            assertEquals(2, paymentService.getPayments().size());

            payment.setCurrency(CurrencyType.EUR);
            paymentService.updatePayment(payment);

            Payment paymentDB = paymentService.getPayment(12345);
            assertEquals(12345, paymentDB.getPaymentID());
            assertEquals(CurrencyType.GBP, paymentDB.getCurrency());

            paymentDB = paymentService.getPayment(67890);
            assertEquals(67890, paymentDB.getPaymentID());
            assertEquals(CurrencyType.EUR, paymentDB.getCurrency());

            paymentService.deletePayment(12345);
            paymentService.deletePayment(67890);
            assertEquals(0, paymentService.getPayments().size());
        } catch (NoDataFoundException e) {
            fail();
        }
        paymentRepository.deleteAll();
    }

    private Payment createPayment(int paymentID) {
        Payment payment = new Payment();
        payment.setPaymentID(paymentID);
        payment.setAmount(new BigDecimal(100.21));
        payment.setPaymentPurpose("Paying for goods/services");
        payment.setPaymentScheme("FPS");
        payment.setProcessingDate(LocalDate.now());
        payment.setReference("Payment for Em's piano lessons");

        PaymentParty beneficiary = new PaymentParty();
        beneficiary.setAccountName("W Owens");
        beneficiary.setAccountNumber("56781234");
        beneficiary.setBankIDCode("BBAN");
        beneficiary.setAddress("1 The Beneficiary Localtown SE2");
        beneficiary.setBankID(403000);
        beneficiary.setBankIDCode("GBDSC");
        beneficiary.setName("Wilfred Jeremiah Owens");
        payment.setBeneficiaryParty(beneficiary);

        PaymentParty debtor = new PaymentParty();
        debtor.setAccountName("EJ Brown Black");
        debtor.setAccountNumber("GB29XABC10161234567801");
        debtor.setAccountNumberCode("IBAN");
        debtor.setAddress("10 Debtor Crescent Sourcetown NE1");
        debtor.setBankID(203301);
        debtor.setBankIDCode("GBDSC");
        debtor.setName("Emelia Jane Brown");
        payment.setDebtorParty(debtor);

        PaymentParty sponsor = new PaymentParty();
        sponsor.setAccountNumber("56781234");
        sponsor.setBankID(123123);
        sponsor.setBankIDCode("GBDSC");
        payment.setSponsorParty(sponsor);

        return payment;
    }

}
