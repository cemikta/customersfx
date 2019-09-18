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
 * User entity
 *
 * @author Cem Ikta
 */
@Entity
@Table(name = "app_user", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"username"})
})
@NamedQueries({
    @NamedQuery(name = User.FIND_ALL,
            query = "SELECT u FROM User u ORDER BY u.name"),
    @NamedQuery(name = User.FIND_BY_USERNAME,
            query = "SELECT u FROM User u WHERE u.username = :username"),
    @NamedQuery(name = User.FIND_BY_FILTER,
            query = "SELECT u FROM User u WHERE u.username LIKE :search OR u.name LIKE :search ORDER BY u.name")
})
@AttributeOverride(name = "id", column = @Column(name = "app_user_id", nullable = false,
        columnDefinition = "BIGINT UNSIGNED"))
public class User extends BaseEntity {

    public static final String FIND_ALL = "User.findAll";
    public static final String FIND_BY_USERNAME = "User.findByUsername";
    public static final String FIND_BY_FILTER = "User.findByFilter";

    @Column(name = "username", nullable = false, length = 20)
    private String username;

    @Column(name = "password", nullable = false, length = 20)
    private String password;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "app_user_role", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Lob
    @Column(name = "notes", length = 65535, columnDefinition = "TEXT")
    private String notes;

    public User() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return this.name;
    }

}
