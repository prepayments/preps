/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import { ServiceOutletService } from 'app/entities/prepayments/service-outlet/service-outlet.service';
import { IServiceOutlet, ServiceOutlet } from 'app/shared/model/prepayments/service-outlet.model';

describe('Service Tests', () => {
  describe('ServiceOutlet Service', () => {
    let injector: TestBed;
    let service: ServiceOutletService;
    let httpMock: HttpTestingController;
    let elemDefault: IServiceOutlet;
    let expectedResult;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(ServiceOutletService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new ServiceOutlet(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
        'AAAAAAA',
        0,
        'AAAAAAA',
        'AAAAAAA',
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

      it('should create a ServiceOutlet', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .create(new ServiceOutlet(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a ServiceOutlet', async () => {
        const returnedFromService = Object.assign(
          {
            serviceOutletName: 'BBBBBB',
            serviceOutletCode: 'BBBBBB',
            serviceOutletLocation: 'BBBBBB',
            serviceOutletManager: 'BBBBBB',
            numberOfStaff: 1,
            building: 'BBBBBB',
            floor: 1,
            postalAddress: 'BBBBBB',
            contactPersonName: 'BBBBBB',
            contactEmail: 'BBBBBB',
            street: 'BBBBBB'
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

      it('should return a list of ServiceOutlet', async () => {
        const returnedFromService = Object.assign(
          {
            serviceOutletName: 'BBBBBB',
            serviceOutletCode: 'BBBBBB',
            serviceOutletLocation: 'BBBBBB',
            serviceOutletManager: 'BBBBBB',
            numberOfStaff: 1,
            building: 'BBBBBB',
            floor: 1,
            postalAddress: 'BBBBBB',
            contactPersonName: 'BBBBBB',
            contactEmail: 'BBBBBB',
            street: 'BBBBBB'
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

      it('should delete a ServiceOutlet', async () => {
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
