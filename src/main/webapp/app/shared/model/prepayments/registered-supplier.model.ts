export interface IRegisteredSupplier {
  id?: number;
  supplierName?: string;
  supplierAddress?: string;
  phoneNumber?: string;
  supplierEmail?: string;
  bankAccountName?: string;
  bankAccountNumber?: string;
  supplierBankName?: string;
  supplierBankBranch?: string;
  bankSwiftCode?: string;
  bankPhysicalAddress?: string;
  domicile?: string;
  taxAuthorityPIN?: string;
  originatingFileToken?: string;
}

export class RegisteredSupplier implements IRegisteredSupplier {
  constructor(
    public id?: number,
    public supplierName?: string,
    public supplierAddress?: string,
    public phoneNumber?: string,
    public supplierEmail?: string,
    public bankAccountName?: string,
    public bankAccountNumber?: string,
    public supplierBankName?: string,
    public supplierBankBranch?: string,
    public bankSwiftCode?: string,
    public bankPhysicalAddress?: string,
    public domicile?: string,
    public taxAuthorityPIN?: string,
    public originatingFileToken?: string
  ) {}
}
