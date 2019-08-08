/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { AmortizationUploadService } from 'app/entities/dataEntry/amortization-upload/amortization-upload.service';
import { IAmortizationUpload, AmortizationUpload } from 'app/shared/model/dataEntry/amortization-upload.model';

describe('Service Tests', () => {
  describe('AmortizationUpload Service', () => {
    let injector: TestBed;
    let service: AmortizationUploadService;
    let httpMock: HttpTestingController;
    let elemDefault: IAmortizationUpload;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(AmortizationUploadService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new AmortizationUpload(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        0,
        0,
        0,
        currentDate,
        false,
        false,
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            prepaymentTransactionDate: currentDate.format(DATE_FORMAT),
            firstAmortizationDate: currentDate.format(DATE_FORMAT)
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

      it('should create a AmortizationUpload', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            prepaymentTransactionDate: currentDate.format(DATE_FORMAT),
            firstAmortizationDate: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            prepaymentTransactionDate: currentDate,
            firstAmortizationDate: currentDate
          },
          returnedFromService
        );
        service
          .create(new AmortizationUpload(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a AmortizationUpload', async () => {
        const returnedFromService = Object.assign(
          {
            accountName: 'BBBBBB',
            particulars: 'BBBBBB',
            amortizationServiceOutletCode: 'BBBBBB',
            prepaymentServiceOutletCode: 'BBBBBB',
            prepaymentAccountNumber: 'BBBBBB',
            expenseAccountNumber: 'BBBBBB',
            prepaymentTransactionId: 'BBBBBB',
            prepaymentTransactionDate: currentDate.format(DATE_FORMAT),
            prepaymentTransactionAmount: 1,
            amortizationAmount: 1,
            numberOfAmortizations: 1,
            firstAmortizationDate: currentDate.format(DATE_FORMAT),
            uploadSuccessful: true,
            uploadOrphaned: true,
            originatingFileToken: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            prepaymentTransactionDate: currentDate,
            firstAmortizationDate: currentDate
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

      it('should return a list of AmortizationUpload', async () => {
        const returnedFromService = Object.assign(
          {
            accountName: 'BBBBBB',
            particulars: 'BBBBBB',
            amortizationServiceOutletCode: 'BBBBBB',
            prepaymentServiceOutletCode: 'BBBBBB',
            prepaymentAccountNumber: 'BBBBBB',
            expenseAccountNumber: 'BBBBBB',
            prepaymentTransactionId: 'BBBBBB',
            prepaymentTransactionDate: currentDate.format(DATE_FORMAT),
            prepaymentTransactionAmount: 1,
            amortizationAmount: 1,
            numberOfAmortizations: 1,
            firstAmortizationDate: currentDate.format(DATE_FORMAT),
            uploadSuccessful: true,
            uploadOrphaned: true,
            originatingFileToken: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            prepaymentTransactionDate: currentDate,
            firstAmortizationDate: currentDate
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

      it('should delete a AmortizationUpload', async () => {
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
