
package com.frelance.stripePaymentPkg.stripModlePkg;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data_ {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("object")
    @Expose
    private String object;
    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("amount_refunded")
    @Expose
    private Integer amountRefunded;
    @SerializedName("application")
    @Expose
    private Object application;
    @SerializedName("application_fee")
    @Expose
    private Object applicationFee;
    @SerializedName("application_fee_amount")
    @Expose
    private Object applicationFeeAmount;
    @SerializedName("balance_transaction")
    @Expose
    private String balanceTransaction;
    @SerializedName("billing_details")
    @Expose
    private BillingDetails billingDetails;
    @SerializedName("calculated_statement_descriptor")
    @Expose
    private String calculatedStatementDescriptor;
    @SerializedName("captured")
    @Expose
    private Boolean captured;
    @SerializedName("created")
    @Expose
    private Integer created;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("customer")
    @Expose
    private Object customer;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("destination")
    @Expose
    private Object destination;
    @SerializedName("dispute")
    @Expose
    private Object dispute;
    @SerializedName("disputed")
    @Expose
    private Boolean disputed;
    @SerializedName("failure_code")
    @Expose
    private Object failureCode;
    @SerializedName("failure_message")
    @Expose
    private Object failureMessage;
    @SerializedName("fraud_details")
    @Expose
    private List<Object> fraudDetails = null;
    @SerializedName("invoice")
    @Expose
    private Object invoice;
    @SerializedName("livemode")
    @Expose
    private Boolean livemode;
    @SerializedName("metadata")
    @Expose
    private List<Object> metadata = null;
    @SerializedName("on_behalf_of")
    @Expose
    private Object onBehalfOf;
    @SerializedName("order")
    @Expose
    private Object order;
    @SerializedName("outcome")
    @Expose
    private Outcome outcome;
    @SerializedName("paid")
    @Expose
    private Boolean paid;
    @SerializedName("payment_intent")
    @Expose
    private Object paymentIntent;
    @SerializedName("payment_method")
    @Expose
    private String paymentMethod;
    @SerializedName("payment_method_details")
    @Expose
    private PaymentMethodDetails paymentMethodDetails;
    @SerializedName("receipt_email")
    @Expose
    private Object receiptEmail;
    @SerializedName("receipt_number")
    @Expose
    private Object receiptNumber;
    @SerializedName("receipt_url")
    @Expose
    private String receiptUrl;
    @SerializedName("refunded")
    @Expose
    private Boolean refunded;
    @SerializedName("refunds")
    @Expose
    private Refunds refunds;
    @SerializedName("review")
    @Expose
    private Object review;
    @SerializedName("shipping")
    @Expose
    private Object shipping;
    @SerializedName("source")
    @Expose
    private Source source;
    @SerializedName("source_transfer")
    @Expose
    private Object sourceTransfer;
    @SerializedName("statement_descriptor")
    @Expose
    private Object statementDescriptor;
    @SerializedName("statement_descriptor_suffix")
    @Expose
    private Object statementDescriptorSuffix;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("transfer_data")
    @Expose
    private Object transferData;
    @SerializedName("transfer_group")
    @Expose
    private Object transferGroup;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Data_() {
    }

    /**
     * 
     * @param transferData
     * @param metadata
     * @param billingDetails
     * @param livemode
     * @param statementDescriptor
     * @param receiptEmail
     * @param applicationFeeAmount
     * @param destination
     * @param onBehalfOf
     * @param description
     * @param source
     * @param refunds
     * @param applicationFee
     * @param calculatedStatementDescriptor
     * @param shipping
     * @param review
     * @param amountRefunded
     * @param captured
     * @param balanceTransaction
     * @param currency
     * @param paymentMethodDetails
     * @param refunded
     * @param id
     * @param outcome
     * @param receiptNumber
     * @param order
     * @param dispute
     * @param amount
     * @param disputed
     * @param failureCode
     * @param created
     * @param paymentIntent
     * @param statementDescriptorSuffix
     * @param application
     * @param sourceTransfer
     * @param transferGroup
     * @param paid
     * @param paymentMethod
     * @param invoice
     * @param receiptUrl
     * @param failureMessage
     * @param object
     * @param customer
     * @param fraudDetails
     * @param status
     */
    public Data_(String id, String object, Integer amount, Integer amountRefunded, Object application, Object applicationFee, Object applicationFeeAmount, String balanceTransaction, BillingDetails billingDetails, String calculatedStatementDescriptor, Boolean captured, Integer created, String currency, Object customer, String description, Object destination, Object dispute, Boolean disputed, Object failureCode, Object failureMessage, List<Object> fraudDetails, Object invoice, Boolean livemode, List<Object> metadata, Object onBehalfOf, Object order, Outcome outcome, Boolean paid, Object paymentIntent, String paymentMethod, PaymentMethodDetails paymentMethodDetails, Object receiptEmail, Object receiptNumber, String receiptUrl, Boolean refunded, Refunds refunds, Object review, Object shipping, Source source, Object sourceTransfer, Object statementDescriptor, Object statementDescriptorSuffix, String status, Object transferData, Object transferGroup) {
        super();
        this.id = id;
        this.object = object;
        this.amount = amount;
        this.amountRefunded = amountRefunded;
        this.application = application;
        this.applicationFee = applicationFee;
        this.applicationFeeAmount = applicationFeeAmount;
        this.balanceTransaction = balanceTransaction;
        this.billingDetails = billingDetails;
        this.calculatedStatementDescriptor = calculatedStatementDescriptor;
        this.captured = captured;
        this.created = created;
        this.currency = currency;
        this.customer = customer;
        this.description = description;
        this.destination = destination;
        this.dispute = dispute;
        this.disputed = disputed;
        this.failureCode = failureCode;
        this.failureMessage = failureMessage;
        this.fraudDetails = fraudDetails;
        this.invoice = invoice;
        this.livemode = livemode;
        this.metadata = metadata;
        this.onBehalfOf = onBehalfOf;
        this.order = order;
        this.outcome = outcome;
        this.paid = paid;
        this.paymentIntent = paymentIntent;
        this.paymentMethod = paymentMethod;
        this.paymentMethodDetails = paymentMethodDetails;
        this.receiptEmail = receiptEmail;
        this.receiptNumber = receiptNumber;
        this.receiptUrl = receiptUrl;
        this.refunded = refunded;
        this.refunds = refunds;
        this.review = review;
        this.shipping = shipping;
        this.source = source;
        this.sourceTransfer = sourceTransfer;
        this.statementDescriptor = statementDescriptor;
        this.statementDescriptorSuffix = statementDescriptorSuffix;
        this.status = status;
        this.transferData = transferData;
        this.transferGroup = transferGroup;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getAmountRefunded() {
        return amountRefunded;
    }

    public void setAmountRefunded(Integer amountRefunded) {
        this.amountRefunded = amountRefunded;
    }

    public Object getApplication() {
        return application;
    }

    public void setApplication(Object application) {
        this.application = application;
    }

    public Object getApplicationFee() {
        return applicationFee;
    }

    public void setApplicationFee(Object applicationFee) {
        this.applicationFee = applicationFee;
    }

    public Object getApplicationFeeAmount() {
        return applicationFeeAmount;
    }

    public void setApplicationFeeAmount(Object applicationFeeAmount) {
        this.applicationFeeAmount = applicationFeeAmount;
    }

    public String getBalanceTransaction() {
        return balanceTransaction;
    }

    public void setBalanceTransaction(String balanceTransaction) {
        this.balanceTransaction = balanceTransaction;
    }

    public BillingDetails getBillingDetails() {
        return billingDetails;
    }

    public void setBillingDetails(BillingDetails billingDetails) {
        this.billingDetails = billingDetails;
    }

    public String getCalculatedStatementDescriptor() {
        return calculatedStatementDescriptor;
    }

    public void setCalculatedStatementDescriptor(String calculatedStatementDescriptor) {
        this.calculatedStatementDescriptor = calculatedStatementDescriptor;
    }

    public Boolean getCaptured() {
        return captured;
    }

    public void setCaptured(Boolean captured) {
        this.captured = captured;
    }

    public Integer getCreated() {
        return created;
    }

    public void setCreated(Integer created) {
        this.created = created;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Object getCustomer() {
        return customer;
    }

    public void setCustomer(Object customer) {
        this.customer = customer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Object getDestination() {
        return destination;
    }

    public void setDestination(Object destination) {
        this.destination = destination;
    }

    public Object getDispute() {
        return dispute;
    }

    public void setDispute(Object dispute) {
        this.dispute = dispute;
    }

    public Boolean getDisputed() {
        return disputed;
    }

    public void setDisputed(Boolean disputed) {
        this.disputed = disputed;
    }

    public Object getFailureCode() {
        return failureCode;
    }

    public void setFailureCode(Object failureCode) {
        this.failureCode = failureCode;
    }

    public Object getFailureMessage() {
        return failureMessage;
    }

    public void setFailureMessage(Object failureMessage) {
        this.failureMessage = failureMessage;
    }

    public List<Object> getFraudDetails() {
        return fraudDetails;
    }

    public void setFraudDetails(List<Object> fraudDetails) {
        this.fraudDetails = fraudDetails;
    }

    public Object getInvoice() {
        return invoice;
    }

    public void setInvoice(Object invoice) {
        this.invoice = invoice;
    }

    public Boolean getLivemode() {
        return livemode;
    }

    public void setLivemode(Boolean livemode) {
        this.livemode = livemode;
    }

    public List<Object> getMetadata() {
        return metadata;
    }

    public void setMetadata(List<Object> metadata) {
        this.metadata = metadata;
    }

    public Object getOnBehalfOf() {
        return onBehalfOf;
    }

    public void setOnBehalfOf(Object onBehalfOf) {
        this.onBehalfOf = onBehalfOf;
    }

    public Object getOrder() {
        return order;
    }

    public void setOrder(Object order) {
        this.order = order;
    }

    public Outcome getOutcome() {
        return outcome;
    }

    public void setOutcome(Outcome outcome) {
        this.outcome = outcome;
    }

    public Boolean getPaid() {
        return paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    public Object getPaymentIntent() {
        return paymentIntent;
    }

    public void setPaymentIntent(Object paymentIntent) {
        this.paymentIntent = paymentIntent;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public PaymentMethodDetails getPaymentMethodDetails() {
        return paymentMethodDetails;
    }

    public void setPaymentMethodDetails(PaymentMethodDetails paymentMethodDetails) {
        this.paymentMethodDetails = paymentMethodDetails;
    }

    public Object getReceiptEmail() {
        return receiptEmail;
    }

    public void setReceiptEmail(Object receiptEmail) {
        this.receiptEmail = receiptEmail;
    }

    public Object getReceiptNumber() {
        return receiptNumber;
    }

    public void setReceiptNumber(Object receiptNumber) {
        this.receiptNumber = receiptNumber;
    }

    public String getReceiptUrl() {
        return receiptUrl;
    }

    public void setReceiptUrl(String receiptUrl) {
        this.receiptUrl = receiptUrl;
    }

    public Boolean getRefunded() {
        return refunded;
    }

    public void setRefunded(Boolean refunded) {
        this.refunded = refunded;
    }

    public Refunds getRefunds() {
        return refunds;
    }

    public void setRefunds(Refunds refunds) {
        this.refunds = refunds;
    }

    public Object getReview() {
        return review;
    }

    public void setReview(Object review) {
        this.review = review;
    }

    public Object getShipping() {
        return shipping;
    }

    public void setShipping(Object shipping) {
        this.shipping = shipping;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public Object getSourceTransfer() {
        return sourceTransfer;
    }

    public void setSourceTransfer(Object sourceTransfer) {
        this.sourceTransfer = sourceTransfer;
    }

    public Object getStatementDescriptor() {
        return statementDescriptor;
    }

    public void setStatementDescriptor(Object statementDescriptor) {
        this.statementDescriptor = statementDescriptor;
    }

    public Object getStatementDescriptorSuffix() {
        return statementDescriptorSuffix;
    }

    public void setStatementDescriptorSuffix(Object statementDescriptorSuffix) {
        this.statementDescriptorSuffix = statementDescriptorSuffix;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getTransferData() {
        return transferData;
    }

    public void setTransferData(Object transferData) {
        this.transferData = transferData;
    }

    public Object getTransferGroup() {
        return transferGroup;
    }

    public void setTransferGroup(Object transferGroup) {
        this.transferGroup = transferGroup;
    }

}
