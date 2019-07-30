import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class AccountingTransactionComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('gha-accounting-transaction div table .btn-danger'));
  title = element.all(by.css('gha-accounting-transaction div h2#page-heading span')).first();

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

export class AccountingTransactionUpdatePage {
  pageTitle = element(by.id('gha-accounting-transaction-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  descriptionInput = element(by.id('field_description'));
  serviceOutletCodeInput = element(by.id('field_serviceOutletCode'));
  accountNameInput = element(by.id('field_accountName'));
  accountNumberInput = element(by.id('field_accountNumber'));
  transactionDateInput = element(by.id('field_transactionDate'));
  transactionAmountInput = element(by.id('field_transactionAmount'));
  incrementAccountInput = element(by.id('field_incrementAccount'));

  async getPageTitle() {
    return this.pageTitle.getText();
  }

  async setDescriptionInput(description) {
    await this.descriptionInput.sendKeys(description);
  }

  async getDescriptionInput() {
    return await this.descriptionInput.getAttribute('value');
  }

  async setServiceOutletCodeInput(serviceOutletCode) {
    await this.serviceOutletCodeInput.sendKeys(serviceOutletCode);
  }

  async getServiceOutletCodeInput() {
    return await this.serviceOutletCodeInput.getAttribute('value');
  }

  async setAccountNameInput(accountName) {
    await this.accountNameInput.sendKeys(accountName);
  }

  async getAccountNameInput() {
    return await this.accountNameInput.getAttribute('value');
  }

  async setAccountNumberInput(accountNumber) {
    await this.accountNumberInput.sendKeys(accountNumber);
  }

  async getAccountNumberInput() {
    return await this.accountNumberInput.getAttribute('value');
  }

  async setTransactionDateInput(transactionDate) {
    await this.transactionDateInput.sendKeys(transactionDate);
  }

  async getTransactionDateInput() {
    return await this.transactionDateInput.getAttribute('value');
  }

  async setTransactionAmountInput(transactionAmount) {
    await this.transactionAmountInput.sendKeys(transactionAmount);
  }

  async getTransactionAmountInput() {
    return await this.transactionAmountInput.getAttribute('value');
  }

  getIncrementAccountInput(timeout?: number) {
    return this.incrementAccountInput;
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

export class AccountingTransactionDeleteDialog {
  private dialogTitle = element(by.id('gha-delete-accountingTransaction-heading'));
  private confirmButton = element(by.id('gha-confirm-delete-accountingTransaction'));

  async getDialogTitle() {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
