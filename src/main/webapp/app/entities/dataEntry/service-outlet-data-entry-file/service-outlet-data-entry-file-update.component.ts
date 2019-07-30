import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IServiceOutletDataEntryFile, ServiceOutletDataEntryFile } from 'app/shared/model/dataEntry/service-outlet-data-entry-file.model';
import { ServiceOutletDataEntryFileService } from './service-outlet-data-entry-file.service';

@Component({
  selector: 'gha-service-outlet-data-entry-file-update',
  templateUrl: './service-outlet-data-entry-file-update.component.html'
})
export class ServiceOutletDataEntryFileUpdateComponent implements OnInit {
  serviceOutletDataEntryFile: IServiceOutletDataEntryFile;
  isSaving: boolean;
  periodFromDp: any;
  periodToDp: any;

  editForm = this.fb.group({
    id: [],
    periodFrom: [null, [Validators.required]],
    periodTo: [null, [Validators.required]],
    uploadSuccessful: [],
    uploadProcessed: [],
    dataEntryFile: [null, [Validators.required]],
    dataEntryFileContentType: [],
    entriesCount: [],
    fileToken: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected serviceOutletDataEntryFileService: ServiceOutletDataEntryFileService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ serviceOutletDataEntryFile }) => {
      this.updateForm(serviceOutletDataEntryFile);
      this.serviceOutletDataEntryFile = serviceOutletDataEntryFile;
    });
  }

  updateForm(serviceOutletDataEntryFile: IServiceOutletDataEntryFile) {
    this.editForm.patchValue({
      id: serviceOutletDataEntryFile.id,
      periodFrom: serviceOutletDataEntryFile.periodFrom,
      periodTo: serviceOutletDataEntryFile.periodTo,
      uploadSuccessful: serviceOutletDataEntryFile.uploadSuccessful,
      uploadProcessed: serviceOutletDataEntryFile.uploadProcessed,
      dataEntryFile: serviceOutletDataEntryFile.dataEntryFile,
      dataEntryFileContentType: serviceOutletDataEntryFile.dataEntryFileContentType,
      entriesCount: serviceOutletDataEntryFile.entriesCount,
      fileToken: serviceOutletDataEntryFile.fileToken
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
    const serviceOutletDataEntryFile = this.createFromForm();
    if (serviceOutletDataEntryFile.id !== undefined) {
      this.subscribeToSaveResponse(this.serviceOutletDataEntryFileService.update(serviceOutletDataEntryFile));
    } else {
      this.subscribeToSaveResponse(this.serviceOutletDataEntryFileService.create(serviceOutletDataEntryFile));
    }
  }

  private createFromForm(): IServiceOutletDataEntryFile {
    const entity = {
      ...new ServiceOutletDataEntryFile(),
      id: this.editForm.get(['id']).value,
      periodFrom: this.editForm.get(['periodFrom']).value,
      periodTo: this.editForm.get(['periodTo']).value,
      uploadSuccessful: this.editForm.get(['uploadSuccessful']).value,
      uploadProcessed: this.editForm.get(['uploadProcessed']).value,
      dataEntryFileContentType: this.editForm.get(['dataEntryFileContentType']).value,
      dataEntryFile: this.editForm.get(['dataEntryFile']).value,
      entriesCount: this.editForm.get(['entriesCount']).value,
      fileToken: this.editForm.get(['fileToken']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IServiceOutletDataEntryFile>>) {
    result.subscribe(
      (res: HttpResponse<IServiceOutletDataEntryFile>) => this.onSaveSuccess(),
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
