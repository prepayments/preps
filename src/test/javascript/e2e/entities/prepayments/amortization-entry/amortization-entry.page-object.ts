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
  postedInput = element(by.id('field_posted'));
  serviceOutletInput = element(by.id('field_serviceOutlet'));
  accountNumberInput = element(by.id('field_accountNumber'));
  accountNameInput = element(by.id('field_accountName'));
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

  getPostedInput(timeout?: number) {
    return this.postedInput;
  }
  async setServiceOutletInput(serviceOutlet) {
    await this.serviceOutletInput.sendKeys(serviceOutlet);
  }

  async getServiceOutletInput() {
    return await this.serviceOutletInput.getAttribute('value');
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
