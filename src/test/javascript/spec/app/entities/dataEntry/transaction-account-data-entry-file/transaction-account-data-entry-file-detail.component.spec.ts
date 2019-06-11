/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PrepsTestModule } from '../../../../test.module';
import { TransactionAccountDataEntryFileDetailComponent } from 'app/entities/dataEntry/transaction-account-data-entry-file/transaction-account-data-entry-file-detail.component';
import { TransactionAccountDataEntryFile } from 'app/shared/model/dataEntry/transaction-account-data-entry-file.model';

describe('Component Tests', () => {
  describe('TransactionAccountDataEntryFile Management Detail Component', () => {
    let comp: TransactionAccountDataEntryFileDetailComponent;
    let fixture: ComponentFixture<TransactionAccountDataEntryFileDetailComponent>;
    const route = ({ data: of({ transactionAccountDataEntryFile: new TransactionAccountDataEntryFile(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PrepsTestModule],
        declarations: [TransactionAccountDataEntryFileDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TransactionAccountDataEntryFileDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TransactionAccountDataEntryFileDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.transactionAccountDataEntryFile).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
