import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class ServiceOutletComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('gha-service-outlet div table .btn-danger'));
  title = element.all(by.css('gha-service-outlet div h2#page-heading span')).first();

  async clickOnCreateButton(timeout?: number) {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(timeout?: number) {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons() {
    return this.deleteButtons.count();
  }

  async getTitle() {
    return this.title.getText();
  }
}

export class ServiceOutletUpdatePage {
  pageTitle = element(by.id('gha-service-outlet-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  serviceOutletNameInput = element(by.id('field_serviceOutletName'));
  serviceOutletCodeInput = element(by.id('field_serviceOutletCode'));
  serviceOutletLocationInput = element(by.id('field_serviceOutletLocation'));
  serviceOutletManagerInput = element(by.id('field_serviceOutletManager'));
  numberOfStaffInput = element(by.id('field_numberOfStaff'));
  buildingInput = element(by.id('field_building'));
  floorInput = element(by.id('field_floor'));
  postalAddressInput = element(by.id('field_postalAddress'));
  contactPersonNameInput = element(by.id('field_contactPersonName'));
  contactEmailInput = element(by.id('field_contactEmail'));
  streetInput = element(by.id('field_street'));

  async getPageTitle() {
    return this.pageTitle.getText();
  }

  async setServiceOutletNameInput(serviceOutletName) {
    await this.serviceOutletNameInput.sendKeys(serviceOutletName);
  }

  async getServiceOutletNameInput() {
    return await this.serviceOutletNameInput.getAttribute('value');
  }

  async setServiceOutletCodeInput(serviceOutletCode) {
    await this.serviceOutletCodeInput.sendKeys(serviceOutletCode);
  }

  async getServiceOutletCodeInput() {
    return await this.serviceOutletCodeInput.getAttribute('value');
  }

  async setServiceOutletLocationInput(serviceOutletLocation) {
    await this.serviceOutletLocationInput.sendKeys(serviceOutletLocation);
  }

  async getServiceOutletLocationInput() {
    return await this.serviceOutletLocationInput.getAttribute('value');
  }

  async setServiceOutletManagerInput(serviceOutletManager) {
    await this.serviceOutletManagerInput.sendKeys(serviceOutletManager);
  }

  async getServiceOutletManagerInput() {
    return await this.serviceOutletManagerInput.getAttribute('value');
  }

  async setNumberOfStaffInput(numberOfStaff) {
    await this.numberOfStaffInput.sendKeys(numberOfStaff);
  }

  async getNumberOfStaffInput() {
    return await this.numberOfStaffInput.getAttribute('value');
  }

  async setBuildingInput(building) {
    await this.buildingInput.sendKeys(building);
  }

  async getBuildingInput() {
    return await this.buildingInput.getAttribute('value');
  }

  async setFloorInput(floor) {
    await this.floorInput.sendKeys(floor);
  }

  async getFloorInput() {
    return await this.floorInput.getAttribute('value');
  }

  async setPostalAddressInput(postalAddress) {
    await this.postalAddressInput.sendKeys(postalAddress);
  }

  async getPostalAddressInput() {
    return await this.postalAddressInput.getAttribute('value');
  }

  async setContactPersonNameInput(contactPersonName) {
    await this.contactPersonNameInput.sendKeys(contactPersonName);
  }

  async getContactPersonNameInput() {
    return await this.contactPersonNameInput.getAttribute('value');
  }

  async setContactEmailInput(contactEmail) {
    await this.contactEmailInput.sendKeys(contactEmail);
  }

  async getContactEmailInput() {
    return await this.contactEmailInput.getAttribute('value');
  }

  async setStreetInput(street) {
    await this.streetInput.sendKeys(street);
  }

  async getStreetInput() {
    return await this.streetInput.getAttribute('value');
  }

  async save(timeout?: number) {
    await this.saveButton.click();
  }

  async cancel(timeout?: number) {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class ServiceOutletDeleteDialog {
  private dialogTitle = element(by.id('gha-delete-serviceOutlet-heading'));
  private confirmButton = element(by.id('gha-confirm-delete-serviceOutlet'));

  async getDialogTitle() {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
