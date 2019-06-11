import { Moment } from 'moment';

export interface ISupplierDataEntryFile {
  id?: number;
  periodFrom?: Moment;
  periodTo?: Moment;
  dataEntryFileContentType?: string;
  dataEntryFile?: any;
  uploadSuccessful?: boolean;
  uploadProcessed?: boolean;
}

export class SupplierDataEntryFile implements ISupplierDataEntryFile {
  constructor(
    public id?: number,
    public periodFrom?: Moment,
    public periodTo?: Moment,
    public dataEntryFileContentType?: string,
    public dataEntryFile?: any,
    public uploadSuccessful?: boolean,
    public uploadProcessed?: boolean
  ) {
    this.uploadSuccessful = this.uploadSuccessful || false;
    this.uploadProcessed = this.uploadProcessed || false;
  }
}
