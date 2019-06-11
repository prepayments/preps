import { Moment } from 'moment';

export interface IAmortizationEntryReport {
    reportTitle?: string;
    reportType?: any;
    reportFileContentType?: string;
    reportFile?: any;
}

export class AmortizationEntryReport implements IAmortizationEntryReport {
    constructor(public reportTitle?: string, public reportType?: any, public reportFileContentType?: string, public reportFile?: any) {}
}
