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
import java.util.List;

/**
 * Country entity
 *
 * @author Cem Ikta
 */
@Entity
@Table(name = "country", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"alpha2_code", "alpha3_code", "numeric_code", "name"})
})
@NamedQueries({
    @NamedQuery(name = Country.FIND_ALL,
            query = "SELECT c FROM Country c ORDER BY c.name"),
    @NamedQuery(name = Country.FIND_BY_FILTER,
            query = "SELECT c FROM Country c " +
                    "WHERE c.alpha2Code LIKE :search OR " +
                    "c.alpha3Code LIKE :search OR " +
                    "str(c.numericCode) LIKE :search OR " +
                    "c.name LIKE :search " +
                    "ORDER BY c.name")
})
@AttributeOverride(name = "id", column = @Column(name = "country_id", nullable = false,
        columnDefinition = "BIGINT UNSIGNED"))
public class Country extends BaseEntity {

    public static final String FIND_ALL = "Country.findAll";
    public static final String FIND_BY_FILTER = "Country.findByFilter";

    @Column(name = "alpha2_code", nullable = false, length = 2)
    private String alpha2Code;

    @Column(name = "alpha3_code", nullable = false, length = 3)
    private String alpha3Code;

    @Column(name = "numeric_code", nullable = false)
    private Short numericCode;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Lob
    @Column(name = "notes", length = 65535, columnDefinition = "TEXT")
    private String notes;

    @OneToMany(mappedBy = "country")
    private List<Customer> customerList;

    public Country() {
    }

    public String getAlpha2Code() {
        return alpha2Code;
    }

    public void setAlpha2Code(String alpha2Code) {
        this.alpha2Code = alpha2Code;
    }

    public String getAlpha3Code() {
        return alpha3Code;
    }

    public void setAlpha3Code(String alpha3Code) {
        this.alpha3Code = alpha3Code;
    }

    public Short getNumericCode() {
        return numericCode;
    }

    public void setNumericCode(Short numericCode) {
        this.numericCode = numericCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<Customer> getCustomerList() {
        return customerList;
    }

    public void setCustomerList(List<Customer> customerList) {
        this.customerList = customerList;
    }

    @Override
    public String toString() {
        return this.name;
    }

}
