import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IAmortizationUploadFile, AmortizationUploadFile } from 'app/shared/model/dataEntry/amortization-upload-file.model';
import { AmortizationUploadFileService } from './amortization-upload-file.service';

@Component({
  selector: 'gha-amortization-upload-file-update',
  templateUrl: './amortization-upload-file-update.component.html'
})
export class AmortizationUploadFileUpdateComponent implements OnInit {
  amortizationUploadFile: IAmortizationUploadFile;
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
    protected amortizationUploadFileService: AmortizationUploadFileService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ amortizationUploadFile }) => {
      this.updateForm(amortizationUploadFile);
      this.amortizationUploadFile = amortizationUploadFile;
    });
  }

  updateForm(amortizationUploadFile: IAmortizationUploadFile) {
    this.editForm.patchValue({
      id: amortizationUploadFile.id,
      periodFrom: amortizationUploadFile.periodFrom,
      periodTo: amortizationUploadFile.periodTo,
      dataEntryFile: amortizationUploadFile.dataEntryFile,
      dataEntryFileContentType: amortizationUploadFile.dataEntryFileContentType,
      uploadSuccessful: amortizationUploadFile.uploadSuccessful,
      uploadProcessed: amortizationUploadFile.uploadProcessed,
      entriesCount: amortizationUploadFile.entriesCount,
      fileToken: amortizationUploadFile.fileToken
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
    const amortizationUploadFile = this.createFromForm();
    if (amortizationUploadFile.id !== undefined) {
      this.subscribeToSaveResponse(this.amortizationUploadFileService.update(amortizationUploadFile));
    } else {
      this.subscribeToSaveResponse(this.amortizationUploadFileService.create(amortizationUploadFile));
    }
  }

  private createFromForm(): IAmortizationUploadFile {
    const entity = {
      ...new AmortizationUploadFile(),
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

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAmortizationUploadFile>>) {
    result.subscribe((res: HttpResponse<IAmortizationUploadFile>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
