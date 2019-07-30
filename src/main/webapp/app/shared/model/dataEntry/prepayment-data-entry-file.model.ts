import { Moment } from 'moment';

export interface IPrepaymentDataEntryFile {
  id?: number;
  periodFrom?: Moment;
  periodTo?: Moment;
  dataEntryFileContentType?: string;
  dataEntryFile?: any;
  uploadProcessed?: boolean;
  uploadSuccessful?: boolean;
  entriesCount?: number;
  fileToken?: string;
}

export class PrepaymentDataEntryFile implements IPrepaymentDataEntryFile {
  constructor(
    public id?: number,
    public periodFrom?: Moment,
    public periodTo?: Moment,
    public dataEntryFileContentType?: string,
    public dataEntryFile?: any,
    public uploadProcessed?: boolean,
    public uploadSuccessful?: boolean,
    public entriesCount?: number,
    public fileToken?: string
  ) {
    this.uploadProcessed = this.uploadProcessed || false;
    this.uploadSuccessful = this.uploadSuccessful || false;
  }
}
