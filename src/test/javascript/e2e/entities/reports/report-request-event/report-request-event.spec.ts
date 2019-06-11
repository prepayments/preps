/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import {
  ReportRequestEventComponentsPage,
  ReportRequestEventDeleteDialog,
  ReportRequestEventUpdatePage
} from './report-request-event.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('ReportRequestEvent e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let reportRequestEventUpdatePage: ReportRequestEventUpdatePage;
  let reportRequestEventComponentsPage: ReportRequestEventComponentsPage;
  let reportRequestEventDeleteDialog: ReportRequestEventDeleteDialog;
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

  it('should load ReportRequestEvents', async () => {
    await navBarPage.goToEntity('report-request-event');
    reportRequestEventComponentsPage = new ReportRequestEventComponentsPage();
    await browser.wait(ec.visibilityOf(reportRequestEventComponentsPage.title), 5000);
    expect(await reportRequestEventComponentsPage.getTitle()).to.eq('Report Request Events');
  });

  it('should load create ReportRequestEvent page', async () => {
    await reportRequestEventComponentsPage.clickOnCreateButton();
    reportRequestEventUpdatePage = new ReportRequestEventUpdatePage();
    expect(await reportRequestEventUpdatePage.getPageTitle()).to.eq('Create or edit a Report Request Event');
    await reportRequestEventUpdatePage.cancel();
  });

  it('should create and save ReportRequestEvents', async () => {
    const nbButtonsBeforeCreate = await reportRequestEventComponentsPage.countDeleteButtons();

    await reportRequestEventComponentsPage.clickOnCreateButton();
    await promise.all([
      reportRequestEventUpdatePage.setReportRequestDateInput('2000-12-31'),
      reportRequestEventUpdatePage.setRequestedByInput('requestedBy'),
      reportRequestEventUpdatePage.setReportFileInput(absolutePath),
      reportRequestEventUpdatePage.reportTypeSelectLastOption()
    ]);
    expect(await reportRequestEventUpdatePage.getReportRequestDateInput()).to.eq(
      '2000-12-31',
      'Expected reportRequestDate value to be equals to 2000-12-31'
    );
    expect(await reportRequestEventUpdatePage.getRequestedByInput()).to.eq(
      'requestedBy',
      'Expected RequestedBy value to be equals to requestedBy'
    );
    expect(await reportRequestEventUpdatePage.getReportFileInput()).to.endsWith(
      fileNameToUpload,
      'Expected ReportFile value to be end with ' + fileNameToUpload
    );
    await reportRequestEventUpdatePage.save();
    expect(await reportRequestEventUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await reportRequestEventComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last ReportRequestEvent', async () => {
    const nbButtonsBeforeDelete = await reportRequestEventComponentsPage.countDeleteButtons();
    await reportRequestEventComponentsPage.clickOnLastDeleteButton();

    reportRequestEventDeleteDialog = new ReportRequestEventDeleteDialog();
    expect(await reportRequestEventDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Report Request Event?');
    await reportRequestEventDeleteDialog.clickOnConfirmButton();

    expect(await reportRequestEventComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
