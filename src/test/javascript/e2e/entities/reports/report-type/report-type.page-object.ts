import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class ReportTypeComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('gha-report-type div table .btn-danger'));
  title = element.all(by.css('gha-report-type div h2#page-heading span')).first();

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

export class ReportTypeUpdatePage {
  pageTitle = element(by.id('gha-report-type-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  reportModelNameInput = element(by.id('field_reportModelName'));
  reportMediumTypeSelect = element(by.id('field_reportMediumType'));
  reportPasswordInput = element(by.id('field_reportPassword'));

  async getPageTitle() {
    return this.pageTitle.getText();
  }

  async setReportModelNameInput(reportModelName) {
    await this.reportModelNameInput.sendKeys(reportModelName);
  }

  async getReportModelNameInput() {
    return await this.reportModelNameInput.getAttribute('value');
  }

  async setReportMediumTypeSelect(reportMediumType) {
    await this.reportMediumTypeSelect.sendKeys(reportMediumType);
  }

  async getReportMediumTypeSelect() {
    return await this.reportMediumTypeSelect.element(by.css('option:checked')).getText();
  }

  async reportMediumTypeSelectLastOption(timeout?: number) {
    await this.reportMediumTypeSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async setReportPasswordInput(reportPassword) {
    await this.reportPasswordInput.sendKeys(reportPassword);
  }

  async getReportPasswordInput() {
    return await this.reportPasswordInput.getAttribute('value');
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

export class ReportTypeDeleteDialog {
  private dialogTitle = element(by.id('gha-delete-reportType-heading'));
  private confirmButton = element(by.id('gha-confirm-delete-reportType'));

  async getDialogTitle() {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
