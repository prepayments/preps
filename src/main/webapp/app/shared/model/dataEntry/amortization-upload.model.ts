import { Moment } from 'moment';

export interface IAmortizationUpload {
  id?: number;
  accountName?: string;
  particulars?: string;
  amortizationServiceOutletCode?: string;
  prepaymentServiceOutletCode?: string;
  prepaymentAccountNumber?: string;
  expenseAccountNumber?: string;
  prepaymentTransactionId?: string;
  prepaymentTransactionDate?: Moment;
  prepaymentTransactionAmount?: number;
  amortizationAmount?: number;
  numberOfAmortizations?: number;
  firstAmortizationDate?: Moment;
  monthlyAmortizationDate?: number;
  uploadSuccessful?: boolean;
  uploadOrphaned?: boolean;
  originatingFileToken?: string;
  amortizationTag?: string;
}

export class AmortizationUpload implements IAmortizationUpload {
  constructor(
    public id?: number,
    public accountName?: string,
    public particulars?: string,
    public amortizationServiceOutletCode?: string,
    public prepaymentServiceOutletCode?: string,
    public prepaymentAccountNumber?: string,
    public expenseAccountNumber?: string,
    public prepaymentTransactionId?: string,
    public prepaymentTransactionDate?: Moment,
    public prepaymentTransactionAmount?: number,
    public amortizationAmount?: number,
    public numberOfAmortizations?: number,
    public firstAmortizationDate?: Moment,
    public monthlyAmortizationDate?: number,
    public uploadSuccessful?: boolean,
    public uploadOrphaned?: boolean,
    public originatingFileToken?: string,
    public amortizationTag?: string
  ) {
    this.uploadSuccessful = this.uploadSuccessful || false;
    this.uploadOrphaned = this.uploadOrphaned || false;
  }
}
