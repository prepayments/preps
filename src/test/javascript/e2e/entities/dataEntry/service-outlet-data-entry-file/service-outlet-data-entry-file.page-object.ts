import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class ServiceOutletDataEntryFileComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('gha-service-outlet-data-entry-file div table .btn-danger'));
  title = element.all(by.css('gha-service-outlet-data-entry-file div h2#page-heading span')).first();

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

export class ServiceOutletDataEntryFileUpdatePage {
  pageTitle = element(by.id('gha-service-outlet-data-entry-file-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  periodFromInput = element(by.id('field_periodFrom'));
  periodToInput = element(by.id('field_periodTo'));
  uploadSuccessfulInput = element(by.id('field_uploadSuccessful'));
  uploadProcessedInput = element(by.id('field_uploadProcessed'));
  dataEntryFileInput = element(by.id('file_dataEntryFile'));

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

  getUploadSuccessfulInput(timeout?: number) {
    return this.uploadSuccessfulInput;
  }
  getUploadProcessedInput(timeout?: number) {
    return this.uploadProcessedInput;
  }
  async setDataEntryFileInput(dataEntryFile) {
    await this.dataEntryFileInput.sendKeys(dataEntryFile);
  }

  async getDataEntryFileInput() {
    return await this.dataEntryFileInput.getAttribute('value');
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

export class ServiceOutletDataEntryFileDeleteDialog {
  private dialogTitle = element(by.id('gha-delete-serviceOutletDataEntryFile-heading'));
  private confirmButton = element(by.id('gha-confirm-delete-serviceOutletDataEntryFile'));

  async getDialogTitle() {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
