import { Moment } from 'moment';

export interface IAccountingTransaction {
  id?: number;
  accountName?: string;
  accountNumber?: string;
  transactionDate?: Moment;
  transactionAmount?: number;
  incrementAccount?: boolean;
}

export class AccountingTransaction implements IAccountingTransaction {
  constructor(
    public id?: number,
    public accountName?: string,
    public accountNumber?: string,
    public transactionDate?: Moment,
    public transactionAmount?: number,
    public incrementAccount?: boolean
  ) {
    this.incrementAccount = this.incrementAccount || false;
  }
}
