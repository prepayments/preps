/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SupplierDataEntryFileService } from 'app/entities/dataEntry/supplier-data-entry-file/supplier-data-entry-file.service';
import { ISupplierDataEntryFile, SupplierDataEntryFile } from 'app/shared/model/dataEntry/supplier-data-entry-file.model';

describe('Service Tests', () => {
  describe('SupplierDataEntryFile Service', () => {
    let injector: TestBed;
    let service: SupplierDataEntryFileService;
    let httpMock: HttpTestingController;
    let elemDefault: ISupplierDataEntryFile;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(SupplierDataEntryFileService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new SupplierDataEntryFile(0, currentDate, currentDate, 'image/png', 'AAAAAAA', false, false, 0, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            periodFrom: currentDate.format(DATE_FORMAT),
            periodTo: currentDate.format(DATE_FORMAT)
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

      it('should create a SupplierDataEntryFile', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            periodFrom: currentDate.format(DATE_FORMAT),
            periodTo: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            periodFrom: currentDate,
            periodTo: currentDate
          },
          returnedFromService
        );
        service
          .create(new SupplierDataEntryFile(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a SupplierDataEntryFile', async () => {
        const returnedFromService = Object.assign(
          {
            periodFrom: currentDate.format(DATE_FORMAT),
            periodTo: currentDate.format(DATE_FORMAT),
            dataEntryFile: 'BBBBBB',
            uploadSuccessful: true,
            uploadProcessed: true,
            entriesCount: 1,
            fileToken: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            periodFrom: currentDate,
            periodTo: currentDate
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

      it('should return a list of SupplierDataEntryFile', async () => {
        const returnedFromService = Object.assign(
          {
            periodFrom: currentDate.format(DATE_FORMAT),
            periodTo: currentDate.format(DATE_FORMAT),
            dataEntryFile: 'BBBBBB',
            uploadSuccessful: true,
            uploadProcessed: true,
            entriesCount: 1,
            fileToken: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            periodFrom: currentDate,
            periodTo: currentDate
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

      it('should delete a SupplierDataEntryFile', async () => {
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
