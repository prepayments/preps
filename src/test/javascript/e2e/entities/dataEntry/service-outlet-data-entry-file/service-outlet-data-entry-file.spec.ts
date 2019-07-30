/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import {
  ServiceOutletDataEntryFileComponentsPage,
  ServiceOutletDataEntryFileDeleteDialog,
  ServiceOutletDataEntryFileUpdatePage
} from './service-outlet-data-entry-file.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('ServiceOutletDataEntryFile e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let serviceOutletDataEntryFileUpdatePage: ServiceOutletDataEntryFileUpdatePage;
  let serviceOutletDataEntryFileComponentsPage: ServiceOutletDataEntryFileComponentsPage;
  let serviceOutletDataEntryFileDeleteDialog: ServiceOutletDataEntryFileDeleteDialog;
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

  it('should load ServiceOutletDataEntryFiles', async () => {
    await navBarPage.goToEntity('service-outlet-data-entry-file');
    serviceOutletDataEntryFileComponentsPage = new ServiceOutletDataEntryFileComponentsPage();
    await browser.wait(ec.visibilityOf(serviceOutletDataEntryFileComponentsPage.title), 5000);
    expect(await serviceOutletDataEntryFileComponentsPage.getTitle()).to.eq('Service Outlet Data Entry Files');
  });

  it('should load create ServiceOutletDataEntryFile page', async () => {
    await serviceOutletDataEntryFileComponentsPage.clickOnCreateButton();
    serviceOutletDataEntryFileUpdatePage = new ServiceOutletDataEntryFileUpdatePage();
    expect(await serviceOutletDataEntryFileUpdatePage.getPageTitle()).to.eq('Create or edit a Service Outlet Data Entry File');
    await serviceOutletDataEntryFileUpdatePage.cancel();
  });

  it('should create and save ServiceOutletDataEntryFiles', async () => {
    const nbButtonsBeforeCreate = await serviceOutletDataEntryFileComponentsPage.countDeleteButtons();

    await serviceOutletDataEntryFileComponentsPage.clickOnCreateButton();
    await promise.all([
      serviceOutletDataEntryFileUpdatePage.setPeriodFromInput('2000-12-31'),
      serviceOutletDataEntryFileUpdatePage.setPeriodToInput('2000-12-31'),
      serviceOutletDataEntryFileUpdatePage.setDataEntryFileInput(absolutePath),
      serviceOutletDataEntryFileUpdatePage.setEntriesCountInput('5'),
      serviceOutletDataEntryFileUpdatePage.setFileTokenInput('fileToken')
    ]);
    expect(await serviceOutletDataEntryFileUpdatePage.getPeriodFromInput()).to.eq(
      '2000-12-31',
      'Expected periodFrom value to be equals to 2000-12-31'
    );
    expect(await serviceOutletDataEntryFileUpdatePage.getPeriodToInput()).to.eq(
      '2000-12-31',
      'Expected periodTo value to be equals to 2000-12-31'
    );
    const selectedUploadSuccessful = serviceOutletDataEntryFileUpdatePage.getUploadSuccessfulInput();
    if (await selectedUploadSuccessful.isSelected()) {
      await serviceOutletDataEntryFileUpdatePage.getUploadSuccessfulInput().click();
      expect(
        await serviceOutletDataEntryFileUpdatePage.getUploadSuccessfulInput().isSelected(),
        'Expected uploadSuccessful not to be selected'
      ).to.be.false;
    } else {
      await serviceOutletDataEntryFileUpdatePage.getUploadSuccessfulInput().click();
      expect(await serviceOutletDataEntryFileUpdatePage.getUploadSuccessfulInput().isSelected(), 'Expected uploadSuccessful to be selected')
        .to.be.true;
    }
    const selectedUploadProcessed = serviceOutletDataEntryFileUpdatePage.getUploadProcessedInput();
    if (await selectedUploadProcessed.isSelected()) {
      await serviceOutletDataEntryFileUpdatePage.getUploadProcessedInput().click();
      expect(
        await serviceOutletDataEntryFileUpdatePage.getUploadProcessedInput().isSelected(),
        'Expected uploadProcessed not to be selected'
      ).to.be.false;
    } else {
      await serviceOutletDataEntryFileUpdatePage.getUploadProcessedInput().click();
      expect(await serviceOutletDataEntryFileUpdatePage.getUploadProcessedInput().isSelected(), 'Expected uploadProcessed to be selected')
        .to.be.true;
    }
    expect(await serviceOutletDataEntryFileUpdatePage.getDataEntryFileInput()).to.endsWith(
      fileNameToUpload,
      'Expected DataEntryFile value to be end with ' + fileNameToUpload
    );
    expect(await serviceOutletDataEntryFileUpdatePage.getEntriesCountInput()).to.eq('5', 'Expected entriesCount value to be equals to 5');
    expect(await serviceOutletDataEntryFileUpdatePage.getFileTokenInput()).to.eq(
      'fileToken',
      'Expected FileToken value to be equals to fileToken'
    );
    await serviceOutletDataEntryFileUpdatePage.save();
    expect(await serviceOutletDataEntryFileUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await serviceOutletDataEntryFileComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last ServiceOutletDataEntryFile', async () => {
    const nbButtonsBeforeDelete = await serviceOutletDataEntryFileComponentsPage.countDeleteButtons();
    await serviceOutletDataEntryFileComponentsPage.clickOnLastDeleteButton();

    serviceOutletDataEntryFileDeleteDialog = new ServiceOutletDataEntryFileDeleteDialog();
    expect(await serviceOutletDataEntryFileDeleteDialog.getDialogTitle()).to.eq(
      'Are you sure you want to delete this Service Outlet Data Entry File?'
    );
    await serviceOutletDataEntryFileDeleteDialog.clickOnConfirmButton();

    expect(await serviceOutletDataEntryFileComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
