import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IPrepaymentDataEntryFile, PrepaymentDataEntryFile } from 'app/shared/model/dataEntry/prepayment-data-entry-file.model';
import { PrepaymentDataEntryFileService } from './prepayment-data-entry-file.service';

@Component({
  selector: 'gha-prepayment-data-entry-file-update',
  templateUrl: './prepayment-data-entry-file-update.component.html'
})
export class PrepaymentDataEntryFileUpdateComponent implements OnInit {
  prepaymentDataEntryFile: IPrepaymentDataEntryFile;
  isSaving: boolean;
  periodFromDp: any;
  periodToDp: any;

  editForm = this.fb.group({
    id: [],
    periodFrom: [null, [Validators.required]],
    periodTo: [null, [Validators.required]],
    dataEntryFile: [null, [Validators.required]],
    dataEntryFileContentType: [],
    uploadProcessed: [],
    uploadSuccessful: [],
    entriesCount: [],
    fileToken: [null, []]
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected prepaymentDataEntryFileService: PrepaymentDataEntryFileService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ prepaymentDataEntryFile }) => {
      this.updateForm(prepaymentDataEntryFile);
      this.prepaymentDataEntryFile = prepaymentDataEntryFile;
    });
  }

  updateForm(prepaymentDataEntryFile: IPrepaymentDataEntryFile) {
    this.editForm.patchValue({
      id: prepaymentDataEntryFile.id,
      periodFrom: prepaymentDataEntryFile.periodFrom,
      periodTo: prepaymentDataEntryFile.periodTo,
      dataEntryFile: prepaymentDataEntryFile.dataEntryFile,
      dataEntryFileContentType: prepaymentDataEntryFile.dataEntryFileContentType,
      uploadProcessed: prepaymentDataEntryFile.uploadProcessed,
      uploadSuccessful: prepaymentDataEntryFile.uploadSuccessful,
      entriesCount: prepaymentDataEntryFile.entriesCount,
      fileToken: prepaymentDataEntryFile.fileToken
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
    const prepaymentDataEntryFile = this.createFromForm();
    if (prepaymentDataEntryFile.id !== undefined) {
      this.subscribeToSaveResponse(this.prepaymentDataEntryFileService.update(prepaymentDataEntryFile));
    } else {
      this.subscribeToSaveResponse(this.prepaymentDataEntryFileService.create(prepaymentDataEntryFile));
    }
  }

  private createFromForm(): IPrepaymentDataEntryFile {
    const entity = {
      ...new PrepaymentDataEntryFile(),
      id: this.editForm.get(['id']).value,
      periodFrom: this.editForm.get(['periodFrom']).value,
      periodTo: this.editForm.get(['periodTo']).value,
      dataEntryFileContentType: this.editForm.get(['dataEntryFileContentType']).value,
      dataEntryFile: this.editForm.get(['dataEntryFile']).value,
      uploadProcessed: this.editForm.get(['uploadProcessed']).value,
      uploadSuccessful: this.editForm.get(['uploadSuccessful']).value,
      entriesCount: this.editForm.get(['entriesCount']).value,
      fileToken: this.editForm.get(['fileToken']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPrepaymentDataEntryFile>>) {
    result.subscribe((res: HttpResponse<IPrepaymentDataEntryFile>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
