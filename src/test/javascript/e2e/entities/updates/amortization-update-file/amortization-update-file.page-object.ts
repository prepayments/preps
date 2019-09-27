import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class AmortizationUpdateFileComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('gha-amortization-update-file div table .btn-danger'));
  title = element.all(by.css('gha-amortization-update-file div h2#page-heading span')).first();

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

export class AmortizationUpdateFileUpdatePage {
  pageTitle = element(by.id('gha-amortization-update-file-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  NarrationInput = element(by.id('field_Narration'));
  dataEntryFileInput = element(by.id('file_dataEntryFile'));
  uploadSuccessfulInput = element(by.id('field_uploadSuccessful'));
  uploadProcessedInput = element(by.id('field_uploadProcessed'));
  entriesCountInput = element(by.id('field_entriesCount'));
  fileTokenInput = element(by.id('field_fileToken'));
  reasonForUpdateInput = element(by.id('field_reasonForUpdate'));

  async getPageTitle() {
    return this.pageTitle.getText();
  }

  async setNarrationInput(Narration) {
    await this.NarrationInput.sendKeys(Narration);
  }

  async getNarrationInput() {
    return await this.NarrationInput.getAttribute('value');
  }

  async setDataEntryFileInput(dataEntryFile) {
    await this.dataEntryFileInput.sendKeys(dataEntryFile);
  }

  async getDataEntryFileInput() {
    return await this.dataEntryFileInput.getAttribute('value');
  }

  getUploadSuccessfulInput(timeout?: number) {
    return this.uploadSuccessfulInput;
  }
  getUploadProcessedInput(timeout?: number) {
    return this.uploadProcessedInput;
  }
  async setEntriesCountInput(entriesCount) {
    await this.entriesCountInput.sendKeys(entriesCount);
  }

  async getEntriesCountInput() {
    return await this.entriesCountInput.getAttribute('value');
  }

  async setFileTokenInput(fileToken) {
    await this.fileTokenInput.sendKeys(fileToken);
  }

  async getFileTokenInput() {
    return await this.fileTokenInput.getAttribute('value');
  }

  async setReasonForUpdateInput(reasonForUpdate) {
    await this.reasonForUpdateInput.sendKeys(reasonForUpdate);
  }

  async getReasonForUpdateInput() {
    return await this.reasonForUpdateInput.getAttribute('value');
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

export class AmortizationUpdateFileDeleteDialog {
  private dialogTitle = element(by.id('gha-delete-amortizationUpdateFile-heading'));
  private confirmButton = element(by.id('gha-confirm-delete-amortizationUpdateFile'));

  async getDialogTitle() {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
