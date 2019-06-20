import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IServiceOutlet, ServiceOutlet } from 'app/shared/model/prepayments/service-outlet.model';
import { ServiceOutletService } from 'app/entities/prepayments/service-outlet';

@Component({
  selector: 'gha-service-outlet-update',
  templateUrl: './service-outlet-update.component.html'
})
export class ServiceOutletUpdateComponent implements OnInit {
  serviceOutlet: IServiceOutlet;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    serviceOutletName: [null, [Validators.required]],
    serviceOutletCode: [null, [Validators.required, Validators.pattern('^[0-9]{3}$')]],
    serviceOutletLocation: [],
    serviceOutletManager: [],
    numberOfStaff: [],
    building: [],
    floor: [],
    postalAddress: [],
    contactPersonName: [],
    contactEmail: [],
    street: []
  });

  constructor(protected serviceOutletService: ServiceOutletService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ serviceOutlet }) => {
      this.updateForm(serviceOutlet);
      this.serviceOutlet = serviceOutlet;
    });
  }

  updateForm(serviceOutlet: IServiceOutlet) {
    this.editForm.patchValue({
      id: serviceOutlet.id,
      serviceOutletName: serviceOutlet.serviceOutletName,
      serviceOutletCode: serviceOutlet.serviceOutletCode,
      serviceOutletLocation: serviceOutlet.serviceOutletLocation,
      serviceOutletManager: serviceOutlet.serviceOutletManager,
      numberOfStaff: serviceOutlet.numberOfStaff,
      building: serviceOutlet.building,
      floor: serviceOutlet.floor,
      postalAddress: serviceOutlet.postalAddress,
      contactPersonName: serviceOutlet.contactPersonName,
      contactEmail: serviceOutlet.contactEmail,
      street: serviceOutlet.street
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const serviceOutlet = this.createFromForm();
    if (serviceOutlet.id !== undefined) {
      this.subscribeToSaveResponse(this.serviceOutletService.update(serviceOutlet));
    } else {
      this.subscribeToSaveResponse(this.serviceOutletService.create(serviceOutlet));
    }
  }

  private createFromForm(): IServiceOutlet {
    const entity = {
      ...new ServiceOutlet(),
      id: this.editForm.get(['id']).value,
      serviceOutletName: this.editForm.get(['serviceOutletName']).value,
      serviceOutletCode: this.editForm.get(['serviceOutletCode']).value,
      serviceOutletLocation: this.editForm.get(['serviceOutletLocation']).value,
      serviceOutletManager: this.editForm.get(['serviceOutletManager']).value,
      numberOfStaff: this.editForm.get(['numberOfStaff']).value,
      building: this.editForm.get(['building']).value,
      floor: this.editForm.get(['floor']).value,
      postalAddress: this.editForm.get(['postalAddress']).value,
      contactPersonName: this.editForm.get(['contactPersonName']).value,
      contactEmail: this.editForm.get(['contactEmail']).value,
      street: this.editForm.get(['street']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IServiceOutlet>>) {
    result.subscribe((res: HttpResponse<IServiceOutlet>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
