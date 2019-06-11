/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PrepsTestModule } from '../../../../test.module';
import { ServiceOutletDataEntryFileDetailComponent } from 'app/entities/dataEntry/service-outlet-data-entry-file/service-outlet-data-entry-file-detail.component';
import { ServiceOutletDataEntryFile } from 'app/shared/model/dataEntry/service-outlet-data-entry-file.model';

describe('Component Tests', () => {
  describe('ServiceOutletDataEntryFile Management Detail Component', () => {
    let comp: ServiceOutletDataEntryFileDetailComponent;
    let fixture: ComponentFixture<ServiceOutletDataEntryFileDetailComponent>;
    const route = ({ data: of({ serviceOutletDataEntryFile: new ServiceOutletDataEntryFile(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PrepsTestModule],
        declarations: [ServiceOutletDataEntryFileDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ServiceOutletDataEntryFileDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ServiceOutletDataEntryFileDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.serviceOutletDataEntryFile).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
