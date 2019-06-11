/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PrepsTestModule } from '../../../../test.module';
import { AmortizationEntryDeleteDialogComponent } from 'app/entities/prepayments/amortization-entry/amortization-entry-delete-dialog.component';
import { AmortizationEntryService } from 'app/entities/prepayments/amortization-entry/amortization-entry.service';

describe('Component Tests', () => {
  describe('AmortizationEntry Management Delete Component', () => {
    let comp: AmortizationEntryDeleteDialogComponent;
    let fixture: ComponentFixture<AmortizationEntryDeleteDialogComponent>;
    let service: AmortizationEntryService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PrepsTestModule],
        declarations: [AmortizationEntryDeleteDialogComponent]
      })
        .overrideTemplate(AmortizationEntryDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AmortizationEntryDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AmortizationEntryService);
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
