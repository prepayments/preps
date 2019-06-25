/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import {
  AmortizationUploadComponentsPage,
  AmortizationUploadDeleteDialog,
  AmortizationUploadUpdatePage
} from './amortization-upload.page-object';

const expect = chai.expect;

describe('AmortizationUpload e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let amortizationUploadUpdatePage: AmortizationUploadUpdatePage;
  let amortizationUploadComponentsPage: AmortizationUploadComponentsPage;
  let amortizationUploadDeleteDialog: AmortizationUploadDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load AmortizationUploads', async () => {
    await navBarPage.goToEntity('amortization-upload');
    amortizationUploadComponentsPage = new AmortizationUploadComponentsPage();
    await browser.wait(ec.visibilityOf(amortizationUploadComponentsPage.title), 5000);
    expect(await amortizationUploadComponentsPage.getTitle()).to.eq('Amortization Uploads');
  });

  it('should load create AmortizationUpload page', async () => {
    await amortizationUploadComponentsPage.clickOnCreateButton();
    amortizationUploadUpdatePage = new AmortizationUploadUpdatePage();
    expect(await amortizationUploadUpdatePage.getPageTitle()).to.eq('Create or edit a Amortization Upload');
    await amortizationUploadUpdatePage.cancel();
  });

  it('should create and save AmortizationUploads', async () => {
    const nbButtonsBeforeCreate = await amortizationUploadComponentsPage.countDeleteButtons();

    await amortizationUploadComponentsPage.clickOnCreateButton();
    await promise.all([
      amortizationUploadUpdatePage.setAccountNameInput('accountName'),
      amortizationUploadUpdatePage.setParticularsInput('particulars'),
      amortizationUploadUpdatePage.setServiceOutletCodeInput('serviceOutletCode'),
      amortizationUploadUpdatePage.setPrepaymentAccountNumberInput('prepaymentAccountNumber'),
      amortizationUploadUpdatePage.setExpenseAccountNumberInput('expenseAccountNumber'),
      amortizationUploadUpdatePage.setPrepaymentTransactionIdInput('prepaymentTransactionId'),
      amortizationUploadUpdatePage.setPrepaymentTransactionDateInput('2000-12-31'),
      amortizationUploadUpdatePage.setPrepaymentTransactionAmountInput('5'),
      amortizationUploadUpdatePage.setAmortizationAmountInput('5'),
      amortizationUploadUpdatePage.setNumberOfAmortizationsInput('5'),
      amortizationUploadUpdatePage.setFirstAmortizationDateInput('2000-12-31')
    ]);
    expect(await amortizationUploadUpdatePage.getAccountNameInput()).to.eq(
      'accountName',
      'Expected AccountName value to be equals to accountName'
    );
    expect(await amortizationUploadUpdatePage.getParticularsInput()).to.eq(
      'particulars',
      'Expected Particulars value to be equals to particulars'
    );
    expect(await amortizationUploadUpdatePage.getServiceOutletCodeInput()).to.eq(
      'serviceOutletCode',
      'Expected ServiceOutletCode value to be equals to serviceOutletCode'
    );
    expect(await amortizationUploadUpdatePage.getPrepaymentAccountNumberInput()).to.eq(
      'prepaymentAccountNumber',
      'Expected PrepaymentAccountNumber value to be equals to prepaymentAccountNumber'
    );
    expect(await amortizationUploadUpdatePage.getExpenseAccountNumberInput()).to.eq(
      'expenseAccountNumber',
      'Expected ExpenseAccountNumber value to be equals to expenseAccountNumber'
    );
    expect(await amortizationUploadUpdatePage.getPrepaymentTransactionIdInput()).to.eq(
      'prepaymentTransactionId',
      'Expected PrepaymentTransactionId value to be equals to prepaymentTransactionId'
    );
    expect(await amortizationUploadUpdatePage.getPrepaymentTransactionDateInput()).to.eq(
      '2000-12-31',
      'Expected prepaymentTransactionDate value to be equals to 2000-12-31'
    );
    expect(await amortizationUploadUpdatePage.getPrepaymentTransactionAmountInput()).to.eq(
      '5',
      'Expected prepaymentTransactionAmount value to be equals to 5'
    );
    expect(await amortizationUploadUpdatePage.getAmortizationAmountInput()).to.eq(
      '5',
      'Expected amortizationAmount value to be equals to 5'
    );
    expect(await amortizationUploadUpdatePage.getNumberOfAmortizationsInput()).to.eq(
      '5',
      'Expected numberOfAmortizations value to be equals to 5'
    );
    expect(await amortizationUploadUpdatePage.getFirstAmortizationDateInput()).to.eq(
      '2000-12-31',
      'Expected firstAmortizationDate value to be equals to 2000-12-31'
    );
    await amortizationUploadUpdatePage.save();
    expect(await amortizationUploadUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await amortizationUploadComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last AmortizationUpload', async () => {
    const nbButtonsBeforeDelete = await amortizationUploadComponentsPage.countDeleteButtons();
    await amortizationUploadComponentsPage.clickOnLastDeleteButton();

    amortizationUploadDeleteDialog = new AmortizationUploadDeleteDialog();
    expect(await amortizationUploadDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Amortization Upload?');
    await amortizationUploadDeleteDialog.clickOnConfirmButton();

    expect(await amortizationUploadComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
