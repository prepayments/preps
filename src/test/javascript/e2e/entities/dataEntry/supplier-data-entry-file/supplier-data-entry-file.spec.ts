/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import {
  SupplierDataEntryFileComponentsPage,
  SupplierDataEntryFileDeleteDialog,
  SupplierDataEntryFileUpdatePage
} from './supplier-data-entry-file.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('SupplierDataEntryFile e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let supplierDataEntryFileUpdatePage: SupplierDataEntryFileUpdatePage;
  let supplierDataEntryFileComponentsPage: SupplierDataEntryFileComponentsPage;
  let supplierDataEntryFileDeleteDialog: SupplierDataEntryFileDeleteDialog;
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

  it('should load SupplierDataEntryFiles', async () => {
    await navBarPage.goToEntity('supplier-data-entry-file');
    supplierDataEntryFileComponentsPage = new SupplierDataEntryFileComponentsPage();
    await browser.wait(ec.visibilityOf(supplierDataEntryFileComponentsPage.title), 5000);
    expect(await supplierDataEntryFileComponentsPage.getTitle()).to.eq('Supplier Data Entry Files');
  });

  it('should load create SupplierDataEntryFile page', async () => {
    await supplierDataEntryFileComponentsPage.clickOnCreateButton();
    supplierDataEntryFileUpdatePage = new SupplierDataEntryFileUpdatePage();
    expect(await supplierDataEntryFileUpdatePage.getPageTitle()).to.eq('Create or edit a Supplier Data Entry File');
    await supplierDataEntryFileUpdatePage.cancel();
  });

  it('should create and save SupplierDataEntryFiles', async () => {
    const nbButtonsBeforeCreate = await supplierDataEntryFileComponentsPage.countDeleteButtons();

    await supplierDataEntryFileComponentsPage.clickOnCreateButton();
    await promise.all([
      supplierDataEntryFileUpdatePage.setPeriodFromInput('2000-12-31'),
      supplierDataEntryFileUpdatePage.setPeriodToInput('2000-12-31'),
      supplierDataEntryFileUpdatePage.setDataEntryFileInput(absolutePath)
    ]);
    expect(await supplierDataEntryFileUpdatePage.getPeriodFromInput()).to.eq(
      '2000-12-31',
      'Expected periodFrom value to be equals to 2000-12-31'
    );
    expect(await supplierDataEntryFileUpdatePage.getPeriodToInput()).to.eq(
      '2000-12-31',
      'Expected periodTo value to be equals to 2000-12-31'
    );
    expect(await supplierDataEntryFileUpdatePage.getDataEntryFileInput()).to.endsWith(
      fileNameToUpload,
      'Expected DataEntryFile value to be end with ' + fileNameToUpload
    );
    const selectedUploadSuccessful = supplierDataEntryFileUpdatePage.getUploadSuccessfulInput();
    if (await selectedUploadSuccessful.isSelected()) {
      await supplierDataEntryFileUpdatePage.getUploadSuccessfulInput().click();
      expect(await supplierDataEntryFileUpdatePage.getUploadSuccessfulInput().isSelected(), 'Expected uploadSuccessful not to be selected')
        .to.be.false;
    } else {
      await supplierDataEntryFileUpdatePage.getUploadSuccessfulInput().click();
      expect(await supplierDataEntryFileUpdatePage.getUploadSuccessfulInput().isSelected(), 'Expected uploadSuccessful to be selected').to
        .be.true;
    }
    const selectedUploadProcessed = supplierDataEntryFileUpdatePage.getUploadProcessedInput();
    if (await selectedUploadProcessed.isSelected()) {
      await supplierDataEntryFileUpdatePage.getUploadProcessedInput().click();
      expect(await supplierDataEntryFileUpdatePage.getUploadProcessedInput().isSelected(), 'Expected uploadProcessed not to be selected').to
        .be.false;
    } else {
      await supplierDataEntryFileUpdatePage.getUploadProcessedInput().click();
      expect(await supplierDataEntryFileUpdatePage.getUploadProcessedInput().isSelected(), 'Expected uploadProcessed to be selected').to.be
        .true;
    }
    await supplierDataEntryFileUpdatePage.save();
    expect(await supplierDataEntryFileUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await supplierDataEntryFileComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last SupplierDataEntryFile', async () => {
    const nbButtonsBeforeDelete = await supplierDataEntryFileComponentsPage.countDeleteButtons();
    await supplierDataEntryFileComponentsPage.clickOnLastDeleteButton();

    supplierDataEntryFileDeleteDialog = new SupplierDataEntryFileDeleteDialog();
    expect(await supplierDataEntryFileDeleteDialog.getDialogTitle()).to.eq(
      'Are you sure you want to delete this Supplier Data Entry File?'
    );
    await supplierDataEntryFileDeleteDialog.clickOnConfirmButton();

    expect(await supplierDataEntryFileComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
