import { Moment } from 'moment';

export interface IAccountingTransaction {
  id?: number;
  description?: string;
  serviceOutletCode?: string;
  accountName?: string;
  accountNumber?: string;
  transactionDate?: Moment;
  transactionAmount?: number;
  incrementAccount?: boolean;
  originatingFileToken?: string;
}

export class AccountingTransaction implements IAccountingTransaction {
  constructor(
    public id?: number,
    public description?: string,
    public serviceOutletCode?: string,
    public accountName?: string,
    public accountNumber?: string,
    public transactionDate?: Moment,
    public transactionAmount?: number,
    public incrementAccount?: boolean,
    public originatingFileToken?: string
  ) {
    this.incrementAccount = this.incrementAccount || false;
  }
}
