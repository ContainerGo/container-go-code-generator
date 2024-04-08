import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IShipperPersonGroup } from '../shipper-person-group.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../shipper-person-group.test-samples';

import { ShipperPersonGroupService } from './shipper-person-group.service';

const requireRestSample: IShipperPersonGroup = {
  ...sampleWithRequiredData,
};

describe('ShipperPersonGroup Service', () => {
  let service: ShipperPersonGroupService;
  let httpMock: HttpTestingController;
  let expectedResult: IShipperPersonGroup | IShipperPersonGroup[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ShipperPersonGroupService);
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

    it('should create a ShipperPersonGroup', () => {
      const shipperPersonGroup = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(shipperPersonGroup).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ShipperPersonGroup', () => {
      const shipperPersonGroup = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(shipperPersonGroup).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ShipperPersonGroup', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ShipperPersonGroup', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ShipperPersonGroup', () => {
      const expected = true;

      service.delete('9fec3727-3421-4967-b213-ba36557ca194').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addShipperPersonGroupToCollectionIfMissing', () => {
      it('should add a ShipperPersonGroup to an empty array', () => {
        const shipperPersonGroup: IShipperPersonGroup = sampleWithRequiredData;
        expectedResult = service.addShipperPersonGroupToCollectionIfMissing([], shipperPersonGroup);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(shipperPersonGroup);
      });

      it('should not add a ShipperPersonGroup to an array that contains it', () => {
        const shipperPersonGroup: IShipperPersonGroup = sampleWithRequiredData;
        const shipperPersonGroupCollection: IShipperPersonGroup[] = [
          {
            ...shipperPersonGroup,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addShipperPersonGroupToCollectionIfMissing(shipperPersonGroupCollection, shipperPersonGroup);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ShipperPersonGroup to an array that doesn't contain it", () => {
        const shipperPersonGroup: IShipperPersonGroup = sampleWithRequiredData;
        const shipperPersonGroupCollection: IShipperPersonGroup[] = [sampleWithPartialData];
        expectedResult = service.addShipperPersonGroupToCollectionIfMissing(shipperPersonGroupCollection, shipperPersonGroup);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(shipperPersonGroup);
      });

      it('should add only unique ShipperPersonGroup to an array', () => {
        const shipperPersonGroupArray: IShipperPersonGroup[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const shipperPersonGroupCollection: IShipperPersonGroup[] = [sampleWithRequiredData];
        expectedResult = service.addShipperPersonGroupToCollectionIfMissing(shipperPersonGroupCollection, ...shipperPersonGroupArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const shipperPersonGroup: IShipperPersonGroup = sampleWithRequiredData;
        const shipperPersonGroup2: IShipperPersonGroup = sampleWithPartialData;
        expectedResult = service.addShipperPersonGroupToCollectionIfMissing([], shipperPersonGroup, shipperPersonGroup2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(shipperPersonGroup);
        expect(expectedResult).toContain(shipperPersonGroup2);
      });

      it('should accept null and undefined values', () => {
        const shipperPersonGroup: IShipperPersonGroup = sampleWithRequiredData;
        expectedResult = service.addShipperPersonGroupToCollectionIfMissing([], null, shipperPersonGroup, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(shipperPersonGroup);
      });

      it('should return initial array if no ShipperPersonGroup is added', () => {
        const shipperPersonGroupCollection: IShipperPersonGroup[] = [sampleWithRequiredData];
        expectedResult = service.addShipperPersonGroupToCollectionIfMissing(shipperPersonGroupCollection, undefined, null);
        expect(expectedResult).toEqual(shipperPersonGroupCollection);
      });
    });

    describe('compareShipperPersonGroup', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareShipperPersonGroup(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = null;

        const compareResult1 = service.compareShipperPersonGroup(entity1, entity2);
        const compareResult2 = service.compareShipperPersonGroup(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };

        const compareResult1 = service.compareShipperPersonGroup(entity1, entity2);
        const compareResult2 = service.compareShipperPersonGroup(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = { id: '9fec3727-3421-4967-b213-ba36557ca194' };

        const compareResult1 = service.compareShipperPersonGroup(entity1, entity2);
        const compareResult2 = service.compareShipperPersonGroup(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
