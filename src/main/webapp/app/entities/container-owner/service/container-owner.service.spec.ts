import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IContainerOwner } from '../container-owner.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../container-owner.test-samples';

import { ContainerOwnerService } from './container-owner.service';

const requireRestSample: IContainerOwner = {
  ...sampleWithRequiredData,
};

describe('ContainerOwner Service', () => {
  let service: ContainerOwnerService;
  let httpMock: HttpTestingController;
  let expectedResult: IContainerOwner | IContainerOwner[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ContainerOwnerService);
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

    it('should create a ContainerOwner', () => {
      const containerOwner = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(containerOwner).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ContainerOwner', () => {
      const containerOwner = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(containerOwner).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ContainerOwner', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ContainerOwner', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ContainerOwner', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addContainerOwnerToCollectionIfMissing', () => {
      it('should add a ContainerOwner to an empty array', () => {
        const containerOwner: IContainerOwner = sampleWithRequiredData;
        expectedResult = service.addContainerOwnerToCollectionIfMissing([], containerOwner);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(containerOwner);
      });

      it('should not add a ContainerOwner to an array that contains it', () => {
        const containerOwner: IContainerOwner = sampleWithRequiredData;
        const containerOwnerCollection: IContainerOwner[] = [
          {
            ...containerOwner,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addContainerOwnerToCollectionIfMissing(containerOwnerCollection, containerOwner);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ContainerOwner to an array that doesn't contain it", () => {
        const containerOwner: IContainerOwner = sampleWithRequiredData;
        const containerOwnerCollection: IContainerOwner[] = [sampleWithPartialData];
        expectedResult = service.addContainerOwnerToCollectionIfMissing(containerOwnerCollection, containerOwner);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(containerOwner);
      });

      it('should add only unique ContainerOwner to an array', () => {
        const containerOwnerArray: IContainerOwner[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const containerOwnerCollection: IContainerOwner[] = [sampleWithRequiredData];
        expectedResult = service.addContainerOwnerToCollectionIfMissing(containerOwnerCollection, ...containerOwnerArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const containerOwner: IContainerOwner = sampleWithRequiredData;
        const containerOwner2: IContainerOwner = sampleWithPartialData;
        expectedResult = service.addContainerOwnerToCollectionIfMissing([], containerOwner, containerOwner2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(containerOwner);
        expect(expectedResult).toContain(containerOwner2);
      });

      it('should accept null and undefined values', () => {
        const containerOwner: IContainerOwner = sampleWithRequiredData;
        expectedResult = service.addContainerOwnerToCollectionIfMissing([], null, containerOwner, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(containerOwner);
      });

      it('should return initial array if no ContainerOwner is added', () => {
        const containerOwnerCollection: IContainerOwner[] = [sampleWithRequiredData];
        expectedResult = service.addContainerOwnerToCollectionIfMissing(containerOwnerCollection, undefined, null);
        expect(expectedResult).toEqual(containerOwnerCollection);
      });
    });

    describe('compareContainerOwner', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareContainerOwner(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareContainerOwner(entity1, entity2);
        const compareResult2 = service.compareContainerOwner(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareContainerOwner(entity1, entity2);
        const compareResult2 = service.compareContainerOwner(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareContainerOwner(entity1, entity2);
        const compareResult2 = service.compareContainerOwner(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
