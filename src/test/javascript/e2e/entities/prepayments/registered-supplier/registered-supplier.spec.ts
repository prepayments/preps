/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import {
  RegisteredSupplierComponentsPage,
  RegisteredSupplierDeleteDialog,
  RegisteredSupplierUpdatePage
} from './registered-supplier.page-object';

const expect = chai.expect;

describe('RegisteredSupplier e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let registeredSupplierUpdatePage: RegisteredSupplierUpdatePage;
  let registeredSupplierComponentsPage: RegisteredSupplierComponentsPage;
  let registeredSupplierDeleteDialog: RegisteredSupplierDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load RegisteredSuppliers', async () => {
    await navBarPage.goToEntity('registered-supplier');
    registeredSupplierComponentsPage = new RegisteredSupplierComponentsPage();
    await browser.wait(ec.visibilityOf(registeredSupplierComponentsPage.title), 5000);
    expect(await registeredSupplierComponentsPage.getTitle()).to.eq('Registered Suppliers');
  });

  it('should load create RegisteredSupplier page', async () => {
    await registeredSupplierComponentsPage.clickOnCreateButton();
    registeredSupplierUpdatePage = new RegisteredSupplierUpdatePage();
    expect(await registeredSupplierUpdatePage.getPageTitle()).to.eq('Create or edit a Registered Supplier');
    await registeredSupplierUpdatePage.cancel();
  });

  it('should create and save RegisteredSuppliers', async () => {
    const nbButtonsBeforeCreate = await registeredSupplierComponentsPage.countDeleteButtons();

    await registeredSupplierComponentsPage.clickOnCreateButton();
    await promise.all([
      registeredSupplierUpdatePage.setSupplierNameInput('supplierName'),
      registeredSupplierUpdatePage.setSupplierAddressInput('supplierAddress'),
      registeredSupplierUpdatePage.setPhoneNumberInput('phoneNumber'),
      registeredSupplierUpdatePage.setSupplierEmailInput('supplierEmail'),
      registeredSupplierUpdatePage.setBankAccountNameInput('bankAccountName'),
      registeredSupplierUpdatePage.setBankAccountNumberInput('bankAccountNumber'),
      registeredSupplierUpdatePage.setSupplierBankNameInput('supplierBankName'),
      registeredSupplierUpdatePage.setSupplierBankBranchInput('supplierBankBranch'),
      registeredSupplierUpdatePage.setBankSwiftCodeInput('bankSwiftCode'),
      registeredSupplierUpdatePage.setBankPhysicalAddressInput('bankPhysicalAddress'),
      registeredSupplierUpdatePage.setDomicileInput('domicile'),
      registeredSupplierUpdatePage.setTaxAuthorityPINInput('taxAuthorityPIN'),
      registeredSupplierUpdatePage.setOriginatingFileTokenInput('originatingFileToken')
    ]);
    expect(await registeredSupplierUpdatePage.getSupplierNameInput()).to.eq(
      'supplierName',
      'Expected SupplierName value to be equals to supplierName'
    );
    expect(await registeredSupplierUpdatePage.getSupplierAddressInput()).to.eq(
      'supplierAddress',
      'Expected SupplierAddress value to be equals to supplierAddress'
    );
    expect(await registeredSupplierUpdatePage.getPhoneNumberInput()).to.eq(
      'phoneNumber',
      'Expected PhoneNumber value to be equals to phoneNumber'
    );
    expect(await registeredSupplierUpdatePage.getSupplierEmailInput()).to.eq(
      'supplierEmail',
      'Expected SupplierEmail value to be equals to supplierEmail'
    );
    expect(await registeredSupplierUpdatePage.getBankAccountNameInput()).to.eq(
      'bankAccountName',
      'Expected BankAccountName value to be equals to bankAccountName'
    );
    expect(await registeredSupplierUpdatePage.getBankAccountNumberInput()).to.eq(
      'bankAccountNumber',
      'Expected BankAccountNumber value to be equals to bankAccountNumber'
    );
    expect(await registeredSupplierUpdatePage.getSupplierBankNameInput()).to.eq(
      'supplierBankName',
      'Expected SupplierBankName value to be equals to supplierBankName'
    );
    expect(await registeredSupplierUpdatePage.getSupplierBankBranchInput()).to.eq(
      'supplierBankBranch',
      'Expected SupplierBankBranch value to be equals to supplierBankBranch'
    );
    expect(await registeredSupplierUpdatePage.getBankSwiftCodeInput()).to.eq(
      'bankSwiftCode',
      'Expected BankSwiftCode value to be equals to bankSwiftCode'
    );
    expect(await registeredSupplierUpdatePage.getBankPhysicalAddressInput()).to.eq(
      'bankPhysicalAddress',
      'Expected BankPhysicalAddress value to be equals to bankPhysicalAddress'
    );
    expect(await registeredSupplierUpdatePage.getDomicileInput()).to.eq('domicile', 'Expected Domicile value to be equals to domicile');
    expect(await registeredSupplierUpdatePage.getTaxAuthorityPINInput()).to.eq(
      'taxAuthorityPIN',
      'Expected TaxAuthorityPIN value to be equals to taxAuthorityPIN'
    );
    expect(await registeredSupplierUpdatePage.getOriginatingFileTokenInput()).to.eq(
      'originatingFileToken',
      'Expected OriginatingFileToken value to be equals to originatingFileToken'
    );
    await registeredSupplierUpdatePage.save();
    expect(await registeredSupplierUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await registeredSupplierComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last RegisteredSupplier', async () => {
    const nbButtonsBeforeDelete = await registeredSupplierComponentsPage.countDeleteButtons();
    await registeredSupplierComponentsPage.clickOnLastDeleteButton();

    registeredSupplierDeleteDialog = new RegisteredSupplierDeleteDialog();
    expect(await registeredSupplierDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Registered Supplier?');
    await registeredSupplierDeleteDialog.clickOnConfirmButton();

    expect(await registeredSupplierComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
