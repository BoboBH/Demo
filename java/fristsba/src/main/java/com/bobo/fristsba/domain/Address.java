package com.bobo.fristsba.domain;

public class Address {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column address.id
     *
     * @mbg.generated Tue Dec 17 18:09:34 CST 2019
     */
    private String id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column address.country
     *
     * @mbg.generated Tue Dec 17 18:09:34 CST 2019
     */
    private String country;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column address.detail
     *
     * @mbg.generated Tue Dec 17 18:09:34 CST 2019
     */
    private String detail;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column address.id
     *
     * @return the value of address.id
     *
     * @mbg.generated Tue Dec 17 18:09:34 CST 2019
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column address.id
     *
     * @param id the value for address.id
     *
     * @mbg.generated Tue Dec 17 18:09:34 CST 2019
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column address.country
     *
     * @return the value of address.country
     *
     * @mbg.generated Tue Dec 17 18:09:34 CST 2019
     */
    public String getCountry() {
        return country;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column address.country
     *
     * @param country the value for address.country
     *
     * @mbg.generated Tue Dec 17 18:09:34 CST 2019
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column address.detail
     *
     * @return the value of address.detail
     *
     * @mbg.generated Tue Dec 17 18:09:34 CST 2019
     */
    public String getDetail() {
        return detail;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column address.detail
     *
     * @param detail the value for address.detail
     *
     * @mbg.generated Tue Dec 17 18:09:34 CST 2019
     */
    public void setDetail(String detail) {
        this.detail = detail;
    }
}