/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import { RegisteredSupplierService } from 'app/entities/prepayments/registered-supplier/registered-supplier.service';
import { IRegisteredSupplier, RegisteredSupplier } from 'app/shared/model/prepayments/registered-supplier.model';

describe('Service Tests', () => {
  describe('RegisteredSupplier Service', () => {
    let injector: TestBed;
    let service: RegisteredSupplierService;
    let httpMock: HttpTestingController;
    let elemDefault: IRegisteredSupplier;
    let expectedResult;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(RegisteredSupplierService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new RegisteredSupplier(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        false,
        'AAAAAAA',
        'AAAAAAA'
      );
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

      it('should create a RegisteredSupplier', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .create(new RegisteredSupplier(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a RegisteredSupplier', async () => {
        const returnedFromService = Object.assign(
          {
            supplierName: 'BBBBBB',
            supplierAddress: 'BBBBBB',
            phoneNumber: 'BBBBBB',
            supplierEmail: 'BBBBBB',
            bankAccountName: 'BBBBBB',
            bankAccountNumber: 'BBBBBB',
            supplierBankName: 'BBBBBB',
            supplierBankBranch: 'BBBBBB',
            bankSwiftCode: 'BBBBBB',
            bankPhysicalAddress: 'BBBBBB',
            locallyDomiciled: true,
            taxAuthorityPIN: 'BBBBBB',
            OriginatingFileToken: 'BBBBBB'
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

      it('should return a list of RegisteredSupplier', async () => {
        const returnedFromService = Object.assign(
          {
            supplierName: 'BBBBBB',
            supplierAddress: 'BBBBBB',
            phoneNumber: 'BBBBBB',
            supplierEmail: 'BBBBBB',
            bankAccountName: 'BBBBBB',
            bankAccountNumber: 'BBBBBB',
            supplierBankName: 'BBBBBB',
            supplierBankBranch: 'BBBBBB',
            bankSwiftCode: 'BBBBBB',
            bankPhysicalAddress: 'BBBBBB',
            locallyDomiciled: true,
            taxAuthorityPIN: 'BBBBBB',
            OriginatingFileToken: 'BBBBBB'
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

      it('should delete a RegisteredSupplier', async () => {
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
