/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { ReportRequestEventService } from 'app/entities/reports/report-request-event/report-request-event.service';
import { IReportRequestEvent, ReportRequestEvent } from 'app/shared/model/reports/report-request-event.model';

describe('Service Tests', () => {
  describe('ReportRequestEvent Service', () => {
    let injector: TestBed;
    let service: ReportRequestEventService;
    let httpMock: HttpTestingController;
    let elemDefault: IReportRequestEvent;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(ReportRequestEventService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new ReportRequestEvent(0, currentDate, 'AAAAAAA', 'image/png', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            reportRequestDate: currentDate.format(DATE_FORMAT)
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

      it('should create a ReportRequestEvent', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            reportRequestDate: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            reportRequestDate: currentDate
          },
          returnedFromService
        );
        service
          .create(new ReportRequestEvent(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a ReportRequestEvent', async () => {
        const returnedFromService = Object.assign(
          {
            reportRequestDate: currentDate.format(DATE_FORMAT),
            requestedBy: 'BBBBBB',
            reportFile: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            reportRequestDate: currentDate
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

      it('should return a list of ReportRequestEvent', async () => {
        const returnedFromService = Object.assign(
          {
            reportRequestDate: currentDate.format(DATE_FORMAT),
            requestedBy: 'BBBBBB',
            reportFile: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            reportRequestDate: currentDate
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

      it('should delete a ReportRequestEvent', async () => {
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
