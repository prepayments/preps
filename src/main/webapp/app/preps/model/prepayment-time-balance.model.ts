import { Moment } from 'moment';

export interface IPrepaymentTimeBalance {
  accountName?: string;
  accountNumber?: string;
  balanceAmount?: number;
  id?: number;
  invoiceNumber?: string;
  months?: number;
  particulars?: string;
  prepaymentAmount?: number;
  prepaymentDate?: Moment;
  prepaymentId?: number;
  scannedDocumentId?: number;
  serviceOutlet?: string;
  supplierName?: string;
}

export class PrepaymentTimeBalance implements IPrepaymentTimeBalance {
  accountName: string;
  accountNumber: string;
  balanceAmount: number;
  id: number;
  invoiceNumber: string;
  months: number;
  particulars: string;
  prepaymentAmount: number;
  prepaymentDate: Moment;
  prepaymentId: number;
  scannedDocumentId: number;
  serviceOutlet: string;
  supplierName: string;

  constructor(
    public options: {
      accountName?: string;
      accountNumber?: string;
      balanceAmount?: number;
      id?: number;
      invoiceNumber?: string;
      months?: number;
      particulars?: string;
      prepaymentAmount?: number;
      prepaymentDate?: Moment;
      prepaymentId?: number;
      scannedDocumentId?: number;
      serviceOutlet?: string;
      supplierName?: string;
    }
  ) {
    this.accountName = options.accountName || '';
    this.accountNumber = options.accountNumber || '';
    this.balanceAmount = options.balanceAmount || 0;
    this.id = options.id || 0;
    this.invoiceNumber = options.invoiceNumber || '';
    this.months = options.months || 0;
    this.particulars = options.particulars || '';
    this.prepaymentAmount = options.prepaymentAmount || 0;
    this.prepaymentDate = options.prepaymentDate;
    this.prepaymentId = options.prepaymentId || 0;
    this.scannedDocumentId = options.scannedDocumentId || 0;
    this.serviceOutlet = options.serviceOutlet || '';
    this.supplierName = options.supplierName || '';
  }
}
