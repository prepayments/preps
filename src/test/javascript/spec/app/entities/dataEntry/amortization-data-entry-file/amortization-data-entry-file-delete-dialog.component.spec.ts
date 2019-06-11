/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PrepsTestModule } from '../../../../test.module';
import { AmortizationDataEntryFileDeleteDialogComponent } from 'app/entities/dataEntry/amortization-data-entry-file/amortization-data-entry-file-delete-dialog.component';
import { AmortizationDataEntryFileService } from 'app/entities/dataEntry/amortization-data-entry-file/amortization-data-entry-file.service';

describe('Component Tests', () => {
  describe('AmortizationDataEntryFile Management Delete Component', () => {
    let comp: AmortizationDataEntryFileDeleteDialogComponent;
    let fixture: ComponentFixture<AmortizationDataEntryFileDeleteDialogComponent>;
    let service: AmortizationDataEntryFileService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PrepsTestModule],
        declarations: [AmortizationDataEntryFileDeleteDialogComponent]
      })
        .overrideTemplate(AmortizationDataEntryFileDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AmortizationDataEntryFileDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AmortizationDataEntryFileService);
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
