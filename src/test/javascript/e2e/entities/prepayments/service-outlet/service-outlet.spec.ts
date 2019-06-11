/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import { ServiceOutletComponentsPage, ServiceOutletDeleteDialog, ServiceOutletUpdatePage } from './service-outlet.page-object';

const expect = chai.expect;

describe('ServiceOutlet e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let serviceOutletUpdatePage: ServiceOutletUpdatePage;
  let serviceOutletComponentsPage: ServiceOutletComponentsPage;
  let serviceOutletDeleteDialog: ServiceOutletDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ServiceOutlets', async () => {
    await navBarPage.goToEntity('service-outlet');
    serviceOutletComponentsPage = new ServiceOutletComponentsPage();
    await browser.wait(ec.visibilityOf(serviceOutletComponentsPage.title), 5000);
    expect(await serviceOutletComponentsPage.getTitle()).to.eq('Service Outlets');
  });

  it('should load create ServiceOutlet page', async () => {
    await serviceOutletComponentsPage.clickOnCreateButton();
    serviceOutletUpdatePage = new ServiceOutletUpdatePage();
    expect(await serviceOutletUpdatePage.getPageTitle()).to.eq('Create or edit a Service Outlet');
    await serviceOutletUpdatePage.cancel();
  });

  it('should create and save ServiceOutlets', async () => {
    const nbButtonsBeforeCreate = await serviceOutletComponentsPage.countDeleteButtons();

    await serviceOutletComponentsPage.clickOnCreateButton();
    await promise.all([
      serviceOutletUpdatePage.setServiceOutletNameInput('serviceOutletName'),
      serviceOutletUpdatePage.setServiceOutletCodeInput('serviceOutletCode'),
      serviceOutletUpdatePage.setServiceOutletLocationInput('serviceOutletLocation'),
      serviceOutletUpdatePage.setServiceOutletManagerInput('serviceOutletManager'),
      serviceOutletUpdatePage.setNumberOfStaffInput('5'),
      serviceOutletUpdatePage.setBuildingInput('building'),
      serviceOutletUpdatePage.setFloorInput('5'),
      serviceOutletUpdatePage.setPostalAddressInput('postalAddress'),
      serviceOutletUpdatePage.setContactPersonNameInput('contactPersonName'),
      serviceOutletUpdatePage.setContactEmailInput('contactEmail'),
      serviceOutletUpdatePage.setStreetInput('street')
    ]);
    expect(await serviceOutletUpdatePage.getServiceOutletNameInput()).to.eq(
      'serviceOutletName',
      'Expected ServiceOutletName value to be equals to serviceOutletName'
    );
    expect(await serviceOutletUpdatePage.getServiceOutletCodeInput()).to.eq(
      'serviceOutletCode',
      'Expected ServiceOutletCode value to be equals to serviceOutletCode'
    );
    expect(await serviceOutletUpdatePage.getServiceOutletLocationInput()).to.eq(
      'serviceOutletLocation',
      'Expected ServiceOutletLocation value to be equals to serviceOutletLocation'
    );
    expect(await serviceOutletUpdatePage.getServiceOutletManagerInput()).to.eq(
      'serviceOutletManager',
      'Expected ServiceOutletManager value to be equals to serviceOutletManager'
    );
    expect(await serviceOutletUpdatePage.getNumberOfStaffInput()).to.eq('5', 'Expected numberOfStaff value to be equals to 5');
    expect(await serviceOutletUpdatePage.getBuildingInput()).to.eq('building', 'Expected Building value to be equals to building');
    expect(await serviceOutletUpdatePage.getFloorInput()).to.eq('5', 'Expected floor value to be equals to 5');
    expect(await serviceOutletUpdatePage.getPostalAddressInput()).to.eq(
      'postalAddress',
      'Expected PostalAddress value to be equals to postalAddress'
    );
    expect(await serviceOutletUpdatePage.getContactPersonNameInput()).to.eq(
      'contactPersonName',
      'Expected ContactPersonName value to be equals to contactPersonName'
    );
    expect(await serviceOutletUpdatePage.getContactEmailInput()).to.eq(
      'contactEmail',
      'Expected ContactEmail value to be equals to contactEmail'
    );
    expect(await serviceOutletUpdatePage.getStreetInput()).to.eq('street', 'Expected Street value to be equals to street');
    await serviceOutletUpdatePage.save();
    expect(await serviceOutletUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await serviceOutletComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last ServiceOutlet', async () => {
    const nbButtonsBeforeDelete = await serviceOutletComponentsPage.countDeleteButtons();
    await serviceOutletComponentsPage.clickOnLastDeleteButton();

    serviceOutletDeleteDialog = new ServiceOutletDeleteDialog();
    expect(await serviceOutletDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Service Outlet?');
    await serviceOutletDeleteDialog.clickOnConfirmButton();

    expect(await serviceOutletComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
