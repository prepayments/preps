import { Moment } from 'moment';

export interface ITransactionAccount {
  id?: number;
  accountName?: string;
  accountNumber?: string;
  accountBalance?: number;
  openingDate?: Moment;
  accountOpeningDateBalance?: number;
}

export class TransactionAccount implements ITransactionAccount {
  constructor(
    public id?: number,
    public accountName?: string,
    public accountNumber?: string,
    public accountBalance?: number,
    public openingDate?: Moment,
    public accountOpeningDateBalance?: number
  ) {}
}
