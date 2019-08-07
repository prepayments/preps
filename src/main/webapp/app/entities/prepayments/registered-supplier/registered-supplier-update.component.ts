import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IRegisteredSupplier, RegisteredSupplier } from 'app/shared/model/prepayments/registered-supplier.model';
import { RegisteredSupplierService } from './registered-supplier.service';

@Component({
  selector: 'gha-registered-supplier-update',
  templateUrl: './registered-supplier-update.component.html'
})
export class RegisteredSupplierUpdateComponent implements OnInit {
  registeredSupplier: IRegisteredSupplier;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    supplierName: [null, [Validators.required]],
    supplierAddress: [],
    phoneNumber: [],
    supplierEmail: [],
    bankAccountName: [],
    bankAccountNumber: [],
    supplierBankName: [],
    supplierBankBranch: [],
    bankSwiftCode: [],
    bankPhysicalAddress: [],
    domicile: [],
    taxAuthorityPIN: [],
    OriginatingFileToken: []
  });

  constructor(
    protected registeredSupplierService: RegisteredSupplierService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ registeredSupplier }) => {
      this.updateForm(registeredSupplier);
      this.registeredSupplier = registeredSupplier;
    });
  }

  updateForm(registeredSupplier: IRegisteredSupplier) {
    this.editForm.patchValue({
      id: registeredSupplier.id,
      supplierName: registeredSupplier.supplierName,
      supplierAddress: registeredSupplier.supplierAddress,
      phoneNumber: registeredSupplier.phoneNumber,
      supplierEmail: registeredSupplier.supplierEmail,
      bankAccountName: registeredSupplier.bankAccountName,
      bankAccountNumber: registeredSupplier.bankAccountNumber,
      supplierBankName: registeredSupplier.supplierBankName,
      supplierBankBranch: registeredSupplier.supplierBankBranch,
      bankSwiftCode: registeredSupplier.bankSwiftCode,
      bankPhysicalAddress: registeredSupplier.bankPhysicalAddress,
      domicile: registeredSupplier.domicile,
      taxAuthorityPIN: registeredSupplier.taxAuthorityPIN,
      OriginatingFileToken: registeredSupplier.OriginatingFileToken
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const registeredSupplier = this.createFromForm();
    if (registeredSupplier.id !== undefined) {
      this.subscribeToSaveResponse(this.registeredSupplierService.update(registeredSupplier));
    } else {
      this.subscribeToSaveResponse(this.registeredSupplierService.create(registeredSupplier));
    }
  }

  private createFromForm(): IRegisteredSupplier {
    const entity = {
      ...new RegisteredSupplier(),
      id: this.editForm.get(['id']).value,
      supplierName: this.editForm.get(['supplierName']).value,
      supplierAddress: this.editForm.get(['supplierAddress']).value,
      phoneNumber: this.editForm.get(['phoneNumber']).value,
      supplierEmail: this.editForm.get(['supplierEmail']).value,
      bankAccountName: this.editForm.get(['bankAccountName']).value,
      bankAccountNumber: this.editForm.get(['bankAccountNumber']).value,
      supplierBankName: this.editForm.get(['supplierBankName']).value,
      supplierBankBranch: this.editForm.get(['supplierBankBranch']).value,
      bankSwiftCode: this.editForm.get(['bankSwiftCode']).value,
      bankPhysicalAddress: this.editForm.get(['bankPhysicalAddress']).value,
      domicile: this.editForm.get(['domicile']).value,
      taxAuthorityPIN: this.editForm.get(['taxAuthorityPIN']).value,
      OriginatingFileToken: this.editForm.get(['OriginatingFileToken']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRegisteredSupplier>>) {
    result.subscribe((res: HttpResponse<IRegisteredSupplier>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
