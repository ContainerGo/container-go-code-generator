import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IContainerStatusGroup } from '../container-status-group.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../container-status-group.test-samples';

import { ContainerStatusGroupService } from './container-status-group.service';

const requireRestSample: IContainerStatusGroup = {
  ...sampleWithRequiredData,
};

describe('ContainerStatusGroup Service', () => {
  let service: ContainerStatusGroupService;
  let httpMock: HttpTestingController;
  let expectedResult: IContainerStatusGroup | IContainerStatusGroup[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ContainerStatusGroupService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a ContainerStatusGroup', () => {
      const containerStatusGroup = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(containerStatusGroup).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ContainerStatusGroup', () => {
      const containerStatusGroup = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(containerStatusGroup).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ContainerStatusGroup', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ContainerStatusGroup', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ContainerStatusGroup', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addContainerStatusGroupToCollectionIfMissing', () => {
      it('should add a ContainerStatusGroup to an empty array', () => {
        const containerStatusGroup: IContainerStatusGroup = sampleWithRequiredData;
        expectedResult = service.addContainerStatusGroupToCollectionIfMissing([], containerStatusGroup);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(containerStatusGroup);
      });

      it('should not add a ContainerStatusGroup to an array that contains it', () => {
        const containerStatusGroup: IContainerStatusGroup = sampleWithRequiredData;
        const containerStatusGroupCollection: IContainerStatusGroup[] = [
          {
            ...containerStatusGroup,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addContainerStatusGroupToCollectionIfMissing(containerStatusGroupCollection, containerStatusGroup);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ContainerStatusGroup to an array that doesn't contain it", () => {
        const containerStatusGroup: IContainerStatusGroup = sampleWithRequiredData;
        const containerStatusGroupCollection: IContainerStatusGroup[] = [sampleWithPartialData];
        expectedResult = service.addContainerStatusGroupToCollectionIfMissing(containerStatusGroupCollection, containerStatusGroup);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(containerStatusGroup);
      });

      it('should add only unique ContainerStatusGroup to an array', () => {
        const containerStatusGroupArray: IContainerStatusGroup[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const containerStatusGroupCollection: IContainerStatusGroup[] = [sampleWithRequiredData];
        expectedResult = service.addContainerStatusGroupToCollectionIfMissing(containerStatusGroupCollection, ...containerStatusGroupArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const containerStatusGroup: IContainerStatusGroup = sampleWithRequiredData;
        const containerStatusGroup2: IContainerStatusGroup = sampleWithPartialData;
        expectedResult = service.addContainerStatusGroupToCollectionIfMissing([], containerStatusGroup, containerStatusGroup2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(containerStatusGroup);
        expect(expectedResult).toContain(containerStatusGroup2);
      });

      it('should accept null and undefined values', () => {
        const containerStatusGroup: IContainerStatusGroup = sampleWithRequiredData;
        expectedResult = service.addContainerStatusGroupToCollectionIfMissing([], null, containerStatusGroup, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(containerStatusGroup);
      });

      it('should return initial array if no ContainerStatusGroup is added', () => {
        const containerStatusGroupCollection: IContainerStatusGroup[] = [sampleWithRequiredData];
        expectedResult = service.addContainerStatusGroupToCollectionIfMissing(containerStatusGroupCollection, undefined, null);
        expect(expectedResult).toEqual(containerStatusGroupCollection);
      });
    });

    describe('compareContainerStatusGroup', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareContainerStatusGroup(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareContainerStatusGroup(entity1, entity2);
        const compareResult2 = service.compareContainerStatusGroup(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareContainerStatusGroup(entity1, entity2);
        const compareResult2 = service.compareContainerStatusGroup(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareContainerStatusGroup(entity1, entity2);
        const compareResult2 = service.compareContainerStatusGroup(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
