package com.kelvin.enterprice.Model;

public class ModelDonor {
    private String donor_id, donor_name, donor_address,donor_firstname,donor_lastname, donor_mobile, donor_age, donor_login_id;

    public ModelDonor(String donor_id, String donor_name, String donor_address, String donor_firstname, String donor_lastname, String donor_mobile, String donor_age, String donor_login_id) {
        this.donor_id = donor_id;
        this.donor_name = donor_name;
        this.donor_address = donor_address;
        this.donor_firstname = donor_firstname;
        this.donor_lastname = donor_lastname;
        this.donor_mobile = donor_mobile;
        this.donor_age = donor_age;
        this.donor_login_id = donor_login_id;
    }

    public ModelDonor() {
    }

    public String getDonor_id() {
        return donor_id;
    }

    public void setDonor_id(String donor_id) {
        this.donor_id = donor_id;
    }

    public String getDonor_name() {
        return donor_name;
    }

    public void setDonor_name(String donor_name) {
        this.donor_name = donor_name;
    }

    public String getDonor_address() {
        return donor_address;
    }

    public void setDonor_address(String donor_address) {
        this.donor_address = donor_address;
    }

    public String getDonor_firstname() {
        return donor_firstname;
    }

    public void setDonor_firstname(String donor_firstname) {
        this.donor_firstname = donor_firstname;
    }

    public String getDonor_lastname() {
        return donor_lastname;
    }

    public void setDonor_lastname(String donor_lastname) {
        this.donor_lastname = donor_lastname;
    }

    public String getDonor_mobile() {
        return donor_mobile;
    }

    public void setDonor_mobile(String donor_mobile) {
        this.donor_mobile = donor_mobile;
    }

    public String getDonor_age() {
        return donor_age;
    }

    public void setDonor_age(String donor_age) {
        this.donor_age = donor_age;
    }

    public String getDonor_login_id() {
        return donor_login_id;
    }

    public void setDonor_login_id(String donor_login_id) {
        this.donor_login_id = donor_login_id;
    }
}
