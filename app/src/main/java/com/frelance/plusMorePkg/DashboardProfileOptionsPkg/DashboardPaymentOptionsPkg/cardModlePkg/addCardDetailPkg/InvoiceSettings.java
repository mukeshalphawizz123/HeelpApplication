
package com.frelance.plusMorePkg.DashboardProfileOptionsPkg.DashboardPaymentOptionsPkg.cardModlePkg.addCardDetailPkg;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InvoiceSettings {

    @SerializedName("custom_fields")
    @Expose
    private Object customFields;
    @SerializedName("default_payment_method")
    @Expose
    private Object defaultPaymentMethod;
    @SerializedName("footer")
    @Expose
    private Object footer;

    /**
     * No args constructor for use in serialization
     * 
     */
    public InvoiceSettings() {
    }

    /**
     * 
     * @param defaultPaymentMethod
     * @param footer
     * @param customFields
     */
    public InvoiceSettings(Object customFields, Object defaultPaymentMethod, Object footer) {
        super();
        this.customFields = customFields;
        this.defaultPaymentMethod = defaultPaymentMethod;
        this.footer = footer;
    }

    public Object getCustomFields() {
        return customFields;
    }

    public void setCustomFields(Object customFields) {
        this.customFields = customFields;
    }

    public Object getDefaultPaymentMethod() {
        return defaultPaymentMethod;
    }

    public void setDefaultPaymentMethod(Object defaultPaymentMethod) {
        this.defaultPaymentMethod = defaultPaymentMethod;
    }

    public Object getFooter() {
        return footer;
    }

    public void setFooter(Object footer) {
        this.footer = footer;
    }

}
