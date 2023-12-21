package tech.reliab.course.kurbanovar.bank.entity.jsonClasses;

import tech.reliab.course.kurbanovar.bank.entity.PaymentAccount;

public class JsonPayAcc {
    private Integer id;
    private Integer bankID;
    private Integer userID;
    private Double sum;

    public JsonPayAcc(PaymentAccount payAcc) {
        this.id = payAcc.getId();
        this.bankID = payAcc.getBank().getId();
        this.userID = payAcc.getUser().getId();
        this.sum = payAcc.getSum();
    }

    public Integer getId() {return this.id;}

    public void setId(Integer id) {this.id = id;}

    public Integer getBankID() {return this.bankID;}

    public void setBankID(Integer bankID) {this.bankID = bankID;}

    public Integer getUserID() {return this.userID;}

    public void setUserID(Integer userID) {this.userID = userID;}

    public Double getSum() {return this.sum;}

    public void setSum(Double amount) {this.sum = amount;}
}
