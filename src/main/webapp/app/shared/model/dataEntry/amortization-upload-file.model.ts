import { Moment } from 'moment';

export interface IAmortizationUploadFile {
  id?: number;
  periodFrom?: Moment;
  periodTo?: Moment;
  dataEntryFileContentType?: string;
  dataEntryFile?: any;
  uploadSuccessful?: boolean;
  uploadProcessed?: boolean;
  entriesCount?: number;
  fileToken?: string;
}

export class AmortizationUploadFile implements IAmortizationUploadFile {
  constructor(
    public id?: number,
    public periodFrom?: Moment,
    public periodTo?: Moment,
    public dataEntryFileContentType?: string,
    public dataEntryFile?: any,
    public uploadSuccessful?: boolean,
    public uploadProcessed?: boolean,
    public entriesCount?: number,
    public fileToken?: string
  ) {
    this.uploadSuccessful = this.uploadSuccessful || false;
    this.uploadProcessed = this.uploadProcessed || false;
  }
}
