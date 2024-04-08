import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICarrierPersonGroup } from '../carrier-person-group.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../carrier-person-group.test-samples';

import { CarrierPersonGroupService } from './carrier-person-group.service';

const requireRestSample: ICarrierPersonGroup = {
  ...sampleWithRequiredData,
};

describe('CarrierPersonGroup Service', () => {
  let service: CarrierPersonGroupService;
  let httpMock: HttpTestingController;
  let expectedResult: ICarrierPersonGroup | ICarrierPersonGroup[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CarrierPersonGroupService);
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

    it('should create a CarrierPersonGroup', () => {
      const carrierPersonGroup = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(carrierPersonGroup).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CarrierPersonGroup', () => {
      const carrierPersonGroup = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(carrierPersonGroup).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CarrierPersonGroup', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CarrierPersonGroup', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CarrierPersonGroup', () => {
      const expected = true;

      service.delete('9fec3727-3421-4967-b213-ba36557ca194').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCarrierPersonGroupToCollectionIfMissing', () => {
      it('should add a CarrierPersonGroup to an empty array', () => {
        const carrierPersonGroup: ICarrierPersonGroup = sampleWithRequiredData;
        expectedResult = service.addCarrierPersonGroupToCollectionIfMissing([], carrierPersonGroup);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(carrierPersonGroup);
      });

      it('should not add a CarrierPersonGroup to an array that contains it', () => {
        const carrierPersonGroup: ICarrierPersonGroup = sampleWithRequiredData;
        const carrierPersonGroupCollection: ICarrierPersonGroup[] = [
          {
            ...carrierPersonGroup,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCarrierPersonGroupToCollectionIfMissing(carrierPersonGroupCollection, carrierPersonGroup);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CarrierPersonGroup to an array that doesn't contain it", () => {
        const carrierPersonGroup: ICarrierPersonGroup = sampleWithRequiredData;
        const carrierPersonGroupCollection: ICarrierPersonGroup[] = [sampleWithPartialData];
        expectedResult = service.addCarrierPersonGroupToCollectionIfMissing(carrierPersonGroupCollection, carrierPersonGroup);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(carrierPersonGroup);
      });

      it('should add only unique CarrierPersonGroup to an array', () => {
        const carrierPersonGroupArray: ICarrierPersonGroup[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const carrierPersonGroupCollection: ICarrierPersonGroup[] = [sampleWithRequiredData];
        expectedResult = service.addCarrierPersonGroupToCollectionIfMissing(carrierPersonGroupCollection, ...carrierPersonGroupArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const carrierPersonGroup: ICarrierPersonGroup = sampleWithRequiredData;
        const carrierPersonGroup2: ICarrierPersonGroup = sampleWithPartialData;
        expectedResult = service.addCarrierPersonGroupToCollectionIfMissing([], carrierPersonGroup, carrierPersonGroup2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(carrierPersonGroup);
        expect(expectedResult).toContain(carrierPersonGroup2);
      });

      it('should accept null and undefined values', () => {
        const carrierPersonGroup: ICarrierPersonGroup = sampleWithRequiredData;
        expectedResult = service.addCarrierPersonGroupToCollectionIfMissing([], null, carrierPersonGroup, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(carrierPersonGroup);
      });

      it('should return initial array if no CarrierPersonGroup is added', () => {
        const carrierPersonGroupCollection: ICarrierPersonGroup[] = [sampleWithRequiredData];
        expectedResult = service.addCarrierPersonGroupToCollectionIfMissing(carrierPersonGroupCollection, undefined, null);
        expect(expectedResult).toEqual(carrierPersonGroupCollection);
      });
    });

    describe('compareCarrierPersonGroup', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCarrierPersonGroup(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = null;

        const compareResult1 = service.compareCarrierPersonGroup(entity1, entity2);
        const compareResult2 = service.compareCarrierPersonGroup(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };

        const compareResult1 = service.compareCarrierPersonGroup(entity1, entity2);
        const compareResult2 = service.compareCarrierPersonGroup(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = { id: '9fec3727-3421-4967-b213-ba36557ca194' };

        const compareResult1 = service.compareCarrierPersonGroup(entity1, entity2);
        const compareResult2 = service.compareCarrierPersonGroup(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
