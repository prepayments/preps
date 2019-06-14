/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { ScannedDocumentService } from 'app/entities/scanned-document/scanned-document.service';
import { IScannedDocument, ScannedDocument } from 'app/shared/model/scanned-document.model';

describe('Service Tests', () => {
  describe('ScannedDocument Service', () => {
    let injector: TestBed;
    let service: ScannedDocumentService;
    let httpMock: HttpTestingController;
    let elemDefault: IScannedDocument;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(ScannedDocumentService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new ScannedDocument(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        'image/png',
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        'image/png',
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            transactionDate: currentDate.format(DATE_FORMAT)
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

      it('should create a ScannedDocument', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            transactionDate: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            transactionDate: currentDate
          },
          returnedFromService
        );
        service
          .create(new ScannedDocument(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a ScannedDocument', async () => {
        const returnedFromService = Object.assign(
          {
            documentName: 'BBBBBB',
            description: 'BBBBBB',
            invoiceNumber: 'BBBBBB',
            transactionId: 'BBBBBB',
            transactionDate: currentDate.format(DATE_FORMAT),
            invoiceDocument: 'BBBBBB',
            requisitionDocument: 'BBBBBB',
            approvalMemoDocument: 'BBBBBB',
            otherScannedDocument: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            transactionDate: currentDate
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

      it('should return a list of ScannedDocument', async () => {
        const returnedFromService = Object.assign(
          {
            documentName: 'BBBBBB',
            description: 'BBBBBB',
            invoiceNumber: 'BBBBBB',
            transactionId: 'BBBBBB',
            transactionDate: currentDate.format(DATE_FORMAT),
            invoiceDocument: 'BBBBBB',
            requisitionDocument: 'BBBBBB',
            approvalMemoDocument: 'BBBBBB',
            otherScannedDocument: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            transactionDate: currentDate
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

      it('should delete a ScannedDocument', async () => {
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
