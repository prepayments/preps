import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class AmortizationEntryComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('gha-amortization-entry div table .btn-danger'));
  title = element.all(by.css('gha-amortization-entry div h2#page-heading span')).first();

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

export class AmortizationEntryUpdatePage {
  pageTitle = element(by.id('gha-amortization-entry-heading'));
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
  orphanedInput = element(by.id('field_orphaned'));
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

  getOrphanedInput(timeout?: number) {
    return this.orphanedInput;
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

export class AmortizationEntryDeleteDialog {
  private dialogTitle = element(by.id('gha-delete-amortizationEntry-heading'));
  private confirmButton = element(by.id('gha-confirm-delete-amortizationEntry'));

  async getDialogTitle() {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
