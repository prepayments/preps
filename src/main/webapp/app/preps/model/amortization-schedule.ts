export interface IAmortizationSchedule {
  prepaymentEntryId?: number;
  amortizationEntryId?: number;
  prepaymentAccount?: string;
  amortizationAccount?: string;
  particulars?: string;
  prepaymentBalance?: number;
  amortizationAmount?: number;
}

export class AmortizationSchedule implements IAmortizationSchedule {
  constructor(
    public prepaymentEntryId?: number,
    public amortizationEntryId?: number,
    public prepaymentAccount?: string,
    public amortizationAccount?: string,
    public particulars?: string,
    public prepaymentBalance?: number,
    public amortizationAmount?: number
  ) {}
}
