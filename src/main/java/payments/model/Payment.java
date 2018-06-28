package payments.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import payments.enums.CurrencyType;
import payments.enums.PaymentType;
import payments.enums.SchemaPaymentType;

import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@XmlRootElement(name = "Payment")
@Document(collection = "payments")
public class Payment {

    @Id
    private long paymentID;

    private BigDecimal amount;
    private CurrencyType currency;
    private int numericReference;
    private PaymentType paymentType;
    private String paymentScheme;
    private String paymentPurpose;
    private LocalDate processingDate;
    private String reference;
    private SchemaPaymentType schemePaymentType;
    private PaymentParty beneficiaryParty;
    private PaymentParty debtorParty;
    private PaymentParty sponsorParty;

    public Payment() {
        setCurrency(CurrencyType.GBP);
        setPaymentType(PaymentType.CREDIT);
        setSchemePaymentType(SchemaPaymentType.IMMEDIATE);
    }

    public long getPaymentID() {
        return paymentID;
    }

    public BigDecimal getAmount() {
        return (amount==null)?null:amount.setScale(2, RoundingMode.HALF_UP);
    }

    public CurrencyType getCurrency() {
        return currency;
    }

    public int getNumericReference() {
        return numericReference;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public String getPaymentScheme() {
        return paymentScheme;
    }

    public String getPaymentPurpose() {
        return paymentPurpose;
    }

    public LocalDate getProcessingDate() {
        return processingDate;
    }

    public String getReference() {
        return reference;
    }

    public SchemaPaymentType getSchemePaymentType() {
        return schemePaymentType;
    }

    public PaymentParty getBeneficiaryParty() {
        return beneficiaryParty;
    }

    public PaymentParty getDebtorParty() {
        return debtorParty;
    }

    public PaymentParty getSponsorParty() {
        return sponsorParty;
    }

    public void setPaymentID(long paymentID) {
        this.paymentID = paymentID;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount.setScale(2, RoundingMode.HALF_UP);
        this.numericReference = this.amount.setScale(2, RoundingMode.HALF_UP).multiply(new BigDecimal(100)).intValue();
    }

    public void setCurrency(CurrencyType currency) {
        this.currency = currency;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public void setPaymentScheme(String paymentScheme) {
        this.paymentScheme = paymentScheme;
    }

    public void setPaymentPurpose(String paymentPurpose) {
        this.paymentPurpose = paymentPurpose;
    }

    public void setProcessingDate(LocalDate processingDate) {
        this.processingDate = processingDate;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public void setSchemePaymentType(SchemaPaymentType schemePaymentType) {
        this.schemePaymentType = schemePaymentType;
    }

    public void setBeneficiaryParty(PaymentParty beneficiaryParty) {
        this.beneficiaryParty = beneficiaryParty;
    }

    public void setDebtorParty(PaymentParty debtorParty) {
        this.debtorParty = debtorParty;
    }

    public void setSponsorParty(PaymentParty sponsorParty) {
        this.sponsorParty = sponsorParty;
    }

    @Override
    public String toString() {
        return String.format(
                "Payment[id=%d, amount='%s', currency='%s']",
                getPaymentID(), getAmount().toString(), getCurrency());
    }
}
