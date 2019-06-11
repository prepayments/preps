/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import {
  TransactionAccountDataEntryFileComponentsPage,
  TransactionAccountDataEntryFileDeleteDialog,
  TransactionAccountDataEntryFileUpdatePage
} from './transaction-account-data-entry-file.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('TransactionAccountDataEntryFile e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let transactionAccountDataEntryFileUpdatePage: TransactionAccountDataEntryFileUpdatePage;
  let transactionAccountDataEntryFileComponentsPage: TransactionAccountDataEntryFileComponentsPage;
  let transactionAccountDataEntryFileDeleteDialog: TransactionAccountDataEntryFileDeleteDialog;
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

  it('should load TransactionAccountDataEntryFiles', async () => {
    await navBarPage.goToEntity('transaction-account-data-entry-file');
    transactionAccountDataEntryFileComponentsPage = new TransactionAccountDataEntryFileComponentsPage();
    await browser.wait(ec.visibilityOf(transactionAccountDataEntryFileComponentsPage.title), 5000);
    expect(await transactionAccountDataEntryFileComponentsPage.getTitle()).to.eq('Transaction Account Data Entry Files');
  });

  it('should load create TransactionAccountDataEntryFile page', async () => {
    await transactionAccountDataEntryFileComponentsPage.clickOnCreateButton();
    transactionAccountDataEntryFileUpdatePage = new TransactionAccountDataEntryFileUpdatePage();
    expect(await transactionAccountDataEntryFileUpdatePage.getPageTitle()).to.eq('Create or edit a Transaction Account Data Entry File');
    await transactionAccountDataEntryFileUpdatePage.cancel();
  });

  it('should create and save TransactionAccountDataEntryFiles', async () => {
    const nbButtonsBeforeCreate = await transactionAccountDataEntryFileComponentsPage.countDeleteButtons();

    await transactionAccountDataEntryFileComponentsPage.clickOnCreateButton();
    await promise.all([
      transactionAccountDataEntryFileUpdatePage.setPeriodFromInput('2000-12-31'),
      transactionAccountDataEntryFileUpdatePage.setPeriodToInput('2000-12-31'),
      transactionAccountDataEntryFileUpdatePage.setDataEntryFileInput(absolutePath)
    ]);
    expect(await transactionAccountDataEntryFileUpdatePage.getPeriodFromInput()).to.eq(
      '2000-12-31',
      'Expected periodFrom value to be equals to 2000-12-31'
    );
    expect(await transactionAccountDataEntryFileUpdatePage.getPeriodToInput()).to.eq(
      '2000-12-31',
      'Expected periodTo value to be equals to 2000-12-31'
    );
    expect(await transactionAccountDataEntryFileUpdatePage.getDataEntryFileInput()).to.endsWith(
      fileNameToUpload,
      'Expected DataEntryFile value to be end with ' + fileNameToUpload
    );
    const selectedUploadSuccessful = transactionAccountDataEntryFileUpdatePage.getUploadSuccessfulInput();
    if (await selectedUploadSuccessful.isSelected()) {
      await transactionAccountDataEntryFileUpdatePage.getUploadSuccessfulInput().click();
      expect(
        await transactionAccountDataEntryFileUpdatePage.getUploadSuccessfulInput().isSelected(),
        'Expected uploadSuccessful not to be selected'
      ).to.be.false;
    } else {
      await transactionAccountDataEntryFileUpdatePage.getUploadSuccessfulInput().click();
      expect(
        await transactionAccountDataEntryFileUpdatePage.getUploadSuccessfulInput().isSelected(),
        'Expected uploadSuccessful to be selected'
      ).to.be.true;
    }
    const selectedUploadProcessed = transactionAccountDataEntryFileUpdatePage.getUploadProcessedInput();
    if (await selectedUploadProcessed.isSelected()) {
      await transactionAccountDataEntryFileUpdatePage.getUploadProcessedInput().click();
      expect(
        await transactionAccountDataEntryFileUpdatePage.getUploadProcessedInput().isSelected(),
        'Expected uploadProcessed not to be selected'
      ).to.be.false;
    } else {
      await transactionAccountDataEntryFileUpdatePage.getUploadProcessedInput().click();
      expect(
        await transactionAccountDataEntryFileUpdatePage.getUploadProcessedInput().isSelected(),
        'Expected uploadProcessed to be selected'
      ).to.be.true;
    }
    await transactionAccountDataEntryFileUpdatePage.save();
    expect(await transactionAccountDataEntryFileUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await transactionAccountDataEntryFileComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last TransactionAccountDataEntryFile', async () => {
    const nbButtonsBeforeDelete = await transactionAccountDataEntryFileComponentsPage.countDeleteButtons();
    await transactionAccountDataEntryFileComponentsPage.clickOnLastDeleteButton();

    transactionAccountDataEntryFileDeleteDialog = new TransactionAccountDataEntryFileDeleteDialog();
    expect(await transactionAccountDataEntryFileDeleteDialog.getDialogTitle()).to.eq(
      'Are you sure you want to delete this Transaction Account Data Entry File?'
    );
    await transactionAccountDataEntryFileDeleteDialog.clickOnConfirmButton();

    expect(await transactionAccountDataEntryFileComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
