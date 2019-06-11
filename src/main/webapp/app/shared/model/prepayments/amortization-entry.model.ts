import { Moment } from 'moment';

export interface IAmortizationEntry {
  id?: number;
  amortizationDate?: Moment;
  amortizationAmount?: number;
  particulars?: string;
  posted?: boolean;
  serviceOutlet?: string;
  accountNumber?: string;
  accountName?: string;
  prepaymentEntryId?: number;
}

export class AmortizationEntry implements IAmortizationEntry {
  constructor(
    public id?: number,
    public amortizationDate?: Moment,
    public amortizationAmount?: number,
    public particulars?: string,
    public posted?: boolean,
    public serviceOutlet?: string,
    public accountNumber?: string,
    public accountName?: string,
    public prepaymentEntryId?: number
  ) {
    this.posted = this.posted || false;
  }
}
