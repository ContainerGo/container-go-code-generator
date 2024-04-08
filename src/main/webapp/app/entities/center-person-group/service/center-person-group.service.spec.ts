import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICenterPersonGroup } from '../center-person-group.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../center-person-group.test-samples';

import { CenterPersonGroupService } from './center-person-group.service';

const requireRestSample: ICenterPersonGroup = {
  ...sampleWithRequiredData,
};

describe('CenterPersonGroup Service', () => {
  let service: CenterPersonGroupService;
  let httpMock: HttpTestingController;
  let expectedResult: ICenterPersonGroup | ICenterPersonGroup[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CenterPersonGroupService);
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

    it('should create a CenterPersonGroup', () => {
      const centerPersonGroup = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(centerPersonGroup).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CenterPersonGroup', () => {
      const centerPersonGroup = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(centerPersonGroup).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CenterPersonGroup', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CenterPersonGroup', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CenterPersonGroup', () => {
      const expected = true;

      service.delete('9fec3727-3421-4967-b213-ba36557ca194').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCenterPersonGroupToCollectionIfMissing', () => {
      it('should add a CenterPersonGroup to an empty array', () => {
        const centerPersonGroup: ICenterPersonGroup = sampleWithRequiredData;
        expectedResult = service.addCenterPersonGroupToCollectionIfMissing([], centerPersonGroup);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(centerPersonGroup);
      });

      it('should not add a CenterPersonGroup to an array that contains it', () => {
        const centerPersonGroup: ICenterPersonGroup = sampleWithRequiredData;
        const centerPersonGroupCollection: ICenterPersonGroup[] = [
          {
            ...centerPersonGroup,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCenterPersonGroupToCollectionIfMissing(centerPersonGroupCollection, centerPersonGroup);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CenterPersonGroup to an array that doesn't contain it", () => {
        const centerPersonGroup: ICenterPersonGroup = sampleWithRequiredData;
        const centerPersonGroupCollection: ICenterPersonGroup[] = [sampleWithPartialData];
        expectedResult = service.addCenterPersonGroupToCollectionIfMissing(centerPersonGroupCollection, centerPersonGroup);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(centerPersonGroup);
      });

      it('should add only unique CenterPersonGroup to an array', () => {
        const centerPersonGroupArray: ICenterPersonGroup[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const centerPersonGroupCollection: ICenterPersonGroup[] = [sampleWithRequiredData];
        expectedResult = service.addCenterPersonGroupToCollectionIfMissing(centerPersonGroupCollection, ...centerPersonGroupArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const centerPersonGroup: ICenterPersonGroup = sampleWithRequiredData;
        const centerPersonGroup2: ICenterPersonGroup = sampleWithPartialData;
        expectedResult = service.addCenterPersonGroupToCollectionIfMissing([], centerPersonGroup, centerPersonGroup2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(centerPersonGroup);
        expect(expectedResult).toContain(centerPersonGroup2);
      });

      it('should accept null and undefined values', () => {
        const centerPersonGroup: ICenterPersonGroup = sampleWithRequiredData;
        expectedResult = service.addCenterPersonGroupToCollectionIfMissing([], null, centerPersonGroup, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(centerPersonGroup);
      });

      it('should return initial array if no CenterPersonGroup is added', () => {
        const centerPersonGroupCollection: ICenterPersonGroup[] = [sampleWithRequiredData];
        expectedResult = service.addCenterPersonGroupToCollectionIfMissing(centerPersonGroupCollection, undefined, null);
        expect(expectedResult).toEqual(centerPersonGroupCollection);
      });
    });

    describe('compareCenterPersonGroup', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCenterPersonGroup(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = null;

        const compareResult1 = service.compareCenterPersonGroup(entity1, entity2);
        const compareResult2 = service.compareCenterPersonGroup(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };

        const compareResult1 = service.compareCenterPersonGroup(entity1, entity2);
        const compareResult2 = service.compareCenterPersonGroup(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = { id: '9fec3727-3421-4967-b213-ba36557ca194' };

        const compareResult1 = service.compareCenterPersonGroup(entity1, entity2);
        const compareResult2 = service.compareCenterPersonGroup(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
