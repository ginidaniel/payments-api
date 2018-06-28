import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import payments.context.Application;
import payments.controllers.PaymentAPI;
import payments.exceptions.NoDataFoundException;
import payments.model.Payment;
import payments.model.PaymentParty;
import payments.services.PaymentService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={Application.class})
public class PaymentAPITest {

    private MockMvc mockMvc;

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private PaymentAPI paymentController;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(paymentController)
                .build();
    }

    @Test
    public void test_get_all_payments_success() throws Exception {
        Payment payment = createPayment(1234);
        List<Payment> payments = new ArrayList<>();
        payments.add(payment);
        payment = createPayment(5678);
        payments.add(payment);

        when(paymentService.getPayments()).thenReturn(payments);
        mockMvc.perform(get("/payments"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.data").value(hasSize(2)))
                    .andExpect(jsonPath("$.data[0].paymentID").value(1234))
                    .andExpect(jsonPath("$.data[1].paymentID").value(5678));

    }

    @Test
    public void test_get_by_payment_id_success() throws Exception {
        Payment payment = createPayment(1234);
        when(paymentService.getPayment(1234)).thenReturn(payment);

        mockMvc.perform(get("/payments/{id}", 1234))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.data.paymentID").value(1234));

    }

    @Test
    public void test_get_by_payment_id_not_found() throws Exception {
        when(paymentService.getPayment(12)).thenThrow(NoDataFoundException.class);

        mockMvc.perform(get("/payments/{id}", 12))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.httpStatus").value(400));

    }

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void test_create_payment_success() throws Exception {
        Payment payment = createPayment(1234);
        when(paymentService.getPayment(1234)).thenReturn(payment);
        doNothing().when(paymentService).createPayment(payment);

        mockMvc.perform(post("/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(payment))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.httpStatus").value(201));

    }

    @Test
    public void test_create_payment_fail() throws Exception {
        Payment payment = new Payment();
        payment.setPaymentID(1234);
        doThrow(IllegalArgumentException.class).when(paymentService).createPayment(payment);

        mockMvc.perform(post("/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(payment))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void test_update_payment_success() throws Exception {
        Payment paymentA = createPayment(1234);
        Payment paymentB = createPayment(1234);
        paymentB.setAmount(new BigDecimal(234.21));
        doNothing().when(paymentService).createPayment(paymentA);
        doNothing().when(paymentService).updatePayment(paymentB);

        mockMvc.perform(post("/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(paymentA))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.httpStatus").value(201));

        mockMvc.perform(
                put("/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(paymentB)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.httpStatus").value(200));

    }

    @Test
    public void test_delete_payment_success() throws Exception {
        Payment payment = createPayment(1234);
        doNothing().when(paymentService).deletePayment(payment.getPaymentID());

        mockMvc.perform(
                delete("/payments/{id}", payment.getPaymentID()))
                .andExpect(status().isOk());

        verify(paymentService, times(1)).deletePayment(payment.getPaymentID());
        verifyNoMoreInteractions(paymentService);
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
