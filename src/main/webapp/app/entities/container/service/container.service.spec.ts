import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IContainer } from '../container.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../container.test-samples';

import { ContainerService, RestContainer } from './container.service';

const requireRestSample: RestContainer = {
  ...sampleWithRequiredData,
  dropoffUntilDate: sampleWithRequiredData.dropoffUntilDate?.toJSON(),
  pickupFromDate: sampleWithRequiredData.pickupFromDate?.toJSON(),
  biddingFromDate: sampleWithRequiredData.biddingFromDate?.toJSON(),
  biddingUntilDate: sampleWithRequiredData.biddingUntilDate?.toJSON(),
};

describe('Container Service', () => {
  let service: ContainerService;
  let httpMock: HttpTestingController;
  let expectedResult: IContainer | IContainer[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ContainerService);
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

    it('should create a Container', () => {
      const container = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(container).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Container', () => {
      const container = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(container).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Container', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Container', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Container', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addContainerToCollectionIfMissing', () => {
      it('should add a Container to an empty array', () => {
        const container: IContainer = sampleWithRequiredData;
        expectedResult = service.addContainerToCollectionIfMissing([], container);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(container);
      });

      it('should not add a Container to an array that contains it', () => {
        const container: IContainer = sampleWithRequiredData;
        const containerCollection: IContainer[] = [
          {
            ...container,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addContainerToCollectionIfMissing(containerCollection, container);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Container to an array that doesn't contain it", () => {
        const container: IContainer = sampleWithRequiredData;
        const containerCollection: IContainer[] = [sampleWithPartialData];
        expectedResult = service.addContainerToCollectionIfMissing(containerCollection, container);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(container);
      });

      it('should add only unique Container to an array', () => {
        const containerArray: IContainer[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const containerCollection: IContainer[] = [sampleWithRequiredData];
        expectedResult = service.addContainerToCollectionIfMissing(containerCollection, ...containerArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const container: IContainer = sampleWithRequiredData;
        const container2: IContainer = sampleWithPartialData;
        expectedResult = service.addContainerToCollectionIfMissing([], container, container2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(container);
        expect(expectedResult).toContain(container2);
      });

      it('should accept null and undefined values', () => {
        const container: IContainer = sampleWithRequiredData;
        expectedResult = service.addContainerToCollectionIfMissing([], null, container, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(container);
      });

      it('should return initial array if no Container is added', () => {
        const containerCollection: IContainer[] = [sampleWithRequiredData];
        expectedResult = service.addContainerToCollectionIfMissing(containerCollection, undefined, null);
        expect(expectedResult).toEqual(containerCollection);
      });
    });

    describe('compareContainer', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareContainer(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareContainer(entity1, entity2);
        const compareResult2 = service.compareContainer(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareContainer(entity1, entity2);
        const compareResult2 = service.compareContainer(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareContainer(entity1, entity2);
        const compareResult2 = service.compareContainer(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
