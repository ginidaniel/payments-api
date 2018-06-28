package payments.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "PaymentParty")
public class PaymentParty {

    private String accountName;
    private String accountNumber;
    private String accountNumberCode;
    private String address;
    private int bankID;
    private String bankIDCode;
    private String name;

    public PaymentParty() {}

    public String getAccountName() {
        return accountName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountNumberCode() {
        return accountNumberCode;
    }

    public String getAddress() {
        return address;
    }

    public int getBankID() {
        return bankID;
    }

    public String getBankIDCode() {
        return bankIDCode;
    }

    public String getName() {
        return name;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setAccountNumberCode(String accountNumberCode) {
        this.accountNumberCode = accountNumberCode;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setBankID(int bankID) {
        this.bankID = bankID;
    }

    public void setBankIDCode(String bankIDCode) {
        this.bankIDCode = bankIDCode;
    }

    public void setName(String name) {
        this.name = name;
    }
}
