import { Moment } from 'moment';

export interface IAmortizationEntry {
  id?: number;
  amortizationDate?: Moment;
  amortizationAmount?: number;
  particulars?: string;
  prepaymentServiceOutlet?: string;
  prepaymentAccountNumber?: string;
  amortizationServiceOutlet?: string;
  amortizationAccountNumber?: string;
  originatingFileToken?: string;
  amortizationTag?: string;
  orphaned?: boolean;
  prepaymentEntryId?: number;
}

export class AmortizationEntry implements IAmortizationEntry {
  constructor(
    public id?: number,
    public amortizationDate?: Moment,
    public amortizationAmount?: number,
    public particulars?: string,
    public prepaymentServiceOutlet?: string,
    public prepaymentAccountNumber?: string,
    public amortizationServiceOutlet?: string,
    public amortizationAccountNumber?: string,
    public originatingFileToken?: string,
    public amortizationTag?: string,
    public orphaned?: boolean,
    public prepaymentEntryId?: number
  ) {
    this.orphaned = this.orphaned || false;
  }
}
