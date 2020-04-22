
package com.frelance.plusMorePkg.DashboardProfileOptionsPkg.DashboardPaymentOptionsPkg.cardModlePkg.addCardDetailPkg;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("object")
    @Expose
    private String object;
    @SerializedName("address")
    @Expose
    private Object address;
    @SerializedName("balance")
    @Expose
    private Integer balance;
    @SerializedName("created")
    @Expose
    private Integer created;
    @SerializedName("currency")
    @Expose
    private Object currency;
    @SerializedName("default_source")
    @Expose
    private String defaultSource;
    @SerializedName("delinquent")
    @Expose
    private Boolean delinquent;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("discount")
    @Expose
    private Object discount;
    @SerializedName("email")
    @Expose
    private Object email;
    @SerializedName("invoice_prefix")
    @Expose
    private String invoicePrefix;
    @SerializedName("invoice_settings")
    @Expose
    private InvoiceSettings invoiceSettings;
    @SerializedName("livemode")
    @Expose
    private Boolean livemode;
    @SerializedName("metadata")
    @Expose
    private List<Object> metadata = null;
    @SerializedName("name")
    @Expose
    private Object name;
    @SerializedName("phone")
    @Expose
    private Object phone;
    @SerializedName("preferred_locales")
    @Expose
    private List<Object> preferredLocales = null;
    @SerializedName("shipping")
    @Expose
    private Object shipping;
    @SerializedName("sources")
    @Expose
    private Sources sources;
    @SerializedName("subscriptions")
    @Expose
    private Subscriptions subscriptions;
    @SerializedName("tax_exempt")
    @Expose
    private String taxExempt;
    @SerializedName("tax_ids")
    @Expose
    private TaxIds taxIds;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Data() {
    }

    /**
     * 
     * @param metadata
     * @param subscriptions
     * @param address
     * @param taxExempt
     * @param livemode
     * @param sources
     * @param created
     * @param description
     * @param discount
     * @param taxIds
     * @param preferredLocales
     * @param balance
     * @param shipping
     * @param invoicePrefix
     * @param phone
     * @param delinquent
     * @param invoiceSettings
     * @param name
     * @param defaultSource
     * @param currency
     * @param id
     * @param email
     * @param object
     */
    public Data(String id, String object, Object address, Integer balance, Integer created, Object currency, String defaultSource, Boolean delinquent, String description, Object discount, Object email, String invoicePrefix, InvoiceSettings invoiceSettings, Boolean livemode, List<Object> metadata, Object name, Object phone, List<Object> preferredLocales, Object shipping, Sources sources, Subscriptions subscriptions, String taxExempt, TaxIds taxIds) {
        super();
        this.id = id;
        this.object = object;
        this.address = address;
        this.balance = balance;
        this.created = created;
        this.currency = currency;
        this.defaultSource = defaultSource;
        this.delinquent = delinquent;
        this.description = description;
        this.discount = discount;
        this.email = email;
        this.invoicePrefix = invoicePrefix;
        this.invoiceSettings = invoiceSettings;
        this.livemode = livemode;
        this.metadata = metadata;
        this.name = name;
        this.phone = phone;
        this.preferredLocales = preferredLocales;
        this.shipping = shipping;
        this.sources = sources;
        this.subscriptions = subscriptions;
        this.taxExempt = taxExempt;
        this.taxIds = taxIds;
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

    public Object getAddress() {
        return address;
    }

    public void setAddress(Object address) {
        this.address = address;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public Integer getCreated() {
        return created;
    }

    public void setCreated(Integer created) {
        this.created = created;
    }

    public Object getCurrency() {
        return currency;
    }

    public void setCurrency(Object currency) {
        this.currency = currency;
    }

    public String getDefaultSource() {
        return defaultSource;
    }

    public void setDefaultSource(String defaultSource) {
        this.defaultSource = defaultSource;
    }

    public Boolean getDelinquent() {
        return delinquent;
    }

    public void setDelinquent(Boolean delinquent) {
        this.delinquent = delinquent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Object getDiscount() {
        return discount;
    }

    public void setDiscount(Object discount) {
        this.discount = discount;
    }

    public Object getEmail() {
        return email;
    }

    public void setEmail(Object email) {
        this.email = email;
    }

    public String getInvoicePrefix() {
        return invoicePrefix;
    }

    public void setInvoicePrefix(String invoicePrefix) {
        this.invoicePrefix = invoicePrefix;
    }

    public InvoiceSettings getInvoiceSettings() {
        return invoiceSettings;
    }

    public void setInvoiceSettings(InvoiceSettings invoiceSettings) {
        this.invoiceSettings = invoiceSettings;
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

    public Object getName() {
        return name;
    }

    public void setName(Object name) {
        this.name = name;
    }

    public Object getPhone() {
        return phone;
    }

    public void setPhone(Object phone) {
        this.phone = phone;
    }

    public List<Object> getPreferredLocales() {
        return preferredLocales;
    }

    public void setPreferredLocales(List<Object> preferredLocales) {
        this.preferredLocales = preferredLocales;
    }

    public Object getShipping() {
        return shipping;
    }

    public void setShipping(Object shipping) {
        this.shipping = shipping;
    }

    public Sources getSources() {
        return sources;
    }

    public void setSources(Sources sources) {
        this.sources = sources;
    }

    public Subscriptions getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(Subscriptions subscriptions) {
        this.subscriptions = subscriptions;
    }

    public String getTaxExempt() {
        return taxExempt;
    }

    public void setTaxExempt(String taxExempt) {
        this.taxExempt = taxExempt;
    }

    public TaxIds getTaxIds() {
        return taxIds;
    }

    public void setTaxIds(TaxIds taxIds) {
        this.taxIds = taxIds;
    }

}
