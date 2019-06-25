/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import {
  AmortizationUploadFileComponentsPage,
  AmortizationUploadFileDeleteDialog,
  AmortizationUploadFileUpdatePage
} from './amortization-upload-file.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('AmortizationUploadFile e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let amortizationUploadFileUpdatePage: AmortizationUploadFileUpdatePage;
  let amortizationUploadFileComponentsPage: AmortizationUploadFileComponentsPage;
  let amortizationUploadFileDeleteDialog: AmortizationUploadFileDeleteDialog;
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

  it('should load AmortizationUploadFiles', async () => {
    await navBarPage.goToEntity('amortization-upload-file');
    amortizationUploadFileComponentsPage = new AmortizationUploadFileComponentsPage();
    await browser.wait(ec.visibilityOf(amortizationUploadFileComponentsPage.title), 5000);
    expect(await amortizationUploadFileComponentsPage.getTitle()).to.eq('Amortization Upload Files');
  });

  it('should load create AmortizationUploadFile page', async () => {
    await amortizationUploadFileComponentsPage.clickOnCreateButton();
    amortizationUploadFileUpdatePage = new AmortizationUploadFileUpdatePage();
    expect(await amortizationUploadFileUpdatePage.getPageTitle()).to.eq('Create or edit a Amortization Upload File');
    await amortizationUploadFileUpdatePage.cancel();
  });

  it('should create and save AmortizationUploadFiles', async () => {
    const nbButtonsBeforeCreate = await amortizationUploadFileComponentsPage.countDeleteButtons();

    await amortizationUploadFileComponentsPage.clickOnCreateButton();
    await promise.all([
      amortizationUploadFileUpdatePage.setPeriodFromInput('2000-12-31'),
      amortizationUploadFileUpdatePage.setPeriodToInput('2000-12-31'),
      amortizationUploadFileUpdatePage.setDataEntryFileInput(absolutePath)
    ]);
    expect(await amortizationUploadFileUpdatePage.getPeriodFromInput()).to.eq(
      '2000-12-31',
      'Expected periodFrom value to be equals to 2000-12-31'
    );
    expect(await amortizationUploadFileUpdatePage.getPeriodToInput()).to.eq(
      '2000-12-31',
      'Expected periodTo value to be equals to 2000-12-31'
    );
    expect(await amortizationUploadFileUpdatePage.getDataEntryFileInput()).to.endsWith(
      fileNameToUpload,
      'Expected DataEntryFile value to be end with ' + fileNameToUpload
    );
    const selectedUploadSuccessful = amortizationUploadFileUpdatePage.getUploadSuccessfulInput();
    if (await selectedUploadSuccessful.isSelected()) {
      await amortizationUploadFileUpdatePage.getUploadSuccessfulInput().click();
      expect(await amortizationUploadFileUpdatePage.getUploadSuccessfulInput().isSelected(), 'Expected uploadSuccessful not to be selected')
        .to.be.false;
    } else {
      await amortizationUploadFileUpdatePage.getUploadSuccessfulInput().click();
      expect(await amortizationUploadFileUpdatePage.getUploadSuccessfulInput().isSelected(), 'Expected uploadSuccessful to be selected').to
        .be.true;
    }
    const selectedUploadProcessed = amortizationUploadFileUpdatePage.getUploadProcessedInput();
    if (await selectedUploadProcessed.isSelected()) {
      await amortizationUploadFileUpdatePage.getUploadProcessedInput().click();
      expect(await amortizationUploadFileUpdatePage.getUploadProcessedInput().isSelected(), 'Expected uploadProcessed not to be selected')
        .to.be.false;
    } else {
      await amortizationUploadFileUpdatePage.getUploadProcessedInput().click();
      expect(await amortizationUploadFileUpdatePage.getUploadProcessedInput().isSelected(), 'Expected uploadProcessed to be selected').to.be
        .true;
    }
    await amortizationUploadFileUpdatePage.save();
    expect(await amortizationUploadFileUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await amortizationUploadFileComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last AmortizationUploadFile', async () => {
    const nbButtonsBeforeDelete = await amortizationUploadFileComponentsPage.countDeleteButtons();
    await amortizationUploadFileComponentsPage.clickOnLastDeleteButton();

    amortizationUploadFileDeleteDialog = new AmortizationUploadFileDeleteDialog();
    expect(await amortizationUploadFileDeleteDialog.getDialogTitle()).to.eq(
      'Are you sure you want to delete this Amortization Upload File?'
    );
    await amortizationUploadFileDeleteDialog.clickOnConfirmButton();

    expect(await amortizationUploadFileComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
