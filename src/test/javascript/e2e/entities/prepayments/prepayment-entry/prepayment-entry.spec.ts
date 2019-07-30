/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import { PrepaymentEntryComponentsPage, PrepaymentEntryDeleteDialog, PrepaymentEntryUpdatePage } from './prepayment-entry.page-object';

const expect = chai.expect;

describe('PrepaymentEntry e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let prepaymentEntryUpdatePage: PrepaymentEntryUpdatePage;
  let prepaymentEntryComponentsPage: PrepaymentEntryComponentsPage;
  let prepaymentEntryDeleteDialog: PrepaymentEntryDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load PrepaymentEntries', async () => {
    await navBarPage.goToEntity('prepayment-entry');
    prepaymentEntryComponentsPage = new PrepaymentEntryComponentsPage();
    await browser.wait(ec.visibilityOf(prepaymentEntryComponentsPage.title), 5000);
    expect(await prepaymentEntryComponentsPage.getTitle()).to.eq('Prepayment Entries');
  });

  it('should load create PrepaymentEntry page', async () => {
    await prepaymentEntryComponentsPage.clickOnCreateButton();
    prepaymentEntryUpdatePage = new PrepaymentEntryUpdatePage();
    expect(await prepaymentEntryUpdatePage.getPageTitle()).to.eq('Create or edit a Prepayment Entry');
    await prepaymentEntryUpdatePage.cancel();
  });

  it('should create and save PrepaymentEntries', async () => {
    const nbButtonsBeforeCreate = await prepaymentEntryComponentsPage.countDeleteButtons();

    await prepaymentEntryComponentsPage.clickOnCreateButton();
    await promise.all([
      prepaymentEntryUpdatePage.setAccountNumberInput('accountNumber'),
      prepaymentEntryUpdatePage.setAccountNameInput('accountName'),
      prepaymentEntryUpdatePage.setPrepaymentIdInput('prepaymentId'),
      prepaymentEntryUpdatePage.setPrepaymentDateInput('2000-12-31'),
      prepaymentEntryUpdatePage.setParticularsInput('particulars'),
      prepaymentEntryUpdatePage.setServiceOutletInput('serviceOutlet'),
      prepaymentEntryUpdatePage.setPrepaymentAmountInput('5'),
      prepaymentEntryUpdatePage.setMonthsInput('5'),
      prepaymentEntryUpdatePage.setSupplierNameInput('supplierName'),
      prepaymentEntryUpdatePage.setInvoiceNumberInput('invoiceNumber'),
      prepaymentEntryUpdatePage.setScannedDocumentIdInput('5'),
      prepaymentEntryUpdatePage.setOriginatingFileTokenInput('OriginatingFileToken')
    ]);
    expect(await prepaymentEntryUpdatePage.getAccountNumberInput()).to.eq(
      'accountNumber',
      'Expected AccountNumber value to be equals to accountNumber'
    );
    expect(await prepaymentEntryUpdatePage.getAccountNameInput()).to.eq(
      'accountName',
      'Expected AccountName value to be equals to accountName'
    );
    expect(await prepaymentEntryUpdatePage.getPrepaymentIdInput()).to.eq(
      'prepaymentId',
      'Expected PrepaymentId value to be equals to prepaymentId'
    );
    expect(await prepaymentEntryUpdatePage.getPrepaymentDateInput()).to.eq(
      '2000-12-31',
      'Expected prepaymentDate value to be equals to 2000-12-31'
    );
    expect(await prepaymentEntryUpdatePage.getParticularsInput()).to.eq(
      'particulars',
      'Expected Particulars value to be equals to particulars'
    );
    expect(await prepaymentEntryUpdatePage.getServiceOutletInput()).to.eq(
      'serviceOutlet',
      'Expected ServiceOutlet value to be equals to serviceOutlet'
    );
    expect(await prepaymentEntryUpdatePage.getPrepaymentAmountInput()).to.eq('5', 'Expected prepaymentAmount value to be equals to 5');
    expect(await prepaymentEntryUpdatePage.getMonthsInput()).to.eq('5', 'Expected months value to be equals to 5');
    expect(await prepaymentEntryUpdatePage.getSupplierNameInput()).to.eq(
      'supplierName',
      'Expected SupplierName value to be equals to supplierName'
    );
    expect(await prepaymentEntryUpdatePage.getInvoiceNumberInput()).to.eq(
      'invoiceNumber',
      'Expected InvoiceNumber value to be equals to invoiceNumber'
    );
    expect(await prepaymentEntryUpdatePage.getScannedDocumentIdInput()).to.eq('5', 'Expected scannedDocumentId value to be equals to 5');
    expect(await prepaymentEntryUpdatePage.getOriginatingFileTokenInput()).to.eq(
      'OriginatingFileToken',
      'Expected OriginatingFileToken value to be equals to OriginatingFileToken'
    );
    await prepaymentEntryUpdatePage.save();
    expect(await prepaymentEntryUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await prepaymentEntryComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last PrepaymentEntry', async () => {
    const nbButtonsBeforeDelete = await prepaymentEntryComponentsPage.countDeleteButtons();
    await prepaymentEntryComponentsPage.clickOnLastDeleteButton();

    prepaymentEntryDeleteDialog = new PrepaymentEntryDeleteDialog();
    expect(await prepaymentEntryDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Prepayment Entry?');
    await prepaymentEntryDeleteDialog.clickOnConfirmButton();

    expect(await prepaymentEntryComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
