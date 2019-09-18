/*
 * Copyright (C) 2017 Cem Ikta
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *
 */

package com.cemikta.customersfx.model;

import javax.persistence.*;

/**
 * Customer entity
 *
 * @author Cem Ikta
 */
@Entity
@Table(name = "customer")
@NamedQueries({
    @NamedQuery(name = Customer.FIND_ALL,
            query = "SELECT c FROM Customer c ORDER BY c.companyName"),
    @NamedQuery(name = Customer.FIND_BY_FILTER,
            query = "SELECT c FROM Customer c " +
                    "LEFT JOIN c.category ca " +
                    "LEFT JOIN c.country co " +
                    "WHERE c.companyName LIKE :search OR " +
                    "c.contactTitle LIKE :search OR " +
                    "c.contactFirstName LIKE :search OR " +
                    "c.contactLastName LIKE :search OR " +
                    "(ca.id IS NOT NULL AND LOWER(ca.name) LIKE LOWER(:search)) OR " +
                    "c.city LIKE :search OR " +
                    "(co.id IS NOT NULL AND LOWER(co.name) LIKE LOWER(:search)) OR " +
                    "c.skype LIKE :search " +
                    "ORDER BY c.companyName"),
    @NamedQuery(name = Customer.SORT_CREATED_AT,
            query = "SELECT c FROM Customer c ORDER BY c.createdAt DESC")
})
@AttributeOverride(name = "id", column = @Column(name = "customer_id",
        nullable = false, columnDefinition = "BIGINT UNSIGNED"))
public class Customer extends BaseEntity {

    public static final String FIND_ALL = "Customer.findAll";
    public static final String FIND_BY_FILTER = "Customer.findByFilter";
    public static final String SORT_CREATED_AT = "Customer.sortCreatedAt";

    @Column(name = "company_name", nullable = false, length = 100)
    private String companyName;

    @JoinColumn(name = "category_id", referencedColumnName = "category_id", nullable = false)
    @ManyToOne(optional = false)
    private Category category;

    @Column(name = "contact_title", length = 50)
    private String contactTitle;

    @Column(name = "contact_first_name", nullable = false, length = 50)
    private String contactFirstName;

    @Column(name = "contact_last_name", nullable = false, length = 50)
    private String contactLastName;

    @Lob
    @Column(name = "address", length = 65535, columnDefinition = "TEXT")
    private String address;

    @Column(name = "city", length = 100)
    private String city;

    @Column(name = "region", length = 100)
    private String region;

    @Column(name = "postal_code", length = 50)
    private String postalCode;

    @JoinColumn(name = "country_id", referencedColumnName = "country_id")
    @ManyToOne
    private Country country;

    @Column(name = "phone", length = 50)
    private String phone;

    @Column(name = "fax", length = 50)
    private String fax;

    @Column(name = "mobile", length = 50)
    private String mobile;

    @Column(name = "email", length = 50)
    private String email;

    @Column(name = "homepage", length = 50)
    private String homepage;

    @Column(name = "skype", length = 50)
    private String skype;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @Lob
    @Column(name = "notes", length = 65535, columnDefinition = "TEXT")
    private String notes;

    public Customer() {
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getContactTitle() {
        return contactTitle;
    }

    public void setContactTitle(String contactTitle) {
        this.contactTitle = contactTitle;
    }

    public String getContactFirstName() {
        return contactFirstName;
    }

    public void setContactFirstName(String contactFirstName) {
        this.contactFirstName = contactFirstName;
    }

    public String getContactLastName() {
        return contactLastName;
    }

    public void setContactLastName(String contactLastName) {
        this.contactLastName = contactLastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getSkype() {
        return skype;
    }

    public void setSkype(String skype) {
        this.skype = skype;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
