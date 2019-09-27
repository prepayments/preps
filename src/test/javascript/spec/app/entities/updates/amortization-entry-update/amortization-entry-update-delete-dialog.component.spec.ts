/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PrepsTestModule } from '../../../../test.module';
import { AmortizationEntryUpdateDeleteDialogComponent } from 'app/entities/updates/amortization-entry-update/amortization-entry-update-delete-dialog.component';
import { AmortizationEntryUpdateService } from 'app/entities/updates/amortization-entry-update/amortization-entry-update.service';

describe('Component Tests', () => {
  describe('AmortizationEntryUpdate Management Delete Component', () => {
    let comp: AmortizationEntryUpdateDeleteDialogComponent;
    let fixture: ComponentFixture<AmortizationEntryUpdateDeleteDialogComponent>;
    let service: AmortizationEntryUpdateService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PrepsTestModule],
        declarations: [AmortizationEntryUpdateDeleteDialogComponent]
      })
        .overrideTemplate(AmortizationEntryUpdateDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AmortizationEntryUpdateDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AmortizationEntryUpdateService);
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
