import { Moment } from 'moment';

export const enum AccountTypes {
  PREPAYMENT = 'PREPAYMENT',
  AMORTIZATION = 'AMORTIZATION'
}

export interface ITransactionAccount {
  id?: number;
  accountName?: string;
  accountNumber?: string;
  accountType?: AccountTypes;
  openingDate?: Moment;
  originatingFileToken?: string;
}

export class TransactionAccount implements ITransactionAccount {
  constructor(
    public id?: number,
    public accountName?: string,
    public accountNumber?: string,
    public accountType?: AccountTypes,
    public openingDate?: Moment,
    public originatingFileToken?: string
  ) {}
}
