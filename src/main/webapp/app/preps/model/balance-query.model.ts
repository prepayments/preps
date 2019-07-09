import { Moment } from 'moment';
import moment = require('moment');

export interface IBalanceQuery {
  balanceDate?: Moment;
  accountName?: string;
  serviceOutlet?: string;
}

export class BalanceQuery implements IBalanceQuery {
  balanceDate?: Moment;
  accountName?: string;
  serviceOutlet?: string;

  constructor(
    public parameters: {
      balanceDate?: Moment;
      accountName?: string;
      serviceOutlet?: string;
    }
  ) {
    const defaultDate: Moment = moment('2019-01-01', 'YYYY-MM-DD');

    this.balanceDate = parameters.balanceDate || defaultDate;
    this.accountName = parameters.accountName || '';
    this.serviceOutlet = parameters.serviceOutlet || '';
  }
}
