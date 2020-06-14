package com.elmodeer.blog.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class Address implements Serializable {

    @Id
    private Long id;

    private String address;
    private String city;
    // TODO!! add country enum
    // private ECountry country;
    private Integer zipCode;

    @OneToOne
    @MapsId
    @JsonIgnore
    private User user;

    public Address(String address, String city, Integer zipCode) {
        this.address = address;
        this.city = city;
        this.zipCode = zipCode;

    }
}
