/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import {
  AmortizationDataEntryFileComponentsPage,
  AmortizationDataEntryFileDeleteDialog,
  AmortizationDataEntryFileUpdatePage
} from './amortization-data-entry-file.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('AmortizationDataEntryFile e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let amortizationDataEntryFileUpdatePage: AmortizationDataEntryFileUpdatePage;
  let amortizationDataEntryFileComponentsPage: AmortizationDataEntryFileComponentsPage;
  let amortizationDataEntryFileDeleteDialog: AmortizationDataEntryFileDeleteDialog;
  const fileNameToUpload = 'logo-jhipster.png';
  const fileToUpload = '../../../../../../../src/main/webapp/content/images/' + fileNameToUpload;
  const absolutePath = path.resolve(__dirname, fileToUpload);

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load AmortizationDataEntryFiles', async () => {
    await navBarPage.goToEntity('amortization-data-entry-file');
    amortizationDataEntryFileComponentsPage = new AmortizationDataEntryFileComponentsPage();
    await browser.wait(ec.visibilityOf(amortizationDataEntryFileComponentsPage.title), 5000);
    expect(await amortizationDataEntryFileComponentsPage.getTitle()).to.eq('Amortization Data Entry Files');
  });

  it('should load create AmortizationDataEntryFile page', async () => {
    await amortizationDataEntryFileComponentsPage.clickOnCreateButton();
    amortizationDataEntryFileUpdatePage = new AmortizationDataEntryFileUpdatePage();
    expect(await amortizationDataEntryFileUpdatePage.getPageTitle()).to.eq('Create or edit a Amortization Data Entry File');
    await amortizationDataEntryFileUpdatePage.cancel();
  });

  it('should create and save AmortizationDataEntryFiles', async () => {
    const nbButtonsBeforeCreate = await amortizationDataEntryFileComponentsPage.countDeleteButtons();

    await amortizationDataEntryFileComponentsPage.clickOnCreateButton();
    await promise.all([
      amortizationDataEntryFileUpdatePage.setPeriodFromInput('2000-12-31'),
      amortizationDataEntryFileUpdatePage.setPeriodToInput('2000-12-31'),
      amortizationDataEntryFileUpdatePage.setDataEntryFileInput(absolutePath)
    ]);
    expect(await amortizationDataEntryFileUpdatePage.getPeriodFromInput()).to.eq(
      '2000-12-31',
      'Expected periodFrom value to be equals to 2000-12-31'
    );
    expect(await amortizationDataEntryFileUpdatePage.getPeriodToInput()).to.eq(
      '2000-12-31',
      'Expected periodTo value to be equals to 2000-12-31'
    );
    expect(await amortizationDataEntryFileUpdatePage.getDataEntryFileInput()).to.endsWith(
      fileNameToUpload,
      'Expected DataEntryFile value to be end with ' + fileNameToUpload
    );
    const selectedUploadSuccessful = amortizationDataEntryFileUpdatePage.getUploadSuccessfulInput();
    if (await selectedUploadSuccessful.isSelected()) {
      await amortizationDataEntryFileUpdatePage.getUploadSuccessfulInput().click();
      expect(
        await amortizationDataEntryFileUpdatePage.getUploadSuccessfulInput().isSelected(),
        'Expected uploadSuccessful not to be selected'
      ).to.be.false;
    } else {
      await amortizationDataEntryFileUpdatePage.getUploadSuccessfulInput().click();
      expect(await amortizationDataEntryFileUpdatePage.getUploadSuccessfulInput().isSelected(), 'Expected uploadSuccessful to be selected')
        .to.be.true;
    }
    const selectedUploadProcessed = amortizationDataEntryFileUpdatePage.getUploadProcessedInput();
    if (await selectedUploadProcessed.isSelected()) {
      await amortizationDataEntryFileUpdatePage.getUploadProcessedInput().click();
      expect(
        await amortizationDataEntryFileUpdatePage.getUploadProcessedInput().isSelected(),
        'Expected uploadProcessed not to be selected'
      ).to.be.false;
    } else {
      await amortizationDataEntryFileUpdatePage.getUploadProcessedInput().click();
      expect(await amortizationDataEntryFileUpdatePage.getUploadProcessedInput().isSelected(), 'Expected uploadProcessed to be selected').to
        .be.true;
    }
    await amortizationDataEntryFileUpdatePage.save();
    expect(await amortizationDataEntryFileUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await amortizationDataEntryFileComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last AmortizationDataEntryFile', async () => {
    const nbButtonsBeforeDelete = await amortizationDataEntryFileComponentsPage.countDeleteButtons();
    await amortizationDataEntryFileComponentsPage.clickOnLastDeleteButton();

    amortizationDataEntryFileDeleteDialog = new AmortizationDataEntryFileDeleteDialog();
    expect(await amortizationDataEntryFileDeleteDialog.getDialogTitle()).to.eq(
      'Are you sure you want to delete this Amortization Data Entry File?'
    );
    await amortizationDataEntryFileDeleteDialog.clickOnConfirmButton();

    expect(await amortizationDataEntryFileComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
