/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import {
  AmortizationEntryUpdateComponentsPage,
  AmortizationEntryUpdateDeleteDialog,
  AmortizationEntryUpdateUpdatePage
} from './amortization-entry-update.page-object';

const expect = chai.expect;

describe('AmortizationEntryUpdate e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let amortizationEntryUpdateUpdatePage: AmortizationEntryUpdateUpdatePage;
  let amortizationEntryUpdateComponentsPage: AmortizationEntryUpdateComponentsPage;
  let amortizationEntryUpdateDeleteDialog: AmortizationEntryUpdateDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load AmortizationEntryUpdates', async () => {
    await navBarPage.goToEntity('amortization-entry-update');
    amortizationEntryUpdateComponentsPage = new AmortizationEntryUpdateComponentsPage();
    await browser.wait(ec.visibilityOf(amortizationEntryUpdateComponentsPage.title), 5000);
    expect(await amortizationEntryUpdateComponentsPage.getTitle()).to.eq('Amortization Entry Updates');
  });

  it('should load create AmortizationEntryUpdate page', async () => {
    await amortizationEntryUpdateComponentsPage.clickOnCreateButton();
    amortizationEntryUpdateUpdatePage = new AmortizationEntryUpdateUpdatePage();
    expect(await amortizationEntryUpdateUpdatePage.getPageTitle()).to.eq('Create or edit a Amortization Entry Update');
    await amortizationEntryUpdateUpdatePage.cancel();
  });

  it('should create and save AmortizationEntryUpdates', async () => {
    const nbButtonsBeforeCreate = await amortizationEntryUpdateComponentsPage.countDeleteButtons();

    await amortizationEntryUpdateComponentsPage.clickOnCreateButton();
    await promise.all([
      amortizationEntryUpdateUpdatePage.setAmortizationEntryIdInput('5'),
      amortizationEntryUpdateUpdatePage.setAmortizationDateInput('2000-12-31'),
      amortizationEntryUpdateUpdatePage.setAmortizationAmountInput('5'),
      amortizationEntryUpdateUpdatePage.setParticularsInput('particulars'),
      amortizationEntryUpdateUpdatePage.setPrepaymentServiceOutletInput('prepaymentServiceOutlet'),
      amortizationEntryUpdateUpdatePage.setPrepaymentAccountNumberInput('prepaymentAccountNumber'),
      amortizationEntryUpdateUpdatePage.setAmortizationServiceOutletInput('amortizationServiceOutlet'),
      amortizationEntryUpdateUpdatePage.setAmortizationAccountNumberInput('amortizationAccountNumber'),
      amortizationEntryUpdateUpdatePage.setPrepaymentEntryIdInput('5'),
      amortizationEntryUpdateUpdatePage.setOriginatingFileTokenInput('originatingFileToken'),
      amortizationEntryUpdateUpdatePage.setAmortizationTagInput('amortizationTag')
    ]);
    expect(await amortizationEntryUpdateUpdatePage.getAmortizationEntryIdInput()).to.eq(
      '5',
      'Expected amortizationEntryId value to be equals to 5'
    );
    expect(await amortizationEntryUpdateUpdatePage.getAmortizationDateInput()).to.eq(
      '2000-12-31',
      'Expected amortizationDate value to be equals to 2000-12-31'
    );
    expect(await amortizationEntryUpdateUpdatePage.getAmortizationAmountInput()).to.eq(
      '5',
      'Expected amortizationAmount value to be equals to 5'
    );
    expect(await amortizationEntryUpdateUpdatePage.getParticularsInput()).to.eq(
      'particulars',
      'Expected Particulars value to be equals to particulars'
    );
    expect(await amortizationEntryUpdateUpdatePage.getPrepaymentServiceOutletInput()).to.eq(
      'prepaymentServiceOutlet',
      'Expected PrepaymentServiceOutlet value to be equals to prepaymentServiceOutlet'
    );
    expect(await amortizationEntryUpdateUpdatePage.getPrepaymentAccountNumberInput()).to.eq(
      'prepaymentAccountNumber',
      'Expected PrepaymentAccountNumber value to be equals to prepaymentAccountNumber'
    );
    expect(await amortizationEntryUpdateUpdatePage.getAmortizationServiceOutletInput()).to.eq(
      'amortizationServiceOutlet',
      'Expected AmortizationServiceOutlet value to be equals to amortizationServiceOutlet'
    );
    expect(await amortizationEntryUpdateUpdatePage.getAmortizationAccountNumberInput()).to.eq(
      'amortizationAccountNumber',
      'Expected AmortizationAccountNumber value to be equals to amortizationAccountNumber'
    );
    expect(await amortizationEntryUpdateUpdatePage.getPrepaymentEntryIdInput()).to.eq(
      '5',
      'Expected prepaymentEntryId value to be equals to 5'
    );
    expect(await amortizationEntryUpdateUpdatePage.getOriginatingFileTokenInput()).to.eq(
      'originatingFileToken',
      'Expected OriginatingFileToken value to be equals to originatingFileToken'
    );
    expect(await amortizationEntryUpdateUpdatePage.getAmortizationTagInput()).to.eq(
      'amortizationTag',
      'Expected AmortizationTag value to be equals to amortizationTag'
    );
    const selectedOrphaned = amortizationEntryUpdateUpdatePage.getOrphanedInput();
    if (await selectedOrphaned.isSelected()) {
      await amortizationEntryUpdateUpdatePage.getOrphanedInput().click();
      expect(await amortizationEntryUpdateUpdatePage.getOrphanedInput().isSelected(), 'Expected orphaned not to be selected').to.be.false;
    } else {
      await amortizationEntryUpdateUpdatePage.getOrphanedInput().click();
      expect(await amortizationEntryUpdateUpdatePage.getOrphanedInput().isSelected(), 'Expected orphaned to be selected').to.be.true;
    }
    await amortizationEntryUpdateUpdatePage.save();
    expect(await amortizationEntryUpdateUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await amortizationEntryUpdateComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last AmortizationEntryUpdate', async () => {
    const nbButtonsBeforeDelete = await amortizationEntryUpdateComponentsPage.countDeleteButtons();
    await amortizationEntryUpdateComponentsPage.clickOnLastDeleteButton();

    amortizationEntryUpdateDeleteDialog = new AmortizationEntryUpdateDeleteDialog();
    expect(await amortizationEntryUpdateDeleteDialog.getDialogTitle()).to.eq(
      'Are you sure you want to delete this Amortization Entry Update?'
    );
    await amortizationEntryUpdateDeleteDialog.clickOnConfirmButton();

    expect(await amortizationEntryUpdateComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
