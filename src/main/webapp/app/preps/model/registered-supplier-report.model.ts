export interface IRegisteredSupplierReport {
    reportTitle?: string;
    reportType?: any;
    reportFileContentType?: string;
    reportFile?: any;
}

export class AmortizationEntryReport implements IRegisteredSupplierReport {
    constructor(public reportTitle?: string, public reportType?: any, public reportFileContentType?: string, public reportFile?: any) {}
}
