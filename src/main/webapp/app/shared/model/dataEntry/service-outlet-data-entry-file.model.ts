import { Moment } from 'moment';

export interface IServiceOutletDataEntryFile {
  id?: number;
  periodFrom?: Moment;
  periodTo?: Moment;
  uploadSuccessful?: boolean;
  uploadProcessed?: boolean;
  dataEntryFileContentType?: string;
  dataEntryFile?: any;
}

export class ServiceOutletDataEntryFile implements IServiceOutletDataEntryFile {
  constructor(
    public id?: number,
    public periodFrom?: Moment,
    public periodTo?: Moment,
    public uploadSuccessful?: boolean,
    public uploadProcessed?: boolean,
    public dataEntryFileContentType?: string,
    public dataEntryFile?: any
  ) {
    this.uploadSuccessful = this.uploadSuccessful || false;
    this.uploadProcessed = this.uploadProcessed || false;
  }
}
