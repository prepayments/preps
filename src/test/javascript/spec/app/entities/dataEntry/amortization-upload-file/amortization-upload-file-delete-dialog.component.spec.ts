/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PrepsTestModule } from '../../../../test.module';
import { AmortizationUploadFileDeleteDialogComponent } from 'app/entities/dataEntry/amortization-upload-file/amortization-upload-file-delete-dialog.component';
import { AmortizationUploadFileService } from 'app/entities/dataEntry/amortization-upload-file/amortization-upload-file.service';

describe('Component Tests', () => {
  describe('AmortizationUploadFile Management Delete Component', () => {
    let comp: AmortizationUploadFileDeleteDialogComponent;
    let fixture: ComponentFixture<AmortizationUploadFileDeleteDialogComponent>;
    let service: AmortizationUploadFileService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PrepsTestModule],
        declarations: [AmortizationUploadFileDeleteDialogComponent]
      })
        .overrideTemplate(AmortizationUploadFileDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AmortizationUploadFileDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AmortizationUploadFileService);
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
