/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ScannedDocumentComponentsPage, ScannedDocumentDeleteDialog, ScannedDocumentUpdatePage } from './scanned-document.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('ScannedDocument e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let scannedDocumentUpdatePage: ScannedDocumentUpdatePage;
  let scannedDocumentComponentsPage: ScannedDocumentComponentsPage;
  let scannedDocumentDeleteDialog: ScannedDocumentDeleteDialog;
  const fileNameToUpload = 'logo-jhipster.png';
  const fileToUpload = '../../../../../../src/main/webapp/content/images/' + fileNameToUpload;
  const absolutePath = path.resolve(__dirname, fileToUpload);

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ScannedDocuments', async () => {
    await navBarPage.goToEntity('scanned-document');
    scannedDocumentComponentsPage = new ScannedDocumentComponentsPage();
    await browser.wait(ec.visibilityOf(scannedDocumentComponentsPage.title), 5000);
    expect(await scannedDocumentComponentsPage.getTitle()).to.eq('Scanned Documents');
  });

  it('should load create ScannedDocument page', async () => {
    await scannedDocumentComponentsPage.clickOnCreateButton();
    scannedDocumentUpdatePage = new ScannedDocumentUpdatePage();
    expect(await scannedDocumentUpdatePage.getPageTitle()).to.eq('Create or edit a Scanned Document');
    await scannedDocumentUpdatePage.cancel();
  });

  it('should create and save ScannedDocuments', async () => {
    const nbButtonsBeforeCreate = await scannedDocumentComponentsPage.countDeleteButtons();

    await scannedDocumentComponentsPage.clickOnCreateButton();
    await promise.all([
      scannedDocumentUpdatePage.setDocumentNameInput('documentName'),
      scannedDocumentUpdatePage.setDescriptionInput('description'),
      scannedDocumentUpdatePage.setInvoiceNumberInput('invoiceNumber'),
      scannedDocumentUpdatePage.setTransactionIdInput('transactionId'),
      scannedDocumentUpdatePage.setTransactionDateInput('2000-12-31'),
      scannedDocumentUpdatePage.setInvoiceDocumentInput(absolutePath),
      scannedDocumentUpdatePage.setRequisitionDocumentInput(absolutePath),
      scannedDocumentUpdatePage.setApprovalMemoDocumentInput(absolutePath),
      scannedDocumentUpdatePage.setOtherScannedDocumentInput(absolutePath)
    ]);
    expect(await scannedDocumentUpdatePage.getDocumentNameInput()).to.eq(
      'documentName',
      'Expected DocumentName value to be equals to documentName'
    );
    expect(await scannedDocumentUpdatePage.getDescriptionInput()).to.eq(
      'description',
      'Expected Description value to be equals to description'
    );
    expect(await scannedDocumentUpdatePage.getInvoiceNumberInput()).to.eq(
      'invoiceNumber',
      'Expected InvoiceNumber value to be equals to invoiceNumber'
    );
    expect(await scannedDocumentUpdatePage.getTransactionIdInput()).to.eq(
      'transactionId',
      'Expected TransactionId value to be equals to transactionId'
    );
    expect(await scannedDocumentUpdatePage.getTransactionDateInput()).to.eq(
      '2000-12-31',
      'Expected transactionDate value to be equals to 2000-12-31'
    );
    expect(await scannedDocumentUpdatePage.getInvoiceDocumentInput()).to.endsWith(
      fileNameToUpload,
      'Expected InvoiceDocument value to be end with ' + fileNameToUpload
    );
    expect(await scannedDocumentUpdatePage.getRequisitionDocumentInput()).to.endsWith(
      fileNameToUpload,
      'Expected RequisitionDocument value to be end with ' + fileNameToUpload
    );
    expect(await scannedDocumentUpdatePage.getApprovalMemoDocumentInput()).to.endsWith(
      fileNameToUpload,
      'Expected ApprovalMemoDocument value to be end with ' + fileNameToUpload
    );
    expect(await scannedDocumentUpdatePage.getOtherScannedDocumentInput()).to.endsWith(
      fileNameToUpload,
      'Expected OtherScannedDocument value to be end with ' + fileNameToUpload
    );
    await scannedDocumentUpdatePage.save();
    expect(await scannedDocumentUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await scannedDocumentComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last ScannedDocument', async () => {
    const nbButtonsBeforeDelete = await scannedDocumentComponentsPage.countDeleteButtons();
    await scannedDocumentComponentsPage.clickOnLastDeleteButton();

    scannedDocumentDeleteDialog = new ScannedDocumentDeleteDialog();
    expect(await scannedDocumentDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Scanned Document?');
    await scannedDocumentDeleteDialog.clickOnConfirmButton();

    expect(await scannedDocumentComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
