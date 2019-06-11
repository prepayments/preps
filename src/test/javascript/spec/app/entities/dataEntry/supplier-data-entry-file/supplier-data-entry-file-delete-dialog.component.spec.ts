/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PrepsTestModule } from '../../../../test.module';
import { SupplierDataEntryFileDeleteDialogComponent } from 'app/entities/dataEntry/supplier-data-entry-file/supplier-data-entry-file-delete-dialog.component';
import { SupplierDataEntryFileService } from 'app/entities/dataEntry/supplier-data-entry-file/supplier-data-entry-file.service';

describe('Component Tests', () => {
  describe('SupplierDataEntryFile Management Delete Component', () => {
    let comp: SupplierDataEntryFileDeleteDialogComponent;
    let fixture: ComponentFixture<SupplierDataEntryFileDeleteDialogComponent>;
    let service: SupplierDataEntryFileService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PrepsTestModule],
        declarations: [SupplierDataEntryFileDeleteDialogComponent]
      })
        .overrideTemplate(SupplierDataEntryFileDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SupplierDataEntryFileDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SupplierDataEntryFileService);
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
