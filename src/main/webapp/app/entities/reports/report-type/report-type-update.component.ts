import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IReportType, ReportType } from 'app/shared/model/reports/report-type.model';
import { ReportTypeService } from './report-type.service';

@Component({
  selector: 'gha-report-type-update',
  templateUrl: './report-type-update.component.html'
})
export class ReportTypeUpdateComponent implements OnInit {
  reportType: IReportType;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    reportModelName: [],
    reportMediumType: [],
    reportPassword: []
  });

  constructor(protected reportTypeService: ReportTypeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ reportType }) => {
      this.updateForm(reportType);
      this.reportType = reportType;
    });
  }

  updateForm(reportType: IReportType) {
    this.editForm.patchValue({
      id: reportType.id,
      reportModelName: reportType.reportModelName,
      reportMediumType: reportType.reportMediumType,
      reportPassword: reportType.reportPassword
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const reportType = this.createFromForm();
    if (reportType.id !== undefined) {
      this.subscribeToSaveResponse(this.reportTypeService.update(reportType));
    } else {
      this.subscribeToSaveResponse(this.reportTypeService.create(reportType));
    }
  }

  private createFromForm(): IReportType {
    const entity = {
      ...new ReportType(),
      id: this.editForm.get(['id']).value,
      reportModelName: this.editForm.get(['reportModelName']).value,
      reportMediumType: this.editForm.get(['reportMediumType']).value,
      reportPassword: this.editForm.get(['reportPassword']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IReportType>>) {
    result.subscribe((res: HttpResponse<IReportType>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
