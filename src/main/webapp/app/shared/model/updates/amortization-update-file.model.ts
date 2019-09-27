export interface IAmortizationUpdateFile {
  id?: number;
  Narration?: string;
  dataEntryFileContentType?: string;
  dataEntryFile?: any;
  uploadSuccessful?: boolean;
  uploadProcessed?: boolean;
  entriesCount?: number;
  fileToken?: string;
  reasonForUpdate?: string;
}

export class AmortizationUpdateFile implements IAmortizationUpdateFile {
  constructor(
    public id?: number,
    public Narration?: string,
    public dataEntryFileContentType?: string,
    public dataEntryFile?: any,
    public uploadSuccessful?: boolean,
    public uploadProcessed?: boolean,
    public entriesCount?: number,
    public fileToken?: string,
    public reasonForUpdate?: string
  ) {
    this.uploadSuccessful = this.uploadSuccessful || false;
    this.uploadProcessed = this.uploadProcessed || false;
  }
}
