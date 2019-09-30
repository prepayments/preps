import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IAmortizationUpdateFile, AmortizationUpdateFile } from 'app/shared/model/updates/amortization-update-file.model';
import { AmortizationUpdateFileService } from './amortization-update-file.service';

@Component({
  selector: 'gha-amortization-update-file-update',
  templateUrl: './amortization-update-file-update.component.html'
})
export class AmortizationUpdateFileUpdateComponent implements OnInit {
  amortizationUpdateFile: IAmortizationUpdateFile;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    narration: [],
    dataEntryFile: [null, [Validators.required]],
    dataEntryFileContentType: [],
    uploadSuccessful: [],
    uploadProcessed: [],
    entriesCount: [],
    fileToken: [null, []],
    reasonForUpdate: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected amortizationUpdateFileService: AmortizationUpdateFileService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ amortizationUpdateFile }) => {
      this.updateForm(amortizationUpdateFile);
      this.amortizationUpdateFile = amortizationUpdateFile;
    });
  }

  updateForm(amortizationUpdateFile: IAmortizationUpdateFile) {
    this.editForm.patchValue({
      id: amortizationUpdateFile.id,
      narration: amortizationUpdateFile.narration,
      dataEntryFile: amortizationUpdateFile.dataEntryFile,
      dataEntryFileContentType: amortizationUpdateFile.dataEntryFileContentType,
      uploadSuccessful: amortizationUpdateFile.uploadSuccessful,
      uploadProcessed: amortizationUpdateFile.uploadProcessed,
      entriesCount: amortizationUpdateFile.entriesCount,
      fileToken: amortizationUpdateFile.fileToken,
      reasonForUpdate: amortizationUpdateFile.reasonForUpdate
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
    const amortizationUpdateFile = this.createFromForm();
    if (amortizationUpdateFile.id !== undefined) {
      this.subscribeToSaveResponse(this.amortizationUpdateFileService.update(amortizationUpdateFile));
    } else {
      this.subscribeToSaveResponse(this.amortizationUpdateFileService.create(amortizationUpdateFile));
    }
  }

  private createFromForm(): IAmortizationUpdateFile {
    const entity = {
      ...new AmortizationUpdateFile(),
      id: this.editForm.get(['id']).value,
      narration: this.editForm.get(['narration']).value,
      dataEntryFileContentType: this.editForm.get(['dataEntryFileContentType']).value,
      dataEntryFile: this.editForm.get(['dataEntryFile']).value,
      uploadSuccessful: this.editForm.get(['uploadSuccessful']).value,
      uploadProcessed: this.editForm.get(['uploadProcessed']).value,
      entriesCount: this.editForm.get(['entriesCount']).value,
      fileToken: this.editForm.get(['fileToken']).value,
      reasonForUpdate: this.editForm.get(['reasonForUpdate']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAmortizationUpdateFile>>) {
    result.subscribe((res: HttpResponse<IAmortizationUpdateFile>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
