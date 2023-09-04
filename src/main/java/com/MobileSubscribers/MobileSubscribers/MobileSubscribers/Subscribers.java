package com.MobileSubscribers.MobileSubscribers.MobileSubscribers;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import lombok.AllArgsConstructor;


@Setter
@Getter
@Entity
@Table
@AllArgsConstructor// automating the process of creating a constructor that sets all the fields...


public class Subscribers {

@Id
@SequenceGenerator(
    name = "Subscribers_sequence",
    sequenceName = "Subscribers_sequence",
    allocationSize = 1
)


@GeneratedValue(
    strategy = GenerationType.SEQUENCE,
    generator = "subscribers_sequence"
)
private Long id;
private String msisdn;
private Integer customer_id_owner;
private Integer customer_id_user;

@Column(name = "unix_epoch_millis")
private Long unixEpochMillis;

@Enumerated(EnumType.STRING) // Specify how the enum values should be stored (as strings)
@Column(name = "service_type")
private ServiceType serviceType; // Use the enum type here


public Subscribers() {
    this.unixEpochMillis = System.currentTimeMillis();
}



public Subscribers(Long id, java.lang.String msisdn, Integer customer_id_owner, Integer customer_id_user,
                   ServiceType serviceType, Long unixEpochMillis) {
    this.id = id;
    this.msisdn = msisdn;
    this.customer_id_owner = customer_id_owner;
    this.customer_id_user = customer_id_user;
    this.serviceType = serviceType;
    this.unixEpochMillis = unixEpochMillis;
}



public Subscribers(String msisdn, Integer customer_id_owner, Integer customer_id_user, ServiceType serviceType,
        long unixEpochMillis) {
    this.msisdn = msisdn;
    this.customer_id_owner = customer_id_owner;
    this.customer_id_user = customer_id_user;
    this.serviceType = serviceType;
    this.unixEpochMillis = unixEpochMillis;
}


    public void setServiceType(ServiceType service_type) {
        // Assuming 'service_type' is an instance variable in the Subscribers class.
        this.serviceType = service_type; // Set the instance variable with the provided value.
    }


    @Override
public String toString() {
    return "Subscribers [id=" + id + ", msisdn=" + msisdn + ", customer_id_owner=" + customer_id_owner
            + ", customer_id_user=" + customer_id_user + ", service_type=" + serviceType + ", unixEpochMillis="
            + unixEpochMillis + "]";
}

}


