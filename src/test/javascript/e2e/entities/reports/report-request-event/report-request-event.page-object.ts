import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class ReportRequestEventComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('gha-report-request-event div table .btn-danger'));
  title = element.all(by.css('gha-report-request-event div h2#page-heading span')).first();

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

export class ReportRequestEventUpdatePage {
  pageTitle = element(by.id('gha-report-request-event-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  reportRequestDateInput = element(by.id('field_reportRequestDate'));
  requestedByInput = element(by.id('field_requestedBy'));
  reportFileInput = element(by.id('file_reportFile'));
  reportTypeSelect = element(by.id('field_reportType'));

  async getPageTitle() {
    return this.pageTitle.getText();
  }

  async setReportRequestDateInput(reportRequestDate) {
    await this.reportRequestDateInput.sendKeys(reportRequestDate);
  }

  async getReportRequestDateInput() {
    return await this.reportRequestDateInput.getAttribute('value');
  }

  async setRequestedByInput(requestedBy) {
    await this.requestedByInput.sendKeys(requestedBy);
  }

  async getRequestedByInput() {
    return await this.requestedByInput.getAttribute('value');
  }

  async setReportFileInput(reportFile) {
    await this.reportFileInput.sendKeys(reportFile);
  }

  async getReportFileInput() {
    return await this.reportFileInput.getAttribute('value');
  }

  async reportTypeSelectLastOption(timeout?: number) {
    await this.reportTypeSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async reportTypeSelectOption(option) {
    await this.reportTypeSelect.sendKeys(option);
  }

  getReportTypeSelect(): ElementFinder {
    return this.reportTypeSelect;
  }

  async getReportTypeSelectedOption() {
    return await this.reportTypeSelect.element(by.css('option:checked')).getText();
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

export class ReportRequestEventDeleteDialog {
  private dialogTitle = element(by.id('gha-delete-reportRequestEvent-heading'));
  private confirmButton = element(by.id('gha-confirm-delete-reportRequestEvent'));

  async getDialogTitle() {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
