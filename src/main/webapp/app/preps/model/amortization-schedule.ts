export interface IAmortizationSchedule {
  prepaymentAccount?: string;
  amortizationAccount?: string;
  particulars?: string;
  amount?: number;
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
