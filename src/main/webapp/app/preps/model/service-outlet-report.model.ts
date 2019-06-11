export interface IServiceOutletReport {
    reportTitle?: string;
    reportType?: any;
    reportFileContentType?: string;
    reportFile?: any;
}

export class AmortizationEntryReport implements IServiceOutletReport {
    constructor(public reportTitle?: string, public reportType?: any, public reportFileContentType?: string, public reportFile?: any) {}
}
