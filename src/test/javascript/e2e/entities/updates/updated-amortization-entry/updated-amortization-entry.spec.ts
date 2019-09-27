/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import {
  UpdatedAmortizationEntryComponentsPage,
  UpdatedAmortizationEntryDeleteDialog,
  UpdatedAmortizationEntryUpdatePage
} from './updated-amortization-entry.page-object';

const expect = chai.expect;

describe('UpdatedAmortizationEntry e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let updatedAmortizationEntryUpdatePage: UpdatedAmortizationEntryUpdatePage;
  let updatedAmortizationEntryComponentsPage: UpdatedAmortizationEntryComponentsPage;
  let updatedAmortizationEntryDeleteDialog: UpdatedAmortizationEntryDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load UpdatedAmortizationEntries', async () => {
    await navBarPage.goToEntity('updated-amortization-entry');
    updatedAmortizationEntryComponentsPage = new UpdatedAmortizationEntryComponentsPage();
    await browser.wait(ec.visibilityOf(updatedAmortizationEntryComponentsPage.title), 5000);
    expect(await updatedAmortizationEntryComponentsPage.getTitle()).to.eq('Updated Amortization Entries');
  });

  it('should load create UpdatedAmortizationEntry page', async () => {
    await updatedAmortizationEntryComponentsPage.clickOnCreateButton();
    updatedAmortizationEntryUpdatePage = new UpdatedAmortizationEntryUpdatePage();
    expect(await updatedAmortizationEntryUpdatePage.getPageTitle()).to.eq('Create or edit a Updated Amortization Entry');
    await updatedAmortizationEntryUpdatePage.cancel();
  });

  it('should create and save UpdatedAmortizationEntries', async () => {
    const nbButtonsBeforeCreate = await updatedAmortizationEntryComponentsPage.countDeleteButtons();

    await updatedAmortizationEntryComponentsPage.clickOnCreateButton();
    await promise.all([
      updatedAmortizationEntryUpdatePage.setAmortizationDateInput('2000-12-31'),
      updatedAmortizationEntryUpdatePage.setAmortizationAmountInput('5'),
      updatedAmortizationEntryUpdatePage.setParticularsInput('particulars'),
      updatedAmortizationEntryUpdatePage.setPrepaymentServiceOutletInput('prepaymentServiceOutlet'),
      updatedAmortizationEntryUpdatePage.setPrepaymentAccountNumberInput('prepaymentAccountNumber'),
      updatedAmortizationEntryUpdatePage.setAmortizationServiceOutletInput('amortizationServiceOutlet'),
      updatedAmortizationEntryUpdatePage.setAmortizationAccountNumberInput('amortizationAccountNumber'),
      updatedAmortizationEntryUpdatePage.setOriginatingFileTokenInput('originatingFileToken'),
      updatedAmortizationEntryUpdatePage.setAmortizationTagInput('amortizationTag'),
      updatedAmortizationEntryUpdatePage.setDateOfUpdateInput('2000-12-31'),
      updatedAmortizationEntryUpdatePage.setReasonForUpdateInput('reasonForUpdate'),
      updatedAmortizationEntryUpdatePage.prepaymentEntrySelectLastOption()
    ]);
    expect(await updatedAmortizationEntryUpdatePage.getAmortizationDateInput()).to.eq(
      '2000-12-31',
      'Expected amortizationDate value to be equals to 2000-12-31'
    );
    expect(await updatedAmortizationEntryUpdatePage.getAmortizationAmountInput()).to.eq(
      '5',
      'Expected amortizationAmount value to be equals to 5'
    );
    expect(await updatedAmortizationEntryUpdatePage.getParticularsInput()).to.eq(
      'particulars',
      'Expected Particulars value to be equals to particulars'
    );
    expect(await updatedAmortizationEntryUpdatePage.getPrepaymentServiceOutletInput()).to.eq(
      'prepaymentServiceOutlet',
      'Expected PrepaymentServiceOutlet value to be equals to prepaymentServiceOutlet'
    );
    expect(await updatedAmortizationEntryUpdatePage.getPrepaymentAccountNumberInput()).to.eq(
      'prepaymentAccountNumber',
      'Expected PrepaymentAccountNumber value to be equals to prepaymentAccountNumber'
    );
    expect(await updatedAmortizationEntryUpdatePage.getAmortizationServiceOutletInput()).to.eq(
      'amortizationServiceOutlet',
      'Expected AmortizationServiceOutlet value to be equals to amortizationServiceOutlet'
    );
    expect(await updatedAmortizationEntryUpdatePage.getAmortizationAccountNumberInput()).to.eq(
      'amortizationAccountNumber',
      'Expected AmortizationAccountNumber value to be equals to amortizationAccountNumber'
    );
    expect(await updatedAmortizationEntryUpdatePage.getOriginatingFileTokenInput()).to.eq(
      'originatingFileToken',
      'Expected OriginatingFileToken value to be equals to originatingFileToken'
    );
    expect(await updatedAmortizationEntryUpdatePage.getAmortizationTagInput()).to.eq(
      'amortizationTag',
      'Expected AmortizationTag value to be equals to amortizationTag'
    );
    const selectedOrphaned = updatedAmortizationEntryUpdatePage.getOrphanedInput();
    if (await selectedOrphaned.isSelected()) {
      await updatedAmortizationEntryUpdatePage.getOrphanedInput().click();
      expect(await updatedAmortizationEntryUpdatePage.getOrphanedInput().isSelected(), 'Expected orphaned not to be selected').to.be.false;
    } else {
      await updatedAmortizationEntryUpdatePage.getOrphanedInput().click();
      expect(await updatedAmortizationEntryUpdatePage.getOrphanedInput().isSelected(), 'Expected orphaned to be selected').to.be.true;
    }
    expect(await updatedAmortizationEntryUpdatePage.getDateOfUpdateInput()).to.eq(
      '2000-12-31',
      'Expected dateOfUpdate value to be equals to 2000-12-31'
    );
    expect(await updatedAmortizationEntryUpdatePage.getReasonForUpdateInput()).to.eq(
      'reasonForUpdate',
      'Expected ReasonForUpdate value to be equals to reasonForUpdate'
    );
    await updatedAmortizationEntryUpdatePage.save();
    expect(await updatedAmortizationEntryUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await updatedAmortizationEntryComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last UpdatedAmortizationEntry', async () => {
    const nbButtonsBeforeDelete = await updatedAmortizationEntryComponentsPage.countDeleteButtons();
    await updatedAmortizationEntryComponentsPage.clickOnLastDeleteButton();

    updatedAmortizationEntryDeleteDialog = new UpdatedAmortizationEntryDeleteDialog();
    expect(await updatedAmortizationEntryDeleteDialog.getDialogTitle()).to.eq(
      'Are you sure you want to delete this Updated Amortization Entry?'
    );
    await updatedAmortizationEntryDeleteDialog.clickOnConfirmButton();

    expect(await updatedAmortizationEntryComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
