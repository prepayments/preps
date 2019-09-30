/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import {
  AmortizationUpdateFileComponentsPage,
  AmortizationUpdateFileDeleteDialog,
  AmortizationUpdateFileUpdatePage
} from './amortization-update-file.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('AmortizationUpdateFile e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let amortizationUpdateFileUpdatePage: AmortizationUpdateFileUpdatePage;
  let amortizationUpdateFileComponentsPage: AmortizationUpdateFileComponentsPage;
  let amortizationUpdateFileDeleteDialog: AmortizationUpdateFileDeleteDialog;
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

  it('should load AmortizationUpdateFiles', async () => {
    await navBarPage.goToEntity('amortization-update-file');
    amortizationUpdateFileComponentsPage = new AmortizationUpdateFileComponentsPage();
    await browser.wait(ec.visibilityOf(amortizationUpdateFileComponentsPage.title), 5000);
    expect(await amortizationUpdateFileComponentsPage.getTitle()).to.eq('Amortization Update Files');
  });

  it('should load create AmortizationUpdateFile page', async () => {
    await amortizationUpdateFileComponentsPage.clickOnCreateButton();
    amortizationUpdateFileUpdatePage = new AmortizationUpdateFileUpdatePage();
    expect(await amortizationUpdateFileUpdatePage.getPageTitle()).to.eq('Create or edit a Amortization Update File');
    await amortizationUpdateFileUpdatePage.cancel();
  });

  it('should create and save AmortizationUpdateFiles', async () => {
    const nbButtonsBeforeCreate = await amortizationUpdateFileComponentsPage.countDeleteButtons();

    await amortizationUpdateFileComponentsPage.clickOnCreateButton();
    await promise.all([
      amortizationUpdateFileUpdatePage.setNarrationInput('narration'),
      amortizationUpdateFileUpdatePage.setDataEntryFileInput(absolutePath),
      amortizationUpdateFileUpdatePage.setEntriesCountInput('5'),
      amortizationUpdateFileUpdatePage.setFileTokenInput('fileToken'),
      amortizationUpdateFileUpdatePage.setReasonForUpdateInput('reasonForUpdate')
    ]);
    expect(await amortizationUpdateFileUpdatePage.getNarrationInput()).to.eq(
      'narration',
      'Expected Narration value to be equals to narration'
    );
    expect(await amortizationUpdateFileUpdatePage.getDataEntryFileInput()).to.endsWith(
      fileNameToUpload,
      'Expected DataEntryFile value to be end with ' + fileNameToUpload
    );
    const selectedUploadSuccessful = amortizationUpdateFileUpdatePage.getUploadSuccessfulInput();
    if (await selectedUploadSuccessful.isSelected()) {
      await amortizationUpdateFileUpdatePage.getUploadSuccessfulInput().click();
      expect(await amortizationUpdateFileUpdatePage.getUploadSuccessfulInput().isSelected(), 'Expected uploadSuccessful not to be selected')
        .to.be.false;
    } else {
      await amortizationUpdateFileUpdatePage.getUploadSuccessfulInput().click();
      expect(await amortizationUpdateFileUpdatePage.getUploadSuccessfulInput().isSelected(), 'Expected uploadSuccessful to be selected').to
        .be.true;
    }
    const selectedUploadProcessed = amortizationUpdateFileUpdatePage.getUploadProcessedInput();
    if (await selectedUploadProcessed.isSelected()) {
      await amortizationUpdateFileUpdatePage.getUploadProcessedInput().click();
      expect(await amortizationUpdateFileUpdatePage.getUploadProcessedInput().isSelected(), 'Expected uploadProcessed not to be selected')
        .to.be.false;
    } else {
      await amortizationUpdateFileUpdatePage.getUploadProcessedInput().click();
      expect(await amortizationUpdateFileUpdatePage.getUploadProcessedInput().isSelected(), 'Expected uploadProcessed to be selected').to.be
        .true;
    }
    expect(await amortizationUpdateFileUpdatePage.getEntriesCountInput()).to.eq('5', 'Expected entriesCount value to be equals to 5');
    expect(await amortizationUpdateFileUpdatePage.getFileTokenInput()).to.eq(
      'fileToken',
      'Expected FileToken value to be equals to fileToken'
    );
    expect(await amortizationUpdateFileUpdatePage.getReasonForUpdateInput()).to.eq(
      'reasonForUpdate',
      'Expected ReasonForUpdate value to be equals to reasonForUpdate'
    );
    await amortizationUpdateFileUpdatePage.save();
    expect(await amortizationUpdateFileUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await amortizationUpdateFileComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last AmortizationUpdateFile', async () => {
    const nbButtonsBeforeDelete = await amortizationUpdateFileComponentsPage.countDeleteButtons();
    await amortizationUpdateFileComponentsPage.clickOnLastDeleteButton();

    amortizationUpdateFileDeleteDialog = new AmortizationUpdateFileDeleteDialog();
    expect(await amortizationUpdateFileDeleteDialog.getDialogTitle()).to.eq(
      'Are you sure you want to delete this Amortization Update File?'
    );
    await amortizationUpdateFileDeleteDialog.clickOnConfirmButton();

    expect(await amortizationUpdateFileComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
