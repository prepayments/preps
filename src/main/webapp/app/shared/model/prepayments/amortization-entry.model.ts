import { Moment } from 'moment';

export interface IAmortizationEntry {
  id?: number;
  amortizationDate?: Moment;
  amortizationAmount?: number;
  particulars?: string;
  prepaymentServiceOutlet?: string;
  prepaymentAccountNumber?: string;
  amortizationServiceOutlet?: string;
  amortizationAccountNumber?: string;
  OriginatingFileToken?: string;
  prepaymentEntryId?: number;
}

export class AmortizationEntry implements IAmortizationEntry {
  constructor(
    public id?: number,
    public amortizationDate?: Moment,
    public amortizationAmount?: number,
    public particulars?: string,
    public prepaymentServiceOutlet?: string,
    public prepaymentAccountNumber?: string,
    public amortizationServiceOutlet?: string,
    public amortizationAccountNumber?: string,
    public OriginatingFileToken?: string,
    public prepaymentEntryId?: number
  ) {}
}
