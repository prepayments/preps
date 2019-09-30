/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import { AmortizationUpdateFileService } from 'app/entities/updates/amortization-update-file/amortization-update-file.service';
import { IAmortizationUpdateFile, AmortizationUpdateFile } from 'app/shared/model/updates/amortization-update-file.model';

describe('Service Tests', () => {
  describe('AmortizationUpdateFile Service', () => {
    let injector: TestBed;
    let service: AmortizationUpdateFileService;
    let httpMock: HttpTestingController;
    let elemDefault: IAmortizationUpdateFile;
    let expectedResult;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(AmortizationUpdateFileService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new AmortizationUpdateFile(0, 'AAAAAAA', 'image/png', 'AAAAAAA', false, false, 0, 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign({}, elemDefault);
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a AmortizationUpdateFile', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .create(new AmortizationUpdateFile(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a AmortizationUpdateFile', async () => {
        const returnedFromService = Object.assign(
          {
            narration: 'BBBBBB',
            dataEntryFile: 'BBBBBB',
            uploadSuccessful: true,
            uploadProcessed: true,
            entriesCount: 1,
            fileToken: 'BBBBBB',
            reasonForUpdate: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of AmortizationUpdateFile', async () => {
        const returnedFromService = Object.assign(
          {
            narration: 'BBBBBB',
            dataEntryFile: 'BBBBBB',
            uploadSuccessful: true,
            uploadProcessed: true,
            entriesCount: 1,
            fileToken: 'BBBBBB',
            reasonForUpdate: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
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

      it('should delete a AmortizationUpdateFile', async () => {
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
