import { Moment } from 'moment';

export interface IReportRequestEvent {
  id?: number;
  reportRequestDate?: Moment;
  requestedBy?: string;
  reportFileContentType?: string;
  reportFile?: any;
  reportTypeId?: number;
}

export class ReportRequestEvent implements IReportRequestEvent {
  constructor(
    public id?: number,
    public reportRequestDate?: Moment,
    public requestedBy?: string,
    public reportFileContentType?: string,
    public reportFile?: any,
    public reportTypeId?: number
  ) {}
}
