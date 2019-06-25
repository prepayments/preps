import { Moment } from 'moment';

export interface IAmortizationUpload {
  id?: number;
  accountName?: string;
  particulars?: string;
  serviceOutletCode?: string;
  prepaymentAccountNumber?: string;
  expenseAccountNumber?: string;
  prepaymentTransactionId?: string;
  prepaymentTransactionDate?: Moment;
  prepaymentTransactionAmount?: number;
  amortizationAmount?: number;
  numberOfAmortizations?: number;
  firstAmortizationDate?: Moment;
}

export class AmortizationUpload implements IAmortizationUpload {
  constructor(
    public id?: number,
    public accountName?: string,
    public particulars?: string,
    public serviceOutletCode?: string,
    public prepaymentAccountNumber?: string,
    public expenseAccountNumber?: string,
    public prepaymentTransactionId?: string,
    public prepaymentTransactionDate?: Moment,
    public prepaymentTransactionAmount?: number,
    public amortizationAmount?: number,
    public numberOfAmortizations?: number,
    public firstAmortizationDate?: Moment
  ) {}
}
