/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import {
  AccountingTransactionComponentsPage,
  AccountingTransactionDeleteDialog,
  AccountingTransactionUpdatePage
} from './accounting-transaction.page-object';

const expect = chai.expect;

describe('AccountingTransaction e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let accountingTransactionUpdatePage: AccountingTransactionUpdatePage;
  let accountingTransactionComponentsPage: AccountingTransactionComponentsPage;
  let accountingTransactionDeleteDialog: AccountingTransactionDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load AccountingTransactions', async () => {
    await navBarPage.goToEntity('accounting-transaction');
    accountingTransactionComponentsPage = new AccountingTransactionComponentsPage();
    await browser.wait(ec.visibilityOf(accountingTransactionComponentsPage.title), 5000);
    expect(await accountingTransactionComponentsPage.getTitle()).to.eq('Accounting Transactions');
  });

  it('should load create AccountingTransaction page', async () => {
    await accountingTransactionComponentsPage.clickOnCreateButton();
    accountingTransactionUpdatePage = new AccountingTransactionUpdatePage();
    expect(await accountingTransactionUpdatePage.getPageTitle()).to.eq('Create or edit a Accounting Transaction');
    await accountingTransactionUpdatePage.cancel();
  });

  it('should create and save AccountingTransactions', async () => {
    const nbButtonsBeforeCreate = await accountingTransactionComponentsPage.countDeleteButtons();

    await accountingTransactionComponentsPage.clickOnCreateButton();
    await promise.all([
      accountingTransactionUpdatePage.setDescriptionInput('description'),
      accountingTransactionUpdatePage.setServiceOutletCodeInput('serviceOutletCode'),
      accountingTransactionUpdatePage.setAccountNameInput('accountName'),
      accountingTransactionUpdatePage.setAccountNumberInput('accountNumber'),
      accountingTransactionUpdatePage.setTransactionDateInput('2000-12-31'),
      accountingTransactionUpdatePage.setTransactionAmountInput('5')
    ]);
    expect(await accountingTransactionUpdatePage.getDescriptionInput()).to.eq(
      'description',
      'Expected Description value to be equals to description'
    );
    expect(await accountingTransactionUpdatePage.getServiceOutletCodeInput()).to.eq(
      'serviceOutletCode',
      'Expected ServiceOutletCode value to be equals to serviceOutletCode'
    );
    expect(await accountingTransactionUpdatePage.getAccountNameInput()).to.eq(
      'accountName',
      'Expected AccountName value to be equals to accountName'
    );
    expect(await accountingTransactionUpdatePage.getAccountNumberInput()).to.eq(
      'accountNumber',
      'Expected AccountNumber value to be equals to accountNumber'
    );
    expect(await accountingTransactionUpdatePage.getTransactionDateInput()).to.eq(
      '2000-12-31',
      'Expected transactionDate value to be equals to 2000-12-31'
    );
    expect(await accountingTransactionUpdatePage.getTransactionAmountInput()).to.eq(
      '5',
      'Expected transactionAmount value to be equals to 5'
    );
    const selectedIncrementAccount = accountingTransactionUpdatePage.getIncrementAccountInput();
    if (await selectedIncrementAccount.isSelected()) {
      await accountingTransactionUpdatePage.getIncrementAccountInput().click();
      expect(await accountingTransactionUpdatePage.getIncrementAccountInput().isSelected(), 'Expected incrementAccount not to be selected')
        .to.be.false;
    } else {
      await accountingTransactionUpdatePage.getIncrementAccountInput().click();
      expect(await accountingTransactionUpdatePage.getIncrementAccountInput().isSelected(), 'Expected incrementAccount to be selected').to
        .be.true;
    }
    await accountingTransactionUpdatePage.save();
    expect(await accountingTransactionUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await accountingTransactionComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last AccountingTransaction', async () => {
    const nbButtonsBeforeDelete = await accountingTransactionComponentsPage.countDeleteButtons();
    await accountingTransactionComponentsPage.clickOnLastDeleteButton();

    accountingTransactionDeleteDialog = new AccountingTransactionDeleteDialog();
    expect(await accountingTransactionDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Accounting Transaction?');
    await accountingTransactionDeleteDialog.clickOnConfirmButton();

    expect(await accountingTransactionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
