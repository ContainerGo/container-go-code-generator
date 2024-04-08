import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IShipper } from '../shipper.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../shipper.test-samples';

import { ShipperService, RestShipper } from './shipper.service';

const requireRestSample: RestShipper = {
  ...sampleWithRequiredData,
  contractValidUntil: sampleWithRequiredData.contractValidUntil?.toJSON(),
};

describe('Shipper Service', () => {
  let service: ShipperService;
  let httpMock: HttpTestingController;
  let expectedResult: IShipper | IShipper[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ShipperService);
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

    it('should create a Shipper', () => {
      const shipper = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(shipper).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Shipper', () => {
      const shipper = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(shipper).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Shipper', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Shipper', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Shipper', () => {
      const expected = true;

      service.delete('9fec3727-3421-4967-b213-ba36557ca194').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addShipperToCollectionIfMissing', () => {
      it('should add a Shipper to an empty array', () => {
        const shipper: IShipper = sampleWithRequiredData;
        expectedResult = service.addShipperToCollectionIfMissing([], shipper);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(shipper);
      });

      it('should not add a Shipper to an array that contains it', () => {
        const shipper: IShipper = sampleWithRequiredData;
        const shipperCollection: IShipper[] = [
          {
            ...shipper,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addShipperToCollectionIfMissing(shipperCollection, shipper);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Shipper to an array that doesn't contain it", () => {
        const shipper: IShipper = sampleWithRequiredData;
        const shipperCollection: IShipper[] = [sampleWithPartialData];
        expectedResult = service.addShipperToCollectionIfMissing(shipperCollection, shipper);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(shipper);
      });

      it('should add only unique Shipper to an array', () => {
        const shipperArray: IShipper[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const shipperCollection: IShipper[] = [sampleWithRequiredData];
        expectedResult = service.addShipperToCollectionIfMissing(shipperCollection, ...shipperArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const shipper: IShipper = sampleWithRequiredData;
        const shipper2: IShipper = sampleWithPartialData;
        expectedResult = service.addShipperToCollectionIfMissing([], shipper, shipper2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(shipper);
        expect(expectedResult).toContain(shipper2);
      });

      it('should accept null and undefined values', () => {
        const shipper: IShipper = sampleWithRequiredData;
        expectedResult = service.addShipperToCollectionIfMissing([], null, shipper, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(shipper);
      });

      it('should return initial array if no Shipper is added', () => {
        const shipperCollection: IShipper[] = [sampleWithRequiredData];
        expectedResult = service.addShipperToCollectionIfMissing(shipperCollection, undefined, null);
        expect(expectedResult).toEqual(shipperCollection);
      });
    });

    describe('compareShipper', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareShipper(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = null;

        const compareResult1 = service.compareShipper(entity1, entity2);
        const compareResult2 = service.compareShipper(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };

        const compareResult1 = service.compareShipper(entity1, entity2);
        const compareResult2 = service.compareShipper(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = { id: '9fec3727-3421-4967-b213-ba36557ca194' };

        const compareResult1 = service.compareShipper(entity1, entity2);
        const compareResult2 = service.compareShipper(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
