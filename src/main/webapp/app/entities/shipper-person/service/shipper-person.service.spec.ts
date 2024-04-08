import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IShipperPerson } from '../shipper-person.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../shipper-person.test-samples';

import { ShipperPersonService } from './shipper-person.service';

const requireRestSample: IShipperPerson = {
  ...sampleWithRequiredData,
};

describe('ShipperPerson Service', () => {
  let service: ShipperPersonService;
  let httpMock: HttpTestingController;
  let expectedResult: IShipperPerson | IShipperPerson[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ShipperPersonService);
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

    it('should create a ShipperPerson', () => {
      const shipperPerson = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(shipperPerson).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ShipperPerson', () => {
      const shipperPerson = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(shipperPerson).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ShipperPerson', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ShipperPerson', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ShipperPerson', () => {
      const expected = true;

      service.delete('9fec3727-3421-4967-b213-ba36557ca194').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addShipperPersonToCollectionIfMissing', () => {
      it('should add a ShipperPerson to an empty array', () => {
        const shipperPerson: IShipperPerson = sampleWithRequiredData;
        expectedResult = service.addShipperPersonToCollectionIfMissing([], shipperPerson);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(shipperPerson);
      });

      it('should not add a ShipperPerson to an array that contains it', () => {
        const shipperPerson: IShipperPerson = sampleWithRequiredData;
        const shipperPersonCollection: IShipperPerson[] = [
          {
            ...shipperPerson,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addShipperPersonToCollectionIfMissing(shipperPersonCollection, shipperPerson);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ShipperPerson to an array that doesn't contain it", () => {
        const shipperPerson: IShipperPerson = sampleWithRequiredData;
        const shipperPersonCollection: IShipperPerson[] = [sampleWithPartialData];
        expectedResult = service.addShipperPersonToCollectionIfMissing(shipperPersonCollection, shipperPerson);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(shipperPerson);
      });

      it('should add only unique ShipperPerson to an array', () => {
        const shipperPersonArray: IShipperPerson[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const shipperPersonCollection: IShipperPerson[] = [sampleWithRequiredData];
        expectedResult = service.addShipperPersonToCollectionIfMissing(shipperPersonCollection, ...shipperPersonArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const shipperPerson: IShipperPerson = sampleWithRequiredData;
        const shipperPerson2: IShipperPerson = sampleWithPartialData;
        expectedResult = service.addShipperPersonToCollectionIfMissing([], shipperPerson, shipperPerson2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(shipperPerson);
        expect(expectedResult).toContain(shipperPerson2);
      });

      it('should accept null and undefined values', () => {
        const shipperPerson: IShipperPerson = sampleWithRequiredData;
        expectedResult = service.addShipperPersonToCollectionIfMissing([], null, shipperPerson, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(shipperPerson);
      });

      it('should return initial array if no ShipperPerson is added', () => {
        const shipperPersonCollection: IShipperPerson[] = [sampleWithRequiredData];
        expectedResult = service.addShipperPersonToCollectionIfMissing(shipperPersonCollection, undefined, null);
        expect(expectedResult).toEqual(shipperPersonCollection);
      });
    });

    describe('compareShipperPerson', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareShipperPerson(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = null;

        const compareResult1 = service.compareShipperPerson(entity1, entity2);
        const compareResult2 = service.compareShipperPerson(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };

        const compareResult1 = service.compareShipperPerson(entity1, entity2);
        const compareResult2 = service.compareShipperPerson(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = { id: '9fec3727-3421-4967-b213-ba36557ca194' };

        const compareResult1 = service.compareShipperPerson(entity1, entity2);
        const compareResult2 = service.compareShipperPerson(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
