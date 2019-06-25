/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PrepsTestModule } from '../../../../test.module';
import { AmortizationUploadDeleteDialogComponent } from 'app/entities/dataEntry/amortization-upload/amortization-upload-delete-dialog.component';
import { AmortizationUploadService } from 'app/entities/dataEntry/amortization-upload/amortization-upload.service';

describe('Component Tests', () => {
  describe('AmortizationUpload Management Delete Component', () => {
    let comp: AmortizationUploadDeleteDialogComponent;
    let fixture: ComponentFixture<AmortizationUploadDeleteDialogComponent>;
    let service: AmortizationUploadService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PrepsTestModule],
        declarations: [AmortizationUploadDeleteDialogComponent]
      })
        .overrideTemplate(AmortizationUploadDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AmortizationUploadDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AmortizationUploadService);
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
