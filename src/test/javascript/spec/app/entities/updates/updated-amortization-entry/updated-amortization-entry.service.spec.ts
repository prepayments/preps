/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { UpdatedAmortizationEntryService } from 'app/entities/updates/updated-amortization-entry/updated-amortization-entry.service';
import { IUpdatedAmortizationEntry, UpdatedAmortizationEntry } from 'app/shared/model/updates/updated-amortization-entry.model';

describe('Service Tests', () => {
  describe('UpdatedAmortizationEntry Service', () => {
    let injector: TestBed;
    let service: UpdatedAmortizationEntryService;
    let httpMock: HttpTestingController;
    let elemDefault: IUpdatedAmortizationEntry;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(UpdatedAmortizationEntryService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new UpdatedAmortizationEntry(
        0,
        currentDate,
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        false,
        currentDate,
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            amortizationDate: currentDate.format(DATE_FORMAT),
            dateOfUpdate: currentDate.format(DATE_FORMAT)
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

      it('should create a UpdatedAmortizationEntry', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            amortizationDate: currentDate.format(DATE_FORMAT),
            dateOfUpdate: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            amortizationDate: currentDate,
            dateOfUpdate: currentDate
          },
          returnedFromService
        );
        service
          .create(new UpdatedAmortizationEntry(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a UpdatedAmortizationEntry', async () => {
        const returnedFromService = Object.assign(
          {
            amortizationDate: currentDate.format(DATE_FORMAT),
            amortizationAmount: 1,
            particulars: 'BBBBBB',
            prepaymentServiceOutlet: 'BBBBBB',
            prepaymentAccountNumber: 'BBBBBB',
            amortizationServiceOutlet: 'BBBBBB',
            amortizationAccountNumber: 'BBBBBB',
            originatingFileToken: 'BBBBBB',
            amortizationTag: 'BBBBBB',
            orphaned: true,
            dateOfUpdate: currentDate.format(DATE_FORMAT),
            reasonForUpdate: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            amortizationDate: currentDate,
            dateOfUpdate: currentDate
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

      it('should return a list of UpdatedAmortizationEntry', async () => {
        const returnedFromService = Object.assign(
          {
            amortizationDate: currentDate.format(DATE_FORMAT),
            amortizationAmount: 1,
            particulars: 'BBBBBB',
            prepaymentServiceOutlet: 'BBBBBB',
            prepaymentAccountNumber: 'BBBBBB',
            amortizationServiceOutlet: 'BBBBBB',
            amortizationAccountNumber: 'BBBBBB',
            originatingFileToken: 'BBBBBB',
            amortizationTag: 'BBBBBB',
            orphaned: true,
            dateOfUpdate: currentDate.format(DATE_FORMAT),
            reasonForUpdate: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            amortizationDate: currentDate,
            dateOfUpdate: currentDate
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

      it('should delete a UpdatedAmortizationEntry', async () => {
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
