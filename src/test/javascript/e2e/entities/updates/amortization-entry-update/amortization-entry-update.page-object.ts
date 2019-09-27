import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class AmortizationEntryUpdateComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('gha-amortization-entry-update div table .btn-danger'));
  title = element.all(by.css('gha-amortization-entry-update div h2#page-heading span')).first();

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

export class AmortizationEntryUpdateUpdatePage {
  pageTitle = element(by.id('gha-amortization-entry-update-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  amortizationEntryIdInput = element(by.id('field_amortizationEntryId'));
  amortizationDateInput = element(by.id('field_amortizationDate'));
  amortizationAmountInput = element(by.id('field_amortizationAmount'));
  particularsInput = element(by.id('field_particulars'));
  prepaymentServiceOutletInput = element(by.id('field_prepaymentServiceOutlet'));
  prepaymentAccountNumberInput = element(by.id('field_prepaymentAccountNumber'));
  amortizationServiceOutletInput = element(by.id('field_amortizationServiceOutlet'));
  amortizationAccountNumberInput = element(by.id('field_amortizationAccountNumber'));
  prepaymentEntryIdInput = element(by.id('field_prepaymentEntryId'));
  originatingFileTokenInput = element(by.id('field_originatingFileToken'));
  amortizationTagInput = element(by.id('field_amortizationTag'));
  orphanedInput = element(by.id('field_orphaned'));

  async getPageTitle() {
    return this.pageTitle.getText();
  }

  async setAmortizationEntryIdInput(amortizationEntryId) {
    await this.amortizationEntryIdInput.sendKeys(amortizationEntryId);
  }

  async getAmortizationEntryIdInput() {
    return await this.amortizationEntryIdInput.getAttribute('value');
  }

  async setAmortizationDateInput(amortizationDate) {
    await this.amortizationDateInput.sendKeys(amortizationDate);
  }

  async getAmortizationDateInput() {
    return await this.amortizationDateInput.getAttribute('value');
  }

  async setAmortizationAmountInput(amortizationAmount) {
    await this.amortizationAmountInput.sendKeys(amortizationAmount);
  }

  async getAmortizationAmountInput() {
    return await this.amortizationAmountInput.getAttribute('value');
  }

  async setParticularsInput(particulars) {
    await this.particularsInput.sendKeys(particulars);
  }

  async getParticularsInput() {
    return await this.particularsInput.getAttribute('value');
  }

  async setPrepaymentServiceOutletInput(prepaymentServiceOutlet) {
    await this.prepaymentServiceOutletInput.sendKeys(prepaymentServiceOutlet);
  }

  async getPrepaymentServiceOutletInput() {
    return await this.prepaymentServiceOutletInput.getAttribute('value');
  }

  async setPrepaymentAccountNumberInput(prepaymentAccountNumber) {
    await this.prepaymentAccountNumberInput.sendKeys(prepaymentAccountNumber);
  }

  async getPrepaymentAccountNumberInput() {
    return await this.prepaymentAccountNumberInput.getAttribute('value');
  }

  async setAmortizationServiceOutletInput(amortizationServiceOutlet) {
    await this.amortizationServiceOutletInput.sendKeys(amortizationServiceOutlet);
  }

  async getAmortizationServiceOutletInput() {
    return await this.amortizationServiceOutletInput.getAttribute('value');
  }

  async setAmortizationAccountNumberInput(amortizationAccountNumber) {
    await this.amortizationAccountNumberInput.sendKeys(amortizationAccountNumber);
  }

  async getAmortizationAccountNumberInput() {
    return await this.amortizationAccountNumberInput.getAttribute('value');
  }

  async setPrepaymentEntryIdInput(prepaymentEntryId) {
    await this.prepaymentEntryIdInput.sendKeys(prepaymentEntryId);
  }

  async getPrepaymentEntryIdInput() {
    return await this.prepaymentEntryIdInput.getAttribute('value');
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

  getOrphanedInput(timeout?: number) {
    return this.orphanedInput;
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

export class AmortizationEntryUpdateDeleteDialog {
  private dialogTitle = element(by.id('gha-delete-amortizationEntryUpdate-heading'));
  private confirmButton = element(by.id('gha-confirm-delete-amortizationEntryUpdate'));

  async getDialogTitle() {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
