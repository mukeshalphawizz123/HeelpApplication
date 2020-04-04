
package com.frelance.stripePaymentPkg.stripModlePkg;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentMethodDetails {

    @SerializedName("card")
    @Expose
    private Card card;
    @SerializedName("type")
    @Expose
    private String type;

    /**
     * No args constructor for use in serialization
     * 
     */
    public PaymentMethodDetails() {
    }

    /**
     * 
     * @param type
     * @param card
     */
    public PaymentMethodDetails(Card card, String type) {
        super();
        this.card = card;
        this.type = type;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
