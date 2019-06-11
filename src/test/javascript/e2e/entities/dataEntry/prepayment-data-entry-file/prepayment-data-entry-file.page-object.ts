import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class PrepaymentDataEntryFileComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('gha-prepayment-data-entry-file div table .btn-danger'));
  title = element.all(by.css('gha-prepayment-data-entry-file div h2#page-heading span')).first();

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

export class PrepaymentDataEntryFileUpdatePage {
  pageTitle = element(by.id('gha-prepayment-data-entry-file-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  periodFromInput = element(by.id('field_periodFrom'));
  periodToInput = element(by.id('field_periodTo'));
  dataEntryFileInput = element(by.id('file_dataEntryFile'));
  uploadProcessedInput = element(by.id('field_uploadProcessed'));
  uploadSuccessfulInput = element(by.id('field_uploadSuccessful'));

  async getPageTitle() {
    return this.pageTitle.getText();
  }

  async setPeriodFromInput(periodFrom) {
    await this.periodFromInput.sendKeys(periodFrom);
  }

  async getPeriodFromInput() {
    return await this.periodFromInput.getAttribute('value');
  }

  async setPeriodToInput(periodTo) {
    await this.periodToInput.sendKeys(periodTo);
  }

  async getPeriodToInput() {
    return await this.periodToInput.getAttribute('value');
  }

  async setDataEntryFileInput(dataEntryFile) {
    await this.dataEntryFileInput.sendKeys(dataEntryFile);
  }

  async getDataEntryFileInput() {
    return await this.dataEntryFileInput.getAttribute('value');
  }

  getUploadProcessedInput(timeout?: number) {
    return this.uploadProcessedInput;
  }
  getUploadSuccessfulInput(timeout?: number) {
    return this.uploadSuccessfulInput;
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

export class PrepaymentDataEntryFileDeleteDialog {
  private dialogTitle = element(by.id('gha-delete-prepaymentDataEntryFile-heading'));
  private confirmButton = element(by.id('gha-confirm-delete-prepaymentDataEntryFile'));

  async getDialogTitle() {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
