import { Moment } from 'moment';
import { IAmortizationEntry } from 'app/shared/model/prepayments/amortization-entry.model';

export interface IPrepaymentEntry {
  id?: number;
  accountNumber?: string;
  accountName?: string;
  prepaymentId?: string;
  prepaymentDate?: Moment;
  particulars?: string;
  serviceOutlet?: string;
  prepaymentAmount?: number;
  months?: number;
  supplierName?: string;
  invoiceNumber?: string;
  scannedDocumentId?: number;
  originatingFileToken?: string;
  amortizationEntries?: IAmortizationEntry[];
}

export class PrepaymentEntry implements IPrepaymentEntry {
  constructor(
    public id?: number,
    public accountNumber?: string,
    public accountName?: string,
    public prepaymentId?: string,
    public prepaymentDate?: Moment,
    public particulars?: string,
    public serviceOutlet?: string,
    public prepaymentAmount?: number,
    public months?: number,
    public supplierName?: string,
    public invoiceNumber?: string,
    public scannedDocumentId?: number,
    public originatingFileToken?: string,
    public amortizationEntries?: IAmortizationEntry[]
  ) {}
}
