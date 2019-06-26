package io.github.prepayments.service.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link io.github.prepayments.domain.ServiceOutlet} entity.
 */
public class ServiceOutletDTO implements Serializable {

    private Long id;

    @NotNull
    private String serviceOutletName;

    @NotNull
    @Pattern(regexp = "^[0-9]{3}$")
    private String serviceOutletCode;

    private String serviceOutletLocation;

    private String serviceOutletManager;

    private Integer numberOfStaff;

    private String building;

    private Integer floor;

    private String postalAddress;

    private String contactPersonName;

    private String contactEmail;

    private String street;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServiceOutletName() {
        return serviceOutletName;
    }

    public void setServiceOutletName(String serviceOutletName) {
        this.serviceOutletName = serviceOutletName;
    }

    public String getServiceOutletCode() {
        return serviceOutletCode;
    }

    public void setServiceOutletCode(String serviceOutletCode) {
        this.serviceOutletCode = serviceOutletCode;
    }

    public String getServiceOutletLocation() {
        return serviceOutletLocation;
    }

    public void setServiceOutletLocation(String serviceOutletLocation) {
        this.serviceOutletLocation = serviceOutletLocation;
    }

    public String getServiceOutletManager() {
        return serviceOutletManager;
    }

    public void setServiceOutletManager(String serviceOutletManager) {
        this.serviceOutletManager = serviceOutletManager;
    }

    public Integer getNumberOfStaff() {
        return numberOfStaff;
    }

    public void setNumberOfStaff(Integer numberOfStaff) {
        this.numberOfStaff = numberOfStaff;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public String getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }

    public String getContactPersonName() {
        return contactPersonName;
    }

    public void setContactPersonName(String contactPersonName) {
        this.contactPersonName = contactPersonName;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ServiceOutletDTO serviceOutletDTO = (ServiceOutletDTO) o;
        if (serviceOutletDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), serviceOutletDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ServiceOutletDTO{" + "id=" + getId() + ", serviceOutletName='" + getServiceOutletName() + "'" + ", serviceOutletCode='" + getServiceOutletCode() + "'" + ", serviceOutletLocation='" +
            getServiceOutletLocation() + "'" + ", serviceOutletManager='" + getServiceOutletManager() + "'" + ", numberOfStaff=" + getNumberOfStaff() + ", building='" + getBuilding() + "'" +
            ", floor=" + getFloor() + ", postalAddress='" + getPostalAddress() + "'" + ", contactPersonName='" + getContactPersonName() + "'" + ", contactEmail='" + getContactEmail() + "'" +
            ", street='" + getStreet() + "'" + "}";
    }
}
