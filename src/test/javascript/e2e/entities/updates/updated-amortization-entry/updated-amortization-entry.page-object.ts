import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class UpdatedAmortizationEntryComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('gha-updated-amortization-entry div table .btn-danger'));
  title = element.all(by.css('gha-updated-amortization-entry div h2#page-heading span')).first();

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

export class UpdatedAmortizationEntryUpdatePage {
  pageTitle = element(by.id('gha-updated-amortization-entry-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  amortizationDateInput = element(by.id('field_amortizationDate'));
  amortizationAmountInput = element(by.id('field_amortizationAmount'));
  particularsInput = element(by.id('field_particulars'));
  prepaymentServiceOutletInput = element(by.id('field_prepaymentServiceOutlet'));
  prepaymentAccountNumberInput = element(by.id('field_prepaymentAccountNumber'));
  amortizationServiceOutletInput = element(by.id('field_amortizationServiceOutlet'));
  amortizationAccountNumberInput = element(by.id('field_amortizationAccountNumber'));
  originatingFileTokenInput = element(by.id('field_originatingFileToken'));
  amortizationTagInput = element(by.id('field_amortizationTag'));
  orphanedInput = element(by.id('field_orphaned'));
  dateOfUpdateInput = element(by.id('field_dateOfUpdate'));
  reasonForUpdateInput = element(by.id('field_reasonForUpdate'));
  prepaymentEntrySelect = element(by.id('field_prepaymentEntry'));

  async getPageTitle() {
    return this.pageTitle.getText();
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
  async setDateOfUpdateInput(dateOfUpdate) {
    await this.dateOfUpdateInput.sendKeys(dateOfUpdate);
  }

  async getDateOfUpdateInput() {
    return await this.dateOfUpdateInput.getAttribute('value');
  }

  async setReasonForUpdateInput(reasonForUpdate) {
    await this.reasonForUpdateInput.sendKeys(reasonForUpdate);
  }

  async getReasonForUpdateInput() {
    return await this.reasonForUpdateInput.getAttribute('value');
  }

  async prepaymentEntrySelectLastOption(timeout?: number) {
    await this.prepaymentEntrySelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async prepaymentEntrySelectOption(option) {
    await this.prepaymentEntrySelect.sendKeys(option);
  }

  getPrepaymentEntrySelect(): ElementFinder {
    return this.prepaymentEntrySelect;
  }

  async getPrepaymentEntrySelectedOption() {
    return await this.prepaymentEntrySelect.element(by.css('option:checked')).getText();
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

export class UpdatedAmortizationEntryDeleteDialog {
  private dialogTitle = element(by.id('gha-delete-updatedAmortizationEntry-heading'));
  private confirmButton = element(by.id('gha-confirm-delete-updatedAmortizationEntry'));

  async getDialogTitle() {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
