/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { TransactionAccountService } from 'app/entities/prepayments/transaction-account/transaction-account.service';
import { ITransactionAccount, TransactionAccount } from 'app/shared/model/prepayments/transaction-account.model';

describe('Service Tests', () => {
  describe('TransactionAccount Service', () => {
    let injector: TestBed;
    let service: TransactionAccountService;
    let httpMock: HttpTestingController;
    let elemDefault: ITransactionAccount;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(TransactionAccountService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new TransactionAccount(0, 'AAAAAAA', 'AAAAAAA', 0, currentDate, 0);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            openingDate: currentDate.format(DATE_FORMAT)
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

      it('should create a TransactionAccount', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            openingDate: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            openingDate: currentDate
          },
          returnedFromService
        );
        service
          .create(new TransactionAccount(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a TransactionAccount', async () => {
        const returnedFromService = Object.assign(
          {
            accountName: 'BBBBBB',
            accountNumber: 'BBBBBB',
            accountBalance: 1,
            openingDate: currentDate.format(DATE_FORMAT),
            accountOpeningDateBalance: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            openingDate: currentDate
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

      it('should return a list of TransactionAccount', async () => {
        const returnedFromService = Object.assign(
          {
            accountName: 'BBBBBB',
            accountNumber: 'BBBBBB',
            accountBalance: 1,
            openingDate: currentDate.format(DATE_FORMAT),
            accountOpeningDateBalance: 1
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            openingDate: currentDate
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

      it('should delete a TransactionAccount', async () => {
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
