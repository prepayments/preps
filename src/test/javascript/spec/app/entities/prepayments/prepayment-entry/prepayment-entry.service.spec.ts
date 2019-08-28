/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { PrepaymentEntryService } from 'app/entities/prepayments/prepayment-entry/prepayment-entry.service';
import { IPrepaymentEntry, PrepaymentEntry } from 'app/shared/model/prepayments/prepayment-entry.model';

describe('Service Tests', () => {
  describe('PrepaymentEntry Service', () => {
    let injector: TestBed;
    let service: PrepaymentEntryService;
    let httpMock: HttpTestingController;
    let elemDefault: IPrepaymentEntry;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(PrepaymentEntryService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new PrepaymentEntry(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        0,
        0,
        'AAAAAAA',
        'AAAAAAA',
        0,
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            prepaymentDate: currentDate.format(DATE_FORMAT)
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

      it('should create a PrepaymentEntry', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            prepaymentDate: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            prepaymentDate: currentDate
          },
          returnedFromService
        );
        service
          .create(new PrepaymentEntry(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a PrepaymentEntry', async () => {
        const returnedFromService = Object.assign(
          {
            accountNumber: 'BBBBBB',
            accountName: 'BBBBBB',
            prepaymentId: 'BBBBBB',
            prepaymentDate: currentDate.format(DATE_FORMAT),
            particulars: 'BBBBBB',
            serviceOutlet: 'BBBBBB',
            prepaymentAmount: 1,
            months: 1,
            supplierName: 'BBBBBB',
            invoiceNumber: 'BBBBBB',
            scannedDocumentId: 1,
            originatingFileToken: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            prepaymentDate: currentDate
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

      it('should return a list of PrepaymentEntry', async () => {
        const returnedFromService = Object.assign(
          {
            accountNumber: 'BBBBBB',
            accountName: 'BBBBBB',
            prepaymentId: 'BBBBBB',
            prepaymentDate: currentDate.format(DATE_FORMAT),
            particulars: 'BBBBBB',
            serviceOutlet: 'BBBBBB',
            prepaymentAmount: 1,
            months: 1,
            supplierName: 'BBBBBB',
            invoiceNumber: 'BBBBBB',
            scannedDocumentId: 1,
            originatingFileToken: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            prepaymentDate: currentDate
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

      it('should delete a PrepaymentEntry', async () => {
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
