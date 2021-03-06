import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import {
  ITransactionAccountDataEntryFile,
  TransactionAccountDataEntryFile
} from 'app/shared/model/dataEntry/transaction-account-data-entry-file.model';
import { TransactionAccountDataEntryFileService } from './transaction-account-data-entry-file.service';

@Component({
  selector: 'gha-transaction-account-data-entry-file-update',
  templateUrl: './transaction-account-data-entry-file-update.component.html'
})
export class TransactionAccountDataEntryFileUpdateComponent implements OnInit {
  transactionAccountDataEntryFile: ITransactionAccountDataEntryFile;
  isSaving: boolean;
  periodFromDp: any;
  periodToDp: any;

  editForm = this.fb.group({
    id: [],
    periodFrom: [null, [Validators.required]],
    periodTo: [null, [Validators.required]],
    dataEntryFile: [null, [Validators.required]],
    dataEntryFileContentType: [],
    uploadSuccessful: [],
    uploadProcessed: [],
    entriesCount: [],
    fileToken: [null, []]
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected transactionAccountDataEntryFileService: TransactionAccountDataEntryFileService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ transactionAccountDataEntryFile }) => {
      this.updateForm(transactionAccountDataEntryFile);
      this.transactionAccountDataEntryFile = transactionAccountDataEntryFile;
    });
  }

  updateForm(transactionAccountDataEntryFile: ITransactionAccountDataEntryFile) {
    this.editForm.patchValue({
      id: transactionAccountDataEntryFile.id,
      periodFrom: transactionAccountDataEntryFile.periodFrom,
      periodTo: transactionAccountDataEntryFile.periodTo,
      dataEntryFile: transactionAccountDataEntryFile.dataEntryFile,
      dataEntryFileContentType: transactionAccountDataEntryFile.dataEntryFileContentType,
      uploadSuccessful: transactionAccountDataEntryFile.uploadSuccessful,
      uploadProcessed: transactionAccountDataEntryFile.uploadProcessed,
      entriesCount: transactionAccountDataEntryFile.entriesCount,
      fileToken: transactionAccountDataEntryFile.fileToken
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
    const transactionAccountDataEntryFile = this.createFromForm();
    if (transactionAccountDataEntryFile.id !== undefined) {
      this.subscribeToSaveResponse(this.transactionAccountDataEntryFileService.update(transactionAccountDataEntryFile));
    } else {
      this.subscribeToSaveResponse(this.transactionAccountDataEntryFileService.create(transactionAccountDataEntryFile));
    }
  }

  private createFromForm(): ITransactionAccountDataEntryFile {
    const entity = {
      ...new TransactionAccountDataEntryFile(),
      id: this.editForm.get(['id']).value,
      periodFrom: this.editForm.get(['periodFrom']).value,
      periodTo: this.editForm.get(['periodTo']).value,
      dataEntryFileContentType: this.editForm.get(['dataEntryFileContentType']).value,
      dataEntryFile: this.editForm.get(['dataEntryFile']).value,
      uploadSuccessful: this.editForm.get(['uploadSuccessful']).value,
      uploadProcessed: this.editForm.get(['uploadProcessed']).value,
      entriesCount: this.editForm.get(['entriesCount']).value,
      fileToken: this.editForm.get(['fileToken']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITransactionAccountDataEntryFile>>) {
    result.subscribe(
      (res: HttpResponse<ITransactionAccountDataEntryFile>) => this.onSaveSuccess(),
      (res: HttpErrorResponse) => this.onSaveError()
    );
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
