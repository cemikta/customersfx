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
 * Category entity
 *
 * @author Cem Ikta
 */
@Entity
@Table(name = "category", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"name"})
})
@NamedQueries({
    @NamedQuery(name = Category.FIND_ALL,
            query = "SELECT c FROM Category c ORDER BY c.name"),
    @NamedQuery(name = Category.FIND_BY_FILTER,
            query = "SELECT c FROM Category c WHERE c.name LIKE :search ORDER BY c.name")
})
@AttributeOverride(name = "id", column = @Column(name = "category_id", nullable = false,
        columnDefinition = "BIGINT UNSIGNED"))
public class Category extends BaseEntity {

    public static final String FIND_ALL = "Category.findAll";
    public static final String FIND_BY_FILTER = "Category.findByFilter";

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Lob
    @Column(name = "notes", length = 65535, columnDefinition = "TEXT")
    private String notes;

    @OneToMany(mappedBy = "category")
    private List<Customer> customerList;

    public Category() {
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
