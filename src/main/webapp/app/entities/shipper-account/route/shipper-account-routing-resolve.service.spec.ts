import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IShipperAccount } from '../shipper-account.model';
import { ShipperAccountService } from '../service/shipper-account.service';

import shipperAccountResolve from './shipper-account-routing-resolve.service';

describe('ShipperAccount routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: ShipperAccountService;
  let resultShipperAccount: IShipperAccount | null | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
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
    service = TestBed.inject(ShipperAccountService);
    resultShipperAccount = undefined;
  });

  describe('resolve', () => {
    it('should return IShipperAccount returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: '9fec3727-3421-4967-b213-ba36557ca194' };

      // WHEN
      TestBed.runInInjectionContext(() => {
        shipperAccountResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultShipperAccount = result;
          },
        });
      });

      // THEN
      expect(service.find).toBeCalledWith('9fec3727-3421-4967-b213-ba36557ca194');
      expect(resultShipperAccount).toEqual({ id: '9fec3727-3421-4967-b213-ba36557ca194' });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        shipperAccountResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultShipperAccount = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultShipperAccount).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IShipperAccount>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: '9fec3727-3421-4967-b213-ba36557ca194' };

      // WHEN
      TestBed.runInInjectionContext(() => {
        shipperAccountResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultShipperAccount = result;
          },
        });
      });

      // THEN
      expect(service.find).toBeCalledWith('9fec3727-3421-4967-b213-ba36557ca194');
      expect(resultShipperAccount).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
