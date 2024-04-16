import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IShipperAccount } from '../shipper-account.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../shipper-account.test-samples';

import { ShipperAccountService } from './shipper-account.service';

const requireRestSample: IShipperAccount = {
  ...sampleWithRequiredData,
};

describe('ShipperAccount Service', () => {
  let service: ShipperAccountService;
  let httpMock: HttpTestingController;
  let expectedResult: IShipperAccount | IShipperAccount[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ShipperAccountService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find('9fec3727-3421-4967-b213-ba36557ca194').subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a ShipperAccount', () => {
      const shipperAccount = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(shipperAccount).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ShipperAccount', () => {
      const shipperAccount = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(shipperAccount).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ShipperAccount', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ShipperAccount', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ShipperAccount', () => {
      const expected = true;

      service.delete('9fec3727-3421-4967-b213-ba36557ca194').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addShipperAccountToCollectionIfMissing', () => {
      it('should add a ShipperAccount to an empty array', () => {
        const shipperAccount: IShipperAccount = sampleWithRequiredData;
        expectedResult = service.addShipperAccountToCollectionIfMissing([], shipperAccount);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(shipperAccount);
      });

      it('should not add a ShipperAccount to an array that contains it', () => {
        const shipperAccount: IShipperAccount = sampleWithRequiredData;
        const shipperAccountCollection: IShipperAccount[] = [
          {
            ...shipperAccount,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addShipperAccountToCollectionIfMissing(shipperAccountCollection, shipperAccount);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ShipperAccount to an array that doesn't contain it", () => {
        const shipperAccount: IShipperAccount = sampleWithRequiredData;
        const shipperAccountCollection: IShipperAccount[] = [sampleWithPartialData];
        expectedResult = service.addShipperAccountToCollectionIfMissing(shipperAccountCollection, shipperAccount);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(shipperAccount);
      });

      it('should add only unique ShipperAccount to an array', () => {
        const shipperAccountArray: IShipperAccount[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const shipperAccountCollection: IShipperAccount[] = [sampleWithRequiredData];
        expectedResult = service.addShipperAccountToCollectionIfMissing(shipperAccountCollection, ...shipperAccountArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const shipperAccount: IShipperAccount = sampleWithRequiredData;
        const shipperAccount2: IShipperAccount = sampleWithPartialData;
        expectedResult = service.addShipperAccountToCollectionIfMissing([], shipperAccount, shipperAccount2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(shipperAccount);
        expect(expectedResult).toContain(shipperAccount2);
      });

      it('should accept null and undefined values', () => {
        const shipperAccount: IShipperAccount = sampleWithRequiredData;
        expectedResult = service.addShipperAccountToCollectionIfMissing([], null, shipperAccount, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(shipperAccount);
      });

      it('should return initial array if no ShipperAccount is added', () => {
        const shipperAccountCollection: IShipperAccount[] = [sampleWithRequiredData];
        expectedResult = service.addShipperAccountToCollectionIfMissing(shipperAccountCollection, undefined, null);
        expect(expectedResult).toEqual(shipperAccountCollection);
      });
    });

    describe('compareShipperAccount', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareShipperAccount(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = null;

        const compareResult1 = service.compareShipperAccount(entity1, entity2);
        const compareResult2 = service.compareShipperAccount(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };

        const compareResult1 = service.compareShipperAccount(entity1, entity2);
        const compareResult2 = service.compareShipperAccount(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = { id: '9fec3727-3421-4967-b213-ba36557ca194' };

        const compareResult1 = service.compareShipperAccount(entity1, entity2);
        const compareResult2 = service.compareShipperAccount(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
