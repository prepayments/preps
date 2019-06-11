/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import {
  PrepaymentDataEntryFileComponentsPage,
  PrepaymentDataEntryFileDeleteDialog,
  PrepaymentDataEntryFileUpdatePage
} from './prepayment-data-entry-file.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('PrepaymentDataEntryFile e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let prepaymentDataEntryFileUpdatePage: PrepaymentDataEntryFileUpdatePage;
  let prepaymentDataEntryFileComponentsPage: PrepaymentDataEntryFileComponentsPage;
  let prepaymentDataEntryFileDeleteDialog: PrepaymentDataEntryFileDeleteDialog;
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

  it('should load PrepaymentDataEntryFiles', async () => {
    await navBarPage.goToEntity('prepayment-data-entry-file');
    prepaymentDataEntryFileComponentsPage = new PrepaymentDataEntryFileComponentsPage();
    await browser.wait(ec.visibilityOf(prepaymentDataEntryFileComponentsPage.title), 5000);
    expect(await prepaymentDataEntryFileComponentsPage.getTitle()).to.eq('Prepayment Data Entry Files');
  });

  it('should load create PrepaymentDataEntryFile page', async () => {
    await prepaymentDataEntryFileComponentsPage.clickOnCreateButton();
    prepaymentDataEntryFileUpdatePage = new PrepaymentDataEntryFileUpdatePage();
    expect(await prepaymentDataEntryFileUpdatePage.getPageTitle()).to.eq('Create or edit a Prepayment Data Entry File');
    await prepaymentDataEntryFileUpdatePage.cancel();
  });

  it('should create and save PrepaymentDataEntryFiles', async () => {
    const nbButtonsBeforeCreate = await prepaymentDataEntryFileComponentsPage.countDeleteButtons();

    await prepaymentDataEntryFileComponentsPage.clickOnCreateButton();
    await promise.all([
      prepaymentDataEntryFileUpdatePage.setPeriodFromInput('2000-12-31'),
      prepaymentDataEntryFileUpdatePage.setPeriodToInput('2000-12-31'),
      prepaymentDataEntryFileUpdatePage.setDataEntryFileInput(absolutePath)
    ]);
    expect(await prepaymentDataEntryFileUpdatePage.getPeriodFromInput()).to.eq(
      '2000-12-31',
      'Expected periodFrom value to be equals to 2000-12-31'
    );
    expect(await prepaymentDataEntryFileUpdatePage.getPeriodToInput()).to.eq(
      '2000-12-31',
      'Expected periodTo value to be equals to 2000-12-31'
    );
    expect(await prepaymentDataEntryFileUpdatePage.getDataEntryFileInput()).to.endsWith(
      fileNameToUpload,
      'Expected DataEntryFile value to be end with ' + fileNameToUpload
    );
    const selectedUploadProcessed = prepaymentDataEntryFileUpdatePage.getUploadProcessedInput();
    if (await selectedUploadProcessed.isSelected()) {
      await prepaymentDataEntryFileUpdatePage.getUploadProcessedInput().click();
      expect(await prepaymentDataEntryFileUpdatePage.getUploadProcessedInput().isSelected(), 'Expected uploadProcessed not to be selected')
        .to.be.false;
    } else {
      await prepaymentDataEntryFileUpdatePage.getUploadProcessedInput().click();
      expect(await prepaymentDataEntryFileUpdatePage.getUploadProcessedInput().isSelected(), 'Expected uploadProcessed to be selected').to
        .be.true;
    }
    const selectedUploadSuccessful = prepaymentDataEntryFileUpdatePage.getUploadSuccessfulInput();
    if (await selectedUploadSuccessful.isSelected()) {
      await prepaymentDataEntryFileUpdatePage.getUploadSuccessfulInput().click();
      expect(
        await prepaymentDataEntryFileUpdatePage.getUploadSuccessfulInput().isSelected(),
        'Expected uploadSuccessful not to be selected'
      ).to.be.false;
    } else {
      await prepaymentDataEntryFileUpdatePage.getUploadSuccessfulInput().click();
      expect(await prepaymentDataEntryFileUpdatePage.getUploadSuccessfulInput().isSelected(), 'Expected uploadSuccessful to be selected').to
        .be.true;
    }
    await prepaymentDataEntryFileUpdatePage.save();
    expect(await prepaymentDataEntryFileUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await prepaymentDataEntryFileComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last PrepaymentDataEntryFile', async () => {
    const nbButtonsBeforeDelete = await prepaymentDataEntryFileComponentsPage.countDeleteButtons();
    await prepaymentDataEntryFileComponentsPage.clickOnLastDeleteButton();

    prepaymentDataEntryFileDeleteDialog = new PrepaymentDataEntryFileDeleteDialog();
    expect(await prepaymentDataEntryFileDeleteDialog.getDialogTitle()).to.eq(
      'Are you sure you want to delete this Prepayment Data Entry File?'
    );
    await prepaymentDataEntryFileDeleteDialog.clickOnConfirmButton();

    expect(await prepaymentDataEntryFileComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
