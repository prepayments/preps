export interface IPrepaymentEntryReport {
    reportTitle?: string;
    reportType?: any;
    reportFileContentType?: string;
    reportFile?: any;
}

export class AmortizationEntryReport implements IPrepaymentEntryReport {
    constructor(public reportTitle?: string, public reportType?: any, public reportFileContentType?: string, public reportFile?: any) {}
}
