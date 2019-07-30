import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class TransactionAccountComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('gha-transaction-account div table .btn-danger'));
  title = element.all(by.css('gha-transaction-account div h2#page-heading span')).first();

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

export class TransactionAccountUpdatePage {
  pageTitle = element(by.id('gha-transaction-account-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  accountNameInput = element(by.id('field_accountName'));
  accountNumberInput = element(by.id('field_accountNumber'));
  accountTypeSelect = element(by.id('field_accountType'));
  openingDateInput = element(by.id('field_openingDate'));
  originatingFileTokenInput = element(by.id('field_originatingFileToken'));

  async getPageTitle() {
    return this.pageTitle.getText();
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

  async setAccountTypeSelect(accountType) {
    await this.accountTypeSelect.sendKeys(accountType);
  }

  async getAccountTypeSelect() {
    return await this.accountTypeSelect.element(by.css('option:checked')).getText();
  }

  async accountTypeSelectLastOption(timeout?: number) {
    await this.accountTypeSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async setOpeningDateInput(openingDate) {
    await this.openingDateInput.sendKeys(openingDate);
  }

  async getOpeningDateInput() {
    return await this.openingDateInput.getAttribute('value');
  }

  async setOriginatingFileTokenInput(originatingFileToken) {
    await this.originatingFileTokenInput.sendKeys(originatingFileToken);
  }

  async getOriginatingFileTokenInput() {
    return await this.originatingFileTokenInput.getAttribute('value');
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

export class TransactionAccountDeleteDialog {
  private dialogTitle = element(by.id('gha-delete-transactionAccount-heading'));
  private confirmButton = element(by.id('gha-confirm-delete-transactionAccount'));

  async getDialogTitle() {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
