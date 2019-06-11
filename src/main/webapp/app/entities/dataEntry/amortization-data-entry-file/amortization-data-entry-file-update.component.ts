import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IAmortizationDataEntryFile, AmortizationDataEntryFile } from 'app/shared/model/dataEntry/amortization-data-entry-file.model';
import { AmortizationDataEntryFileService } from './amortization-data-entry-file.service';

@Component({
  selector: 'gha-amortization-data-entry-file-update',
  templateUrl: './amortization-data-entry-file-update.component.html'
})
export class AmortizationDataEntryFileUpdateComponent implements OnInit {
  amortizationDataEntryFile: IAmortizationDataEntryFile;
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
    uploadProcessed: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected amortizationDataEntryFileService: AmortizationDataEntryFileService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ amortizationDataEntryFile }) => {
      this.updateForm(amortizationDataEntryFile);
      this.amortizationDataEntryFile = amortizationDataEntryFile;
    });
  }

  updateForm(amortizationDataEntryFile: IAmortizationDataEntryFile) {
    this.editForm.patchValue({
      id: amortizationDataEntryFile.id,
      periodFrom: amortizationDataEntryFile.periodFrom,
      periodTo: amortizationDataEntryFile.periodTo,
      dataEntryFile: amortizationDataEntryFile.dataEntryFile,
      dataEntryFileContentType: amortizationDataEntryFile.dataEntryFileContentType,
      uploadSuccessful: amortizationDataEntryFile.uploadSuccessful,
      uploadProcessed: amortizationDataEntryFile.uploadProcessed
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
    const amortizationDataEntryFile = this.createFromForm();
    if (amortizationDataEntryFile.id !== undefined) {
      this.subscribeToSaveResponse(this.amortizationDataEntryFileService.update(amortizationDataEntryFile));
    } else {
      this.subscribeToSaveResponse(this.amortizationDataEntryFileService.create(amortizationDataEntryFile));
    }
  }

  private createFromForm(): IAmortizationDataEntryFile {
    const entity = {
      ...new AmortizationDataEntryFile(),
      id: this.editForm.get(['id']).value,
      periodFrom: this.editForm.get(['periodFrom']).value,
      periodTo: this.editForm.get(['periodTo']).value,
      dataEntryFileContentType: this.editForm.get(['dataEntryFileContentType']).value,
      dataEntryFile: this.editForm.get(['dataEntryFile']).value,
      uploadSuccessful: this.editForm.get(['uploadSuccessful']).value,
      uploadProcessed: this.editForm.get(['uploadProcessed']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAmortizationDataEntryFile>>) {
    result.subscribe(
      (res: HttpResponse<IAmortizationDataEntryFile>) => this.onSaveSuccess(),
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
