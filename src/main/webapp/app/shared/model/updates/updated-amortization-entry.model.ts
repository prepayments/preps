import { Moment } from 'moment';

export interface IUpdatedAmortizationEntry {
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
  dateOfUpdate?: Moment;
  reasonForUpdate?: string;
  prepaymentEntryId?: number;
}

export class UpdatedAmortizationEntry implements IUpdatedAmortizationEntry {
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
    public dateOfUpdate?: Moment,
    public reasonForUpdate?: string,
    public prepaymentEntryId?: number
  ) {
    this.orphaned = this.orphaned || false;
  }
}
