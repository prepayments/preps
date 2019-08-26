import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class AmortizationUploadComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('gha-amortization-upload div table .btn-danger'));
  title = element.all(by.css('gha-amortization-upload div h2#page-heading span')).first();

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

export class AmortizationUploadUpdatePage {
  pageTitle = element(by.id('gha-amortization-upload-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  accountNameInput = element(by.id('field_accountName'));
  particularsInput = element(by.id('field_particulars'));
  amortizationServiceOutletCodeInput = element(by.id('field_amortizationServiceOutletCode'));
  prepaymentServiceOutletCodeInput = element(by.id('field_prepaymentServiceOutletCode'));
  prepaymentAccountNumberInput = element(by.id('field_prepaymentAccountNumber'));
  expenseAccountNumberInput = element(by.id('field_expenseAccountNumber'));
  prepaymentTransactionIdInput = element(by.id('field_prepaymentTransactionId'));
  prepaymentTransactionDateInput = element(by.id('field_prepaymentTransactionDate'));
  prepaymentTransactionAmountInput = element(by.id('field_prepaymentTransactionAmount'));
  amortizationAmountInput = element(by.id('field_amortizationAmount'));
  numberOfAmortizationsInput = element(by.id('field_numberOfAmortizations'));
  firstAmortizationDateInput = element(by.id('field_firstAmortizationDate'));
  monthlyAmortizationDateInput = element(by.id('field_monthlyAmortizationDate'));
  uploadSuccessfulInput = element(by.id('field_uploadSuccessful'));
  uploadOrphanedInput = element(by.id('field_uploadOrphaned'));
  originatingFileTokenInput = element(by.id('field_originatingFileToken'));
  amortizationTagInput = element(by.id('field_amortizationTag'));

  async getPageTitle() {
    return this.pageTitle.getText();
  }

  async setAccountNameInput(accountName) {
    await this.accountNameInput.sendKeys(accountName);
  }

  async getAccountNameInput() {
    return await this.accountNameInput.getAttribute('value');
  }

  async setParticularsInput(particulars) {
    await this.particularsInput.sendKeys(particulars);
  }

  async getParticularsInput() {
    return await this.particularsInput.getAttribute('value');
  }

  async setAmortizationServiceOutletCodeInput(amortizationServiceOutletCode) {
    await this.amortizationServiceOutletCodeInput.sendKeys(amortizationServiceOutletCode);
  }

  async getAmortizationServiceOutletCodeInput() {
    return await this.amortizationServiceOutletCodeInput.getAttribute('value');
  }

  async setPrepaymentServiceOutletCodeInput(prepaymentServiceOutletCode) {
    await this.prepaymentServiceOutletCodeInput.sendKeys(prepaymentServiceOutletCode);
  }

  async getPrepaymentServiceOutletCodeInput() {
    return await this.prepaymentServiceOutletCodeInput.getAttribute('value');
  }

  async setPrepaymentAccountNumberInput(prepaymentAccountNumber) {
    await this.prepaymentAccountNumberInput.sendKeys(prepaymentAccountNumber);
  }

  async getPrepaymentAccountNumberInput() {
    return await this.prepaymentAccountNumberInput.getAttribute('value');
  }

  async setExpenseAccountNumberInput(expenseAccountNumber) {
    await this.expenseAccountNumberInput.sendKeys(expenseAccountNumber);
  }

  async getExpenseAccountNumberInput() {
    return await this.expenseAccountNumberInput.getAttribute('value');
  }

  async setPrepaymentTransactionIdInput(prepaymentTransactionId) {
    await this.prepaymentTransactionIdInput.sendKeys(prepaymentTransactionId);
  }

  async getPrepaymentTransactionIdInput() {
    return await this.prepaymentTransactionIdInput.getAttribute('value');
  }

  async setPrepaymentTransactionDateInput(prepaymentTransactionDate) {
    await this.prepaymentTransactionDateInput.sendKeys(prepaymentTransactionDate);
  }

  async getPrepaymentTransactionDateInput() {
    return await this.prepaymentTransactionDateInput.getAttribute('value');
  }

  async setPrepaymentTransactionAmountInput(prepaymentTransactionAmount) {
    await this.prepaymentTransactionAmountInput.sendKeys(prepaymentTransactionAmount);
  }

  async getPrepaymentTransactionAmountInput() {
    return await this.prepaymentTransactionAmountInput.getAttribute('value');
  }

  async setAmortizationAmountInput(amortizationAmount) {
    await this.amortizationAmountInput.sendKeys(amortizationAmount);
  }

  async getAmortizationAmountInput() {
    return await this.amortizationAmountInput.getAttribute('value');
  }

  async setNumberOfAmortizationsInput(numberOfAmortizations) {
    await this.numberOfAmortizationsInput.sendKeys(numberOfAmortizations);
  }

  async getNumberOfAmortizationsInput() {
    return await this.numberOfAmortizationsInput.getAttribute('value');
  }

  async setFirstAmortizationDateInput(firstAmortizationDate) {
    await this.firstAmortizationDateInput.sendKeys(firstAmortizationDate);
  }

  async getFirstAmortizationDateInput() {
    return await this.firstAmortizationDateInput.getAttribute('value');
  }

  async setMonthlyAmortizationDateInput(monthlyAmortizationDate) {
    await this.monthlyAmortizationDateInput.sendKeys(monthlyAmortizationDate);
  }

  async getMonthlyAmortizationDateInput() {
    return await this.monthlyAmortizationDateInput.getAttribute('value');
  }

  getUploadSuccessfulInput(timeout?: number) {
    return this.uploadSuccessfulInput;
  }
  getUploadOrphanedInput(timeout?: number) {
    return this.uploadOrphanedInput;
  }
  async setOriginatingFileTokenInput(originatingFileToken) {
    await this.originatingFileTokenInput.sendKeys(originatingFileToken);
  }

  async getOriginatingFileTokenInput() {
    return await this.originatingFileTokenInput.getAttribute('value');
  }

  async setAmortizationTagInput(amortizationTag) {
    await this.amortizationTagInput.sendKeys(amortizationTag);
  }

  async getAmortizationTagInput() {
    return await this.amortizationTagInput.getAttribute('value');
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

export class AmortizationUploadDeleteDialog {
  private dialogTitle = element(by.id('gha-delete-amortizationUpload-heading'));
  private confirmButton = element(by.id('gha-confirm-delete-amortizationUpload'));

  async getDialogTitle() {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
