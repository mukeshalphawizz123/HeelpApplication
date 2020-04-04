
package com.frelance.stripePaymentPkg.stripModlePkg;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Checks {

    @SerializedName("address_line1_check")
    @Expose
    private Object addressLine1Check;
    @SerializedName("address_postal_code_check")
    @Expose
    private String addressPostalCodeCheck;
    @SerializedName("cvc_check")
    @Expose
    private String cvcCheck;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Checks() {
    }

    /**
     * 
     * @param addressPostalCodeCheck
     * @param cvcCheck
     * @param addressLine1Check
     */
    public Checks(Object addressLine1Check, String addressPostalCodeCheck, String cvcCheck) {
        super();
        this.addressLine1Check = addressLine1Check;
        this.addressPostalCodeCheck = addressPostalCodeCheck;
        this.cvcCheck = cvcCheck;
    }

    public Object getAddressLine1Check() {
        return addressLine1Check;
    }

    public void setAddressLine1Check(Object addressLine1Check) {
        this.addressLine1Check = addressLine1Check;
    }

    public String getAddressPostalCodeCheck() {
        return addressPostalCodeCheck;
    }

    public void setAddressPostalCodeCheck(String addressPostalCodeCheck) {
        this.addressPostalCodeCheck = addressPostalCodeCheck;
    }

    public String getCvcCheck() {
        return cvcCheck;
    }

    public void setCvcCheck(String cvcCheck) {
        this.cvcCheck = cvcCheck;
    }

}
