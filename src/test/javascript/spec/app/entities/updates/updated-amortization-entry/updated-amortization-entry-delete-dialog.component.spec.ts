/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PrepsTestModule } from '../../../../test.module';
import { UpdatedAmortizationEntryDeleteDialogComponent } from 'app/entities/updates/updated-amortization-entry/updated-amortization-entry-delete-dialog.component';
import { UpdatedAmortizationEntryService } from 'app/entities/updates/updated-amortization-entry/updated-amortization-entry.service';

describe('Component Tests', () => {
  describe('UpdatedAmortizationEntry Management Delete Component', () => {
    let comp: UpdatedAmortizationEntryDeleteDialogComponent;
    let fixture: ComponentFixture<UpdatedAmortizationEntryDeleteDialogComponent>;
    let service: UpdatedAmortizationEntryService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PrepsTestModule],
        declarations: [UpdatedAmortizationEntryDeleteDialogComponent]
      })
        .overrideTemplate(UpdatedAmortizationEntryDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UpdatedAmortizationEntryDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UpdatedAmortizationEntryService);
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
