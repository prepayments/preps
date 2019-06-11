/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import {
  TransactionAccountComponentsPage,
  TransactionAccountDeleteDialog,
  TransactionAccountUpdatePage
} from './transaction-account.page-object';

const expect = chai.expect;

describe('TransactionAccount e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let transactionAccountUpdatePage: TransactionAccountUpdatePage;
  let transactionAccountComponentsPage: TransactionAccountComponentsPage;
  let transactionAccountDeleteDialog: TransactionAccountDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load TransactionAccounts', async () => {
    await navBarPage.goToEntity('transaction-account');
    transactionAccountComponentsPage = new TransactionAccountComponentsPage();
    await browser.wait(ec.visibilityOf(transactionAccountComponentsPage.title), 5000);
    expect(await transactionAccountComponentsPage.getTitle()).to.eq('Transaction Accounts');
  });

  it('should load create TransactionAccount page', async () => {
    await transactionAccountComponentsPage.clickOnCreateButton();
    transactionAccountUpdatePage = new TransactionAccountUpdatePage();
    expect(await transactionAccountUpdatePage.getPageTitle()).to.eq('Create or edit a Transaction Account');
    await transactionAccountUpdatePage.cancel();
  });

  it('should create and save TransactionAccounts', async () => {
    const nbButtonsBeforeCreate = await transactionAccountComponentsPage.countDeleteButtons();

    await transactionAccountComponentsPage.clickOnCreateButton();
    await promise.all([
      transactionAccountUpdatePage.setAccountNameInput('accountName'),
      transactionAccountUpdatePage.setAccountNumberInput('accountNumber'),
      transactionAccountUpdatePage.setAccountBalanceInput('5'),
      transactionAccountUpdatePage.setOpeningDateInput('2000-12-31'),
      transactionAccountUpdatePage.setAccountOpeningDateBalanceInput('5')
    ]);
    expect(await transactionAccountUpdatePage.getAccountNameInput()).to.eq(
      'accountName',
      'Expected AccountName value to be equals to accountName'
    );
    expect(await transactionAccountUpdatePage.getAccountNumberInput()).to.eq(
      'accountNumber',
      'Expected AccountNumber value to be equals to accountNumber'
    );
    expect(await transactionAccountUpdatePage.getAccountBalanceInput()).to.eq('5', 'Expected accountBalance value to be equals to 5');
    expect(await transactionAccountUpdatePage.getOpeningDateInput()).to.eq(
      '2000-12-31',
      'Expected openingDate value to be equals to 2000-12-31'
    );
    expect(await transactionAccountUpdatePage.getAccountOpeningDateBalanceInput()).to.eq(
      '5',
      'Expected accountOpeningDateBalance value to be equals to 5'
    );
    await transactionAccountUpdatePage.save();
    expect(await transactionAccountUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await transactionAccountComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last TransactionAccount', async () => {
    const nbButtonsBeforeDelete = await transactionAccountComponentsPage.countDeleteButtons();
    await transactionAccountComponentsPage.clickOnLastDeleteButton();

    transactionAccountDeleteDialog = new TransactionAccountDeleteDialog();
    expect(await transactionAccountDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Transaction Account?');
    await transactionAccountDeleteDialog.clickOnConfirmButton();

    expect(await transactionAccountComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
