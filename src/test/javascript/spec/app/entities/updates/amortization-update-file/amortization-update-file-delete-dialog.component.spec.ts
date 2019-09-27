/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PrepsTestModule } from '../../../../test.module';
import { AmortizationUpdateFileDeleteDialogComponent } from 'app/entities/updates/amortization-update-file/amortization-update-file-delete-dialog.component';
import { AmortizationUpdateFileService } from 'app/entities/updates/amortization-update-file/amortization-update-file.service';

describe('Component Tests', () => {
  describe('AmortizationUpdateFile Management Delete Component', () => {
    let comp: AmortizationUpdateFileDeleteDialogComponent;
    let fixture: ComponentFixture<AmortizationUpdateFileDeleteDialogComponent>;
    let service: AmortizationUpdateFileService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PrepsTestModule],
        declarations: [AmortizationUpdateFileDeleteDialogComponent]
      })
        .overrideTemplate(AmortizationUpdateFileDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AmortizationUpdateFileDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AmortizationUpdateFileService);
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
