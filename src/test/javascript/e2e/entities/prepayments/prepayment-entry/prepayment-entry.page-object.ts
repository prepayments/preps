import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class PrepaymentEntryComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('gha-prepayment-entry div table .btn-danger'));
  title = element.all(by.css('gha-prepayment-entry div h2#page-heading span')).first();

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

export class PrepaymentEntryUpdatePage {
  pageTitle = element(by.id('gha-prepayment-entry-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  accountNumberInput = element(by.id('field_accountNumber'));
  accountNameInput = element(by.id('field_accountName'));
  prepaymentIdInput = element(by.id('field_prepaymentId'));
  prepaymentDateInput = element(by.id('field_prepaymentDate'));
  particularsInput = element(by.id('field_particulars'));
  serviceOutletInput = element(by.id('field_serviceOutlet'));
  prepaymentAmountInput = element(by.id('field_prepaymentAmount'));
  monthsInput = element(by.id('field_months'));
  supplierNameInput = element(by.id('field_supplierName'));
  invoiceNumberInput = element(by.id('field_invoiceNumber'));

  async getPageTitle() {
    return this.pageTitle.getText();
  }

  async setAccountNumberInput(accountNumber) {
    await this.accountNumberInput.sendKeys(accountNumber);
  }

  async getAccountNumberInput() {
    return await this.accountNumberInput.getAttribute('value');
  }

  async setAccountNameInput(accountName) {
    await this.accountNameInput.sendKeys(accountName);
  }

  async getAccountNameInput() {
    return await this.accountNameInput.getAttribute('value');
  }

  async setPrepaymentIdInput(prepaymentId) {
    await this.prepaymentIdInput.sendKeys(prepaymentId);
  }

  async getPrepaymentIdInput() {
    return await this.prepaymentIdInput.getAttribute('value');
  }

  async setPrepaymentDateInput(prepaymentDate) {
    await this.prepaymentDateInput.sendKeys(prepaymentDate);
  }

  async getPrepaymentDateInput() {
    return await this.prepaymentDateInput.getAttribute('value');
  }

  async setParticularsInput(particulars) {
    await this.particularsInput.sendKeys(particulars);
  }

  async getParticularsInput() {
    return await this.particularsInput.getAttribute('value');
  }

  async setServiceOutletInput(serviceOutlet) {
    await this.serviceOutletInput.sendKeys(serviceOutlet);
  }

  async getServiceOutletInput() {
    return await this.serviceOutletInput.getAttribute('value');
  }

  async setPrepaymentAmountInput(prepaymentAmount) {
    await this.prepaymentAmountInput.sendKeys(prepaymentAmount);
  }

  async getPrepaymentAmountInput() {
    return await this.prepaymentAmountInput.getAttribute('value');
  }

  async setMonthsInput(months) {
    await this.monthsInput.sendKeys(months);
  }

  async getMonthsInput() {
    return await this.monthsInput.getAttribute('value');
  }

  async setSupplierNameInput(supplierName) {
    await this.supplierNameInput.sendKeys(supplierName);
  }

  async getSupplierNameInput() {
    return await this.supplierNameInput.getAttribute('value');
  }

  async setInvoiceNumberInput(invoiceNumber) {
    await this.invoiceNumberInput.sendKeys(invoiceNumber);
  }

  async getInvoiceNumberInput() {
    return await this.invoiceNumberInput.getAttribute('value');
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

export class PrepaymentEntryDeleteDialog {
  private dialogTitle = element(by.id('gha-delete-prepaymentEntry-heading'));
  private confirmButton = element(by.id('gha-confirm-delete-prepaymentEntry'));

  async getDialogTitle() {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
