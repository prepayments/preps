import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IReportRequestEvent, ReportRequestEvent } from 'app/shared/model/reports/report-request-event.model';
import { ReportRequestEventService } from './report-request-event.service';
import { IReportType } from 'app/shared/model/reports/report-type.model';
import { ReportTypeService } from 'app/entities/reports/report-type';

@Component({
  selector: 'gha-report-request-event-update',
  templateUrl: './report-request-event-update.component.html'
})
export class ReportRequestEventUpdateComponent implements OnInit {
  reportRequestEvent: IReportRequestEvent;
  isSaving: boolean;

  reporttypes: IReportType[];
  reportRequestDateDp: any;

  editForm = this.fb.group({
    id: [],
    reportRequestDate: [],
    requestedBy: [],
    reportFile: [],
    reportFileContentType: [],
    reportTypeId: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected reportRequestEventService: ReportRequestEventService,
    protected reportTypeService: ReportTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ reportRequestEvent }) => {
      this.updateForm(reportRequestEvent);
      this.reportRequestEvent = reportRequestEvent;
    });
    this.reportTypeService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IReportType[]>) => mayBeOk.ok),
        map((response: HttpResponse<IReportType[]>) => response.body)
      )
      .subscribe((res: IReportType[]) => (this.reporttypes = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(reportRequestEvent: IReportRequestEvent) {
    this.editForm.patchValue({
      id: reportRequestEvent.id,
      reportRequestDate: reportRequestEvent.reportRequestDate,
      requestedBy: reportRequestEvent.requestedBy,
      reportFile: reportRequestEvent.reportFile,
      reportFileContentType: reportRequestEvent.reportFileContentType,
      reportTypeId: reportRequestEvent.reportTypeId
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
    const reportRequestEvent = this.createFromForm();
    if (reportRequestEvent.id !== undefined) {
      this.subscribeToSaveResponse(this.reportRequestEventService.update(reportRequestEvent));
    } else {
      this.subscribeToSaveResponse(this.reportRequestEventService.create(reportRequestEvent));
    }
  }

  private createFromForm(): IReportRequestEvent {
    const entity = {
      ...new ReportRequestEvent(),
      id: this.editForm.get(['id']).value,
      reportRequestDate: this.editForm.get(['reportRequestDate']).value,
      requestedBy: this.editForm.get(['requestedBy']).value,
      reportFileContentType: this.editForm.get(['reportFileContentType']).value,
      reportFile: this.editForm.get(['reportFile']).value,
      reportTypeId: this.editForm.get(['reportTypeId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IReportRequestEvent>>) {
    result.subscribe((res: HttpResponse<IReportRequestEvent>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackReportTypeById(index: number, item: IReportType) {
    return item.id;
  }
}
