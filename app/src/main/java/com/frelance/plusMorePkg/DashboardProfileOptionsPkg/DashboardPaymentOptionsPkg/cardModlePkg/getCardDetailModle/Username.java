
package com.frelance.plusMorePkg.DashboardProfileOptionsPkg.DashboardPaymentOptionsPkg.cardModlePkg.getCardDetailModle;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Username {

    @SerializedName("name_on_card")
    @Expose
    private String nameOnCard;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Username() {
    }

    /**
     * 
     * @param nameOnCard
     */
    public Username(String nameOnCard) {
        super();
        this.nameOnCard = nameOnCard;
    }

    public String getNameOnCard() {
        return nameOnCard;
    }

    public void setNameOnCard(String nameOnCard) {
        this.nameOnCard = nameOnCard;
    }

}
