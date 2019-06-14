import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IScannedDocument, ScannedDocument } from 'app/shared/model/scanned-document.model';
import { ScannedDocumentService } from './scanned-document.service';

@Component({
  selector: 'gha-scanned-document-update',
  templateUrl: './scanned-document-update.component.html'
})
export class ScannedDocumentUpdateComponent implements OnInit {
  scannedDocument: IScannedDocument;
  isSaving: boolean;
  transactionDateDp: any;

  editForm = this.fb.group({
    id: [],
    documentName: [null, [Validators.required]],
    description: [],
    invoiceNumber: [],
    transactionId: [],
    transactionDate: [],
    invoiceDocument: [],
    invoiceDocumentContentType: [],
    requisitionDocument: [],
    requisitionDocumentContentType: [],
    approvalMemoDocument: [],
    approvalMemoDocumentContentType: [],
    otherScannedDocument: [],
    otherScannedDocumentContentType: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected scannedDocumentService: ScannedDocumentService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ scannedDocument }) => {
      this.updateForm(scannedDocument);
      this.scannedDocument = scannedDocument;
    });
  }

  updateForm(scannedDocument: IScannedDocument) {
    this.editForm.patchValue({
      id: scannedDocument.id,
      documentName: scannedDocument.documentName,
      description: scannedDocument.description,
      invoiceNumber: scannedDocument.invoiceNumber,
      transactionId: scannedDocument.transactionId,
      transactionDate: scannedDocument.transactionDate,
      invoiceDocument: scannedDocument.invoiceDocument,
      invoiceDocumentContentType: scannedDocument.invoiceDocumentContentType,
      requisitionDocument: scannedDocument.requisitionDocument,
      requisitionDocumentContentType: scannedDocument.requisitionDocumentContentType,
      approvalMemoDocument: scannedDocument.approvalMemoDocument,
      approvalMemoDocumentContentType: scannedDocument.approvalMemoDocumentContentType,
      otherScannedDocument: scannedDocument.otherScannedDocument,
      otherScannedDocumentContentType: scannedDocument.otherScannedDocumentContentType
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  setFileData(event, field: string, isImage) {
    return new Promise((resolve, reject) => {
      if (event && event.target && event.target.files && event.target.files[0]) {
        const file = event.target.files[0];
        if (isImage && !/^image\//.test(file.type)) {
          reject(`File was expected to be an image but was found to be ${file.type}`);
        } else {
          const filedContentType: string = field + 'ContentType';
          this.dataUtils.toBase64(file, base64Data => {
            this.editForm.patchValue({
              [field]: base64Data,
              [filedContentType]: file.type
            });
          });
        }
      } else {
        reject(`Base64 data was not set as file could not be extracted from passed parameter: ${event}`);
      }
    }).then(
      () => console.log('blob added'), // sucess
      this.onError
    );
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const scannedDocument = this.createFromForm();
    if (scannedDocument.id !== undefined) {
      this.subscribeToSaveResponse(this.scannedDocumentService.update(scannedDocument));
    } else {
      this.subscribeToSaveResponse(this.scannedDocumentService.create(scannedDocument));
    }
  }

  private createFromForm(): IScannedDocument {
    const entity = {
      ...new ScannedDocument(),
      id: this.editForm.get(['id']).value,
      documentName: this.editForm.get(['documentName']).value,
      description: this.editForm.get(['description']).value,
      invoiceNumber: this.editForm.get(['invoiceNumber']).value,
      transactionId: this.editForm.get(['transactionId']).value,
      transactionDate: this.editForm.get(['transactionDate']).value,
      invoiceDocumentContentType: this.editForm.get(['invoiceDocumentContentType']).value,
      invoiceDocument: this.editForm.get(['invoiceDocument']).value,
      requisitionDocumentContentType: this.editForm.get(['requisitionDocumentContentType']).value,
      requisitionDocument: this.editForm.get(['requisitionDocument']).value,
      approvalMemoDocumentContentType: this.editForm.get(['approvalMemoDocumentContentType']).value,
      approvalMemoDocument: this.editForm.get(['approvalMemoDocument']).value,
      otherScannedDocumentContentType: this.editForm.get(['otherScannedDocumentContentType']).value,
      otherScannedDocument: this.editForm.get(['otherScannedDocument']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IScannedDocument>>) {
    result.subscribe((res: HttpResponse<IScannedDocument>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
