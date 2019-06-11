/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import { ReportTypeComponentsPage, ReportTypeDeleteDialog, ReportTypeUpdatePage } from './report-type.page-object';

const expect = chai.expect;

describe('ReportType e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let reportTypeUpdatePage: ReportTypeUpdatePage;
  let reportTypeComponentsPage: ReportTypeComponentsPage;
  let reportTypeDeleteDialog: ReportTypeDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ReportTypes', async () => {
    await navBarPage.goToEntity('report-type');
    reportTypeComponentsPage = new ReportTypeComponentsPage();
    await browser.wait(ec.visibilityOf(reportTypeComponentsPage.title), 5000);
    expect(await reportTypeComponentsPage.getTitle()).to.eq('Report Types');
  });

  it('should load create ReportType page', async () => {
    await reportTypeComponentsPage.clickOnCreateButton();
    reportTypeUpdatePage = new ReportTypeUpdatePage();
    expect(await reportTypeUpdatePage.getPageTitle()).to.eq('Create or edit a Report Type');
    await reportTypeUpdatePage.cancel();
  });

  it('should create and save ReportTypes', async () => {
    const nbButtonsBeforeCreate = await reportTypeComponentsPage.countDeleteButtons();

    await reportTypeComponentsPage.clickOnCreateButton();
    await promise.all([
      reportTypeUpdatePage.setReportModelNameInput('reportModelName'),
      reportTypeUpdatePage.reportMediumTypeSelectLastOption(),
      reportTypeUpdatePage.setReportPasswordInput('reportPassword')
    ]);
    expect(await reportTypeUpdatePage.getReportModelNameInput()).to.eq(
      'reportModelName',
      'Expected ReportModelName value to be equals to reportModelName'
    );
    expect(await reportTypeUpdatePage.getReportPasswordInput()).to.eq(
      'reportPassword',
      'Expected ReportPassword value to be equals to reportPassword'
    );
    await reportTypeUpdatePage.save();
    expect(await reportTypeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await reportTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last ReportType', async () => {
    const nbButtonsBeforeDelete = await reportTypeComponentsPage.countDeleteButtons();
    await reportTypeComponentsPage.clickOnLastDeleteButton();

    reportTypeDeleteDialog = new ReportTypeDeleteDialog();
    expect(await reportTypeDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Report Type?');
    await reportTypeDeleteDialog.clickOnConfirmButton();

    expect(await reportTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
