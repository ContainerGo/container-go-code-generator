import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ITruckType } from '../truck-type.model';
import { TruckTypeService } from '../service/truck-type.service';

import truckTypeResolve from './truck-type-routing-resolve.service';

describe('TruckType routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: TruckTypeService;
  let resultTruckType: ITruckType | null | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    service = TestBed.inject(TruckTypeService);
    resultTruckType = undefined;
  });

  describe('resolve', () => {
    it('should return ITruckType returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: '9fec3727-3421-4967-b213-ba36557ca194' };

      // WHEN
      TestBed.runInInjectionContext(() => {
        truckTypeResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultTruckType = result;
          },
        });
      });

      // THEN
      expect(service.find).toBeCalledWith('9fec3727-3421-4967-b213-ba36557ca194');
      expect(resultTruckType).toEqual({ id: '9fec3727-3421-4967-b213-ba36557ca194' });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        truckTypeResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultTruckType = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultTruckType).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<ITruckType>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: '9fec3727-3421-4967-b213-ba36557ca194' };

      // WHEN
      TestBed.runInInjectionContext(() => {
        truckTypeResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultTruckType = result;
          },
        });
      });

      // THEN
      expect(service.find).toBeCalledWith('9fec3727-3421-4967-b213-ba36557ca194');
      expect(resultTruckType).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
