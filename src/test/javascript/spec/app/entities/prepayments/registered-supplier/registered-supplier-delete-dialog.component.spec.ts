/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PrepsTestModule } from '../../../../test.module';
import { RegisteredSupplierDeleteDialogComponent } from 'app/entities/prepayments/registered-supplier/registered-supplier-delete-dialog.component';
import { RegisteredSupplierService } from 'app/entities/prepayments/registered-supplier/registered-supplier.service';

describe('Component Tests', () => {
  describe('RegisteredSupplier Management Delete Component', () => {
    let comp: RegisteredSupplierDeleteDialogComponent;
    let fixture: ComponentFixture<RegisteredSupplierDeleteDialogComponent>;
    let service: RegisteredSupplierService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PrepsTestModule],
        declarations: [RegisteredSupplierDeleteDialogComponent]
      })
        .overrideTemplate(RegisteredSupplierDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RegisteredSupplierDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RegisteredSupplierService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
