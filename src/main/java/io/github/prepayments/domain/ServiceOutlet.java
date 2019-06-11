package io.github.prepayments.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ServiceOutlet.
 */
@Entity
@Table(name = "service_outlet")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "serviceoutlet")
public class ServiceOutlet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "service_outlet_name", nullable = false)
    private String serviceOutletName;

    @NotNull
    @Pattern(regexp = "^[0-9]{3}$")
    @Column(name = "service_outlet_code", nullable = false)
    private String serviceOutletCode;

    @Column(name = "service_outlet_location")
    private String serviceOutletLocation;

    @Column(name = "service_outlet_manager")
    private String serviceOutletManager;

    @Column(name = "number_of_staff")
    private Integer numberOfStaff;

    @Column(name = "building")
    private String building;

    @Column(name = "floor")
    private Integer floor;

    @Column(name = "postal_address")
    private String postalAddress;

    @Column(name = "contact_person_name")
    private String contactPersonName;

    @Column(name = "contact_email")
    private String contactEmail;

    @Column(name = "street")
    private String street;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServiceOutletName() {
        return serviceOutletName;
    }

    public ServiceOutlet serviceOutletName(String serviceOutletName) {
        this.serviceOutletName = serviceOutletName;
        return this;
    }

    public void setServiceOutletName(String serviceOutletName) {
        this.serviceOutletName = serviceOutletName;
    }

    public String getServiceOutletCode() {
        return serviceOutletCode;
    }

    public ServiceOutlet serviceOutletCode(String serviceOutletCode) {
        this.serviceOutletCode = serviceOutletCode;
        return this;
    }

    public void setServiceOutletCode(String serviceOutletCode) {
        this.serviceOutletCode = serviceOutletCode;
    }

    public String getServiceOutletLocation() {
        return serviceOutletLocation;
    }

    public ServiceOutlet serviceOutletLocation(String serviceOutletLocation) {
        this.serviceOutletLocation = serviceOutletLocation;
        return this;
    }

    public void setServiceOutletLocation(String serviceOutletLocation) {
        this.serviceOutletLocation = serviceOutletLocation;
    }

    public String getServiceOutletManager() {
        return serviceOutletManager;
    }

    public ServiceOutlet serviceOutletManager(String serviceOutletManager) {
        this.serviceOutletManager = serviceOutletManager;
        return this;
    }

    public void setServiceOutletManager(String serviceOutletManager) {
        this.serviceOutletManager = serviceOutletManager;
    }

    public Integer getNumberOfStaff() {
        return numberOfStaff;
    }

    public ServiceOutlet numberOfStaff(Integer numberOfStaff) {
        this.numberOfStaff = numberOfStaff;
        return this;
    }

    public void setNumberOfStaff(Integer numberOfStaff) {
        this.numberOfStaff = numberOfStaff;
    }

    public String getBuilding() {
        return building;
    }

    public ServiceOutlet building(String building) {
        this.building = building;
        return this;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public Integer getFloor() {
        return floor;
    }

    public ServiceOutlet floor(Integer floor) {
        this.floor = floor;
        return this;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public String getPostalAddress() {
        return postalAddress;
    }

    public ServiceOutlet postalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
        return this;
    }

    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }

    public String getContactPersonName() {
        return contactPersonName;
    }

    public ServiceOutlet contactPersonName(String contactPersonName) {
        this.contactPersonName = contactPersonName;
        return this;
    }

    public void setContactPersonName(String contactPersonName) {
        this.contactPersonName = contactPersonName;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public ServiceOutlet contactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
        return this;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getStreet() {
        return street;
    }

    public ServiceOutlet street(String street) {
        this.street = street;
        return this;
    }

    public void setStreet(String street) {
        this.street = street;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServiceOutlet)) {
            return false;
        }
        return id != null && id.equals(((ServiceOutlet) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ServiceOutlet{" +
            "id=" + getId() +
            ", serviceOutletName='" + getServiceOutletName() + "'" +
            ", serviceOutletCode='" + getServiceOutletCode() + "'" +
            ", serviceOutletLocation='" + getServiceOutletLocation() + "'" +
            ", serviceOutletManager='" + getServiceOutletManager() + "'" +
            ", numberOfStaff=" + getNumberOfStaff() +
            ", building='" + getBuilding() + "'" +
            ", floor=" + getFloor() +
            ", postalAddress='" + getPostalAddress() + "'" +
            ", contactPersonName='" + getContactPersonName() + "'" +
            ", contactEmail='" + getContactEmail() + "'" +
            ", street='" + getStreet() + "'" +
            "}";
    }
}
