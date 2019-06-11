import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class RegisteredSupplierComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('gha-registered-supplier div table .btn-danger'));
  title = element.all(by.css('gha-registered-supplier div h2#page-heading span')).first();

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

export class RegisteredSupplierUpdatePage {
  pageTitle = element(by.id('gha-registered-supplier-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  supplierNameInput = element(by.id('field_supplierName'));
  supplierAddressInput = element(by.id('field_supplierAddress'));
  phoneNumberInput = element(by.id('field_phoneNumber'));
  supplierEmailInput = element(by.id('field_supplierEmail'));
  bankAccountNameInput = element(by.id('field_bankAccountName'));
  bankAccountNumberInput = element(by.id('field_bankAccountNumber'));
  supplierBankNameInput = element(by.id('field_supplierBankName'));
  supplierBankBranchInput = element(by.id('field_supplierBankBranch'));
  bankSwiftCodeInput = element(by.id('field_bankSwiftCode'));
  bankPhysicalAddressInput = element(by.id('field_bankPhysicalAddress'));
  locallyDomiciledInput = element(by.id('field_locallyDomiciled'));
  taxAuthorityPINInput = element(by.id('field_taxAuthorityPIN'));

  async getPageTitle() {
    return this.pageTitle.getText();
  }

  async setSupplierNameInput(supplierName) {
    await this.supplierNameInput.sendKeys(supplierName);
  }

  async getSupplierNameInput() {
    return await this.supplierNameInput.getAttribute('value');
  }

  async setSupplierAddressInput(supplierAddress) {
    await this.supplierAddressInput.sendKeys(supplierAddress);
  }

  async getSupplierAddressInput() {
    return await this.supplierAddressInput.getAttribute('value');
  }

  async setPhoneNumberInput(phoneNumber) {
    await this.phoneNumberInput.sendKeys(phoneNumber);
  }

  async getPhoneNumberInput() {
    return await this.phoneNumberInput.getAttribute('value');
  }

  async setSupplierEmailInput(supplierEmail) {
    await this.supplierEmailInput.sendKeys(supplierEmail);
  }

  async getSupplierEmailInput() {
    return await this.supplierEmailInput.getAttribute('value');
  }

  async setBankAccountNameInput(bankAccountName) {
    await this.bankAccountNameInput.sendKeys(bankAccountName);
  }

  async getBankAccountNameInput() {
    return await this.bankAccountNameInput.getAttribute('value');
  }

  async setBankAccountNumberInput(bankAccountNumber) {
    await this.bankAccountNumberInput.sendKeys(bankAccountNumber);
  }

  async getBankAccountNumberInput() {
    return await this.bankAccountNumberInput.getAttribute('value');
  }

  async setSupplierBankNameInput(supplierBankName) {
    await this.supplierBankNameInput.sendKeys(supplierBankName);
  }

  async getSupplierBankNameInput() {
    return await this.supplierBankNameInput.getAttribute('value');
  }

  async setSupplierBankBranchInput(supplierBankBranch) {
    await this.supplierBankBranchInput.sendKeys(supplierBankBranch);
  }

  async getSupplierBankBranchInput() {
    return await this.supplierBankBranchInput.getAttribute('value');
  }

  async setBankSwiftCodeInput(bankSwiftCode) {
    await this.bankSwiftCodeInput.sendKeys(bankSwiftCode);
  }

  async getBankSwiftCodeInput() {
    return await this.bankSwiftCodeInput.getAttribute('value');
  }

  async setBankPhysicalAddressInput(bankPhysicalAddress) {
    await this.bankPhysicalAddressInput.sendKeys(bankPhysicalAddress);
  }

  async getBankPhysicalAddressInput() {
    return await this.bankPhysicalAddressInput.getAttribute('value');
  }

  getLocallyDomiciledInput(timeout?: number) {
    return this.locallyDomiciledInput;
  }
  async setTaxAuthorityPINInput(taxAuthorityPIN) {
    await this.taxAuthorityPINInput.sendKeys(taxAuthorityPIN);
  }

  async getTaxAuthorityPINInput() {
    return await this.taxAuthorityPINInput.getAttribute('value');
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

export class RegisteredSupplierDeleteDialog {
  private dialogTitle = element(by.id('gha-delete-registeredSupplier-heading'));
  private confirmButton = element(by.id('gha-confirm-delete-registeredSupplier'));

  async getDialogTitle() {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
