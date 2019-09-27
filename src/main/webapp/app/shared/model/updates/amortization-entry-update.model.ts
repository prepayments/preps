import { Moment } from 'moment';

export interface IAmortizationEntryUpdate {
  id?: number;
  amortizationEntryId?: number;
  amortizationDate?: Moment;
  amortizationAmount?: number;
  particulars?: string;
  prepaymentServiceOutlet?: string;
  prepaymentAccountNumber?: string;
  amortizationServiceOutlet?: string;
  amortizationAccountNumber?: string;
  prepaymentEntryId?: number;
  originatingFileToken?: string;
  amortizationTag?: string;
  orphaned?: boolean;
}

export class AmortizationEntryUpdate implements IAmortizationEntryUpdate {
  constructor(
    public id?: number,
    public amortizationEntryId?: number,
    public amortizationDate?: Moment,
    public amortizationAmount?: number,
    public particulars?: string,
    public prepaymentServiceOutlet?: string,
    public prepaymentAccountNumber?: string,
    public amortizationServiceOutlet?: string,
    public amortizationAccountNumber?: string,
    public prepaymentEntryId?: number,
    public originatingFileToken?: string,
    public amortizationTag?: string,
    public orphaned?: boolean
  ) {
    this.orphaned = this.orphaned || false;
  }
}
