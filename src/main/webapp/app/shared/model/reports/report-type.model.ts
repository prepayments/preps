export const enum ReportMediumTypes {
  EXCEL = 'EXCEL',
  PDF = 'PDF',
  POWERPOINT = 'POWERPOINT',
  DOC = 'DOC',
  TEXT = 'TEXT',
  JSON = 'JSON',
  HTML5 = 'HTML5'
}

export interface IReportType {
  id?: number;
  reportModelName?: string;
  reportMediumType?: ReportMediumTypes;
  reportPassword?: string;
}

export class ReportType implements IReportType {
  constructor(
    public id?: number,
    public reportModelName?: string,
    public reportMediumType?: ReportMediumTypes,
    public reportPassword?: string
  ) {}
}
