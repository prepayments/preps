/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import {
  AmortizationEntryComponentsPage,
  AmortizationEntryDeleteDialog,
  AmortizationEntryUpdatePage
} from './amortization-entry.page-object';

const expect = chai.expect;

describe('AmortizationEntry e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let amortizationEntryUpdatePage: AmortizationEntryUpdatePage;
  let amortizationEntryComponentsPage: AmortizationEntryComponentsPage;
  /*let amortizationEntryDeleteDialog: AmortizationEntryDeleteDialog;*/

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load AmortizationEntries', async () => {
    await navBarPage.goToEntity('amortization-entry');
    amortizationEntryComponentsPage = new AmortizationEntryComponentsPage();
    await browser.wait(ec.visibilityOf(amortizationEntryComponentsPage.title), 5000);
    expect(await amortizationEntryComponentsPage.getTitle()).to.eq('Amortization Entries');
  });

  it('should load create AmortizationEntry page', async () => {
    await amortizationEntryComponentsPage.clickOnCreateButton();
    amortizationEntryUpdatePage = new AmortizationEntryUpdatePage();
    expect(await amortizationEntryUpdatePage.getPageTitle()).to.eq('Create or edit a Amortization Entry');
    await amortizationEntryUpdatePage.cancel();
  });

  /* it('should create and save AmortizationEntries', async () => {
        const nbButtonsBeforeCreate = await amortizationEntryComponentsPage.countDeleteButtons();

        await amortizationEntryComponentsPage.clickOnCreateButton();
        await promise.all([
            amortizationEntryUpdatePage.setAmortizationDateInput('2000-12-31'),
            amortizationEntryUpdatePage.setAmortizationAmountInput('5'),
            amortizationEntryUpdatePage.setParticularsInput('particulars'),
            amortizationEntryUpdatePage.setServiceOutletInput('serviceOutlet'),
            amortizationEntryUpdatePage.setAccountNumberInput('accountNumber'),
            amortizationEntryUpdatePage.setAccountNameInput('accountName'),
            amortizationEntryUpdatePage.prepaymentEntrySelectLastOption(),
        ]);
        expect(await amortizationEntryUpdatePage.getAmortizationDateInput()).to.eq('2000-12-31', 'Expected amortizationDate value to be equals to 2000-12-31');
        expect(await amortizationEntryUpdatePage.getAmortizationAmountInput()).to.eq('5', 'Expected amortizationAmount value to be equals to 5');
        expect(await amortizationEntryUpdatePage.getParticularsInput()).to.eq('particulars', 'Expected Particulars value to be equals to particulars');
        const selectedPosted = amortizationEntryUpdatePage.getPostedInput();
        if (await selectedPosted.isSelected()) {
            await amortizationEntryUpdatePage.getPostedInput().click();
            expect(await amortizationEntryUpdatePage.getPostedInput().isSelected(), 'Expected posted not to be selected').to.be.false;
        } else {
            await amortizationEntryUpdatePage.getPostedInput().click();
            expect(await amortizationEntryUpdatePage.getPostedInput().isSelected(), 'Expected posted to be selected').to.be.true;
        }
        expect(await amortizationEntryUpdatePage.getServiceOutletInput()).to.eq('serviceOutlet', 'Expected ServiceOutlet value to be equals to serviceOutlet');
        expect(await amortizationEntryUpdatePage.getAccountNumberInput()).to.eq('accountNumber', 'Expected AccountNumber value to be equals to accountNumber');
        expect(await amortizationEntryUpdatePage.getAccountNameInput()).to.eq('accountName', 'Expected AccountName value to be equals to accountName');
        await amortizationEntryUpdatePage.save();
        expect(await amortizationEntryUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await amortizationEntryComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    });*/

  /* it('should delete last AmortizationEntry', async () => {
        const nbButtonsBeforeDelete = await amortizationEntryComponentsPage.countDeleteButtons();
        await amortizationEntryComponentsPage.clickOnLastDeleteButton();

        amortizationEntryDeleteDialog = new AmortizationEntryDeleteDialog();
        expect(await amortizationEntryDeleteDialog.getDialogTitle())
            .to.eq('Are you sure you want to delete this Amortization Entry?');
        await amortizationEntryDeleteDialog.clickOnConfirmButton();

        expect(await amortizationEntryComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });*/

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
