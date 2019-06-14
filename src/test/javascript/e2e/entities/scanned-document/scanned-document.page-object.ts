import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class ScannedDocumentComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('gha-scanned-document div table .btn-danger'));
  title = element.all(by.css('gha-scanned-document div h2#page-heading span')).first();

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

export class ScannedDocumentUpdatePage {
  pageTitle = element(by.id('gha-scanned-document-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  documentNameInput = element(by.id('field_documentName'));
  descriptionInput = element(by.id('field_description'));
  invoiceNumberInput = element(by.id('field_invoiceNumber'));
  transactionIdInput = element(by.id('field_transactionId'));
  transactionDateInput = element(by.id('field_transactionDate'));
  invoiceDocumentInput = element(by.id('file_invoiceDocument'));
  requisitionDocumentInput = element(by.id('file_requisitionDocument'));
  approvalMemoDocumentInput = element(by.id('file_approvalMemoDocument'));
  otherScannedDocumentInput = element(by.id('file_otherScannedDocument'));

  async getPageTitle() {
    return this.pageTitle.getText();
  }

  async setDocumentNameInput(documentName) {
    await this.documentNameInput.sendKeys(documentName);
  }

  async getDocumentNameInput() {
    return await this.documentNameInput.getAttribute('value');
  }

  async setDescriptionInput(description) {
    await this.descriptionInput.sendKeys(description);
  }

  async getDescriptionInput() {
    return await this.descriptionInput.getAttribute('value');
  }

  async setInvoiceNumberInput(invoiceNumber) {
    await this.invoiceNumberInput.sendKeys(invoiceNumber);
  }

  async getInvoiceNumberInput() {
    return await this.invoiceNumberInput.getAttribute('value');
  }

  async setTransactionIdInput(transactionId) {
    await this.transactionIdInput.sendKeys(transactionId);
  }

  async getTransactionIdInput() {
    return await this.transactionIdInput.getAttribute('value');
  }

  async setTransactionDateInput(transactionDate) {
    await this.transactionDateInput.sendKeys(transactionDate);
  }

  async getTransactionDateInput() {
    return await this.transactionDateInput.getAttribute('value');
  }

  async setInvoiceDocumentInput(invoiceDocument) {
    await this.invoiceDocumentInput.sendKeys(invoiceDocument);
  }

  async getInvoiceDocumentInput() {
    return await this.invoiceDocumentInput.getAttribute('value');
  }

  async setRequisitionDocumentInput(requisitionDocument) {
    await this.requisitionDocumentInput.sendKeys(requisitionDocument);
  }

  async getRequisitionDocumentInput() {
    return await this.requisitionDocumentInput.getAttribute('value');
  }

  async setApprovalMemoDocumentInput(approvalMemoDocument) {
    await this.approvalMemoDocumentInput.sendKeys(approvalMemoDocument);
  }

  async getApprovalMemoDocumentInput() {
    return await this.approvalMemoDocumentInput.getAttribute('value');
  }

  async setOtherScannedDocumentInput(otherScannedDocument) {
    await this.otherScannedDocumentInput.sendKeys(otherScannedDocument);
  }

  async getOtherScannedDocumentInput() {
    return await this.otherScannedDocumentInput.getAttribute('value');
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

export class ScannedDocumentDeleteDialog {
  private dialogTitle = element(by.id('gha-delete-scannedDocument-heading'));
  private confirmButton = element(by.id('gha-confirm-delete-scannedDocument'));

  async getDialogTitle() {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
