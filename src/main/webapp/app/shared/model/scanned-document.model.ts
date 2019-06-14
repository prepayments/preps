import { Moment } from 'moment';

export interface IScannedDocument {
  id?: number;
  documentName?: string;
  description?: string;
  invoiceNumber?: string;
  transactionId?: string;
  transactionDate?: Moment;
  invoiceDocumentContentType?: string;
  invoiceDocument?: any;
  requisitionDocumentContentType?: string;
  requisitionDocument?: any;
  approvalMemoDocumentContentType?: string;
  approvalMemoDocument?: any;
  otherScannedDocumentContentType?: string;
  otherScannedDocument?: any;
}

export class ScannedDocument implements IScannedDocument {
  constructor(
    public id?: number,
    public documentName?: string,
    public description?: string,
    public invoiceNumber?: string,
    public transactionId?: string,
    public transactionDate?: Moment,
    public invoiceDocumentContentType?: string,
    public invoiceDocument?: any,
    public requisitionDocumentContentType?: string,
    public requisitionDocument?: any,
    public approvalMemoDocumentContentType?: string,
    public approvalMemoDocument?: any,
    public otherScannedDocumentContentType?: string,
    public otherScannedDocument?: any
  ) {}
}
