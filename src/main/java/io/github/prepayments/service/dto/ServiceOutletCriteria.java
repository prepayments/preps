package io.github.prepayments.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link io.github.prepayments.domain.ServiceOutlet} entity. This class is used
 * in {@link io.github.prepayments.web.rest.ServiceOutletResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /service-outlets?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ServiceOutletCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter serviceOutletName;

    private StringFilter serviceOutletCode;

    private StringFilter serviceOutletLocation;

    private StringFilter serviceOutletManager;

    private IntegerFilter numberOfStaff;

    private StringFilter building;

    private IntegerFilter floor;

    private StringFilter postalAddress;

    private StringFilter contactPersonName;

    private StringFilter contactEmail;

    private StringFilter street;

    public ServiceOutletCriteria(){
    }

    public ServiceOutletCriteria(ServiceOutletCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.serviceOutletName = other.serviceOutletName == null ? null : other.serviceOutletName.copy();
        this.serviceOutletCode = other.serviceOutletCode == null ? null : other.serviceOutletCode.copy();
        this.serviceOutletLocation = other.serviceOutletLocation == null ? null : other.serviceOutletLocation.copy();
        this.serviceOutletManager = other.serviceOutletManager == null ? null : other.serviceOutletManager.copy();
        this.numberOfStaff = other.numberOfStaff == null ? null : other.numberOfStaff.copy();
        this.building = other.building == null ? null : other.building.copy();
        this.floor = other.floor == null ? null : other.floor.copy();
        this.postalAddress = other.postalAddress == null ? null : other.postalAddress.copy();
        this.contactPersonName = other.contactPersonName == null ? null : other.contactPersonName.copy();
        this.contactEmail = other.contactEmail == null ? null : other.contactEmail.copy();
        this.street = other.street == null ? null : other.street.copy();
    }

    @Override
    public ServiceOutletCriteria copy() {
        return new ServiceOutletCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getServiceOutletName() {
        return serviceOutletName;
    }

    public void setServiceOutletName(StringFilter serviceOutletName) {
        this.serviceOutletName = serviceOutletName;
    }

    public StringFilter getServiceOutletCode() {
        return serviceOutletCode;
    }

    public void setServiceOutletCode(StringFilter serviceOutletCode) {
        this.serviceOutletCode = serviceOutletCode;
    }

    public StringFilter getServiceOutletLocation() {
        return serviceOutletLocation;
    }

    public void setServiceOutletLocation(StringFilter serviceOutletLocation) {
        this.serviceOutletLocation = serviceOutletLocation;
    }

    public StringFilter getServiceOutletManager() {
        return serviceOutletManager;
    }

    public void setServiceOutletManager(StringFilter serviceOutletManager) {
        this.serviceOutletManager = serviceOutletManager;
    }

    public IntegerFilter getNumberOfStaff() {
        return numberOfStaff;
    }

    public void setNumberOfStaff(IntegerFilter numberOfStaff) {
        this.numberOfStaff = numberOfStaff;
    }

    public StringFilter getBuilding() {
        return building;
    }

    public void setBuilding(StringFilter building) {
        this.building = building;
    }

    public IntegerFilter getFloor() {
        return floor;
    }

    public void setFloor(IntegerFilter floor) {
        this.floor = floor;
    }

    public StringFilter getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(StringFilter postalAddress) {
        this.postalAddress = postalAddress;
    }

    public StringFilter getContactPersonName() {
        return contactPersonName;
    }

    public void setContactPersonName(StringFilter contactPersonName) {
        this.contactPersonName = contactPersonName;
    }

    public StringFilter getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(StringFilter contactEmail) {
        this.contactEmail = contactEmail;
    }

    public StringFilter getStreet() {
        return street;
    }

    public void setStreet(StringFilter street) {
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
        final ServiceOutletCriteria that = (ServiceOutletCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(serviceOutletName, that.serviceOutletName) &&
            Objects.equals(serviceOutletCode, that.serviceOutletCode) &&
            Objects.equals(serviceOutletLocation, that.serviceOutletLocation) &&
            Objects.equals(serviceOutletManager, that.serviceOutletManager) &&
            Objects.equals(numberOfStaff, that.numberOfStaff) &&
            Objects.equals(building, that.building) &&
            Objects.equals(floor, that.floor) &&
            Objects.equals(postalAddress, that.postalAddress) &&
            Objects.equals(contactPersonName, that.contactPersonName) &&
            Objects.equals(contactEmail, that.contactEmail) &&
            Objects.equals(street, that.street);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        serviceOutletName,
        serviceOutletCode,
        serviceOutletLocation,
        serviceOutletManager,
        numberOfStaff,
        building,
        floor,
        postalAddress,
        contactPersonName,
        contactEmail,
        street
        );
    }

    @Override
    public String toString() {
        return "ServiceOutletCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (serviceOutletName != null ? "serviceOutletName=" + serviceOutletName + ", " : "") +
                (serviceOutletCode != null ? "serviceOutletCode=" + serviceOutletCode + ", " : "") +
                (serviceOutletLocation != null ? "serviceOutletLocation=" + serviceOutletLocation + ", " : "") +
                (serviceOutletManager != null ? "serviceOutletManager=" + serviceOutletManager + ", " : "") +
                (numberOfStaff != null ? "numberOfStaff=" + numberOfStaff + ", " : "") +
                (building != null ? "building=" + building + ", " : "") +
                (floor != null ? "floor=" + floor + ", " : "") +
                (postalAddress != null ? "postalAddress=" + postalAddress + ", " : "") +
                (contactPersonName != null ? "contactPersonName=" + contactPersonName + ", " : "") +
                (contactEmail != null ? "contactEmail=" + contactEmail + ", " : "") +
                (street != null ? "street=" + street + ", " : "") +
            "}";
    }

}
