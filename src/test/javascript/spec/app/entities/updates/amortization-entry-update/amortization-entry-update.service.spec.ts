/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { AmortizationEntryUpdateService } from 'app/entities/updates/amortization-entry-update/amortization-entry-update.service';
import { IAmortizationEntryUpdate, AmortizationEntryUpdate } from 'app/shared/model/updates/amortization-entry-update.model';

describe('Service Tests', () => {
  describe('AmortizationEntryUpdate Service', () => {
    let injector: TestBed;
    let service: AmortizationEntryUpdateService;
    let httpMock: HttpTestingController;
    let elemDefault: IAmortizationEntryUpdate;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(AmortizationEntryUpdateService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new AmortizationEntryUpdate(
        0,
        0,
        currentDate,
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
        'AAAAAAA',
        'AAAAAAA',
        false
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            amortizationDate: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a AmortizationEntryUpdate', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            amortizationDate: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            amortizationDate: currentDate
          },
          returnedFromService
        );
        service
          .create(new AmortizationEntryUpdate(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a AmortizationEntryUpdate', async () => {
        const returnedFromService = Object.assign(
          {
            amortizationEntryId: 1,
            amortizationDate: currentDate.format(DATE_FORMAT),
            amortizationAmount: 1,
            particulars: 'BBBBBB',
            prepaymentServiceOutlet: 'BBBBBB',
            prepaymentAccountNumber: 'BBBBBB',
            amortizationServiceOutlet: 'BBBBBB',
            amortizationAccountNumber: 'BBBBBB',
            prepaymentEntryId: 1,
            originatingFileToken: 'BBBBBB',
            amortizationTag: 'BBBBBB',
            orphaned: true
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            amortizationDate: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of AmortizationEntryUpdate', async () => {
        const returnedFromService = Object.assign(
          {
            amortizationEntryId: 1,
            amortizationDate: currentDate.format(DATE_FORMAT),
            amortizationAmount: 1,
            particulars: 'BBBBBB',
            prepaymentServiceOutlet: 'BBBBBB',
            prepaymentAccountNumber: 'BBBBBB',
            amortizationServiceOutlet: 'BBBBBB',
            amortizationAccountNumber: 'BBBBBB',
            prepaymentEntryId: 1,
            originatingFileToken: 'BBBBBB',
            amortizationTag: 'BBBBBB',
            orphaned: true
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            amortizationDate: currentDate
          },
          returnedFromService
        );
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a AmortizationEntryUpdate', async () => {
        const rxPromise = service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
