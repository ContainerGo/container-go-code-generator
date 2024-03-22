import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../truck-type.test-samples';

import { TruckTypeFormService } from './truck-type-form.service';

describe('TruckType Form Service', () => {
  let service: TruckTypeFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TruckTypeFormService);
  });

  describe('Service methods', () => {
    describe('createTruckTypeFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createTruckTypeFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            name: expect.any(Object),
            category: expect.any(Object),
            height: expect.any(Object),
            length: expect.any(Object),
            maxSpeed: expect.any(Object),
            type: expect.any(Object),
            weight: expect.any(Object),
            width: expect.any(Object),
          }),
        );
      });

      it('passing ITruckType should create a new form with FormGroup', () => {
        const formGroup = service.createTruckTypeFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            name: expect.any(Object),
            category: expect.any(Object),
            height: expect.any(Object),
            length: expect.any(Object),
            maxSpeed: expect.any(Object),
            type: expect.any(Object),
            weight: expect.any(Object),
            width: expect.any(Object),
          }),
        );
      });
    });

    describe('getTruckType', () => {
      it('should return NewTruckType for default TruckType initial value', () => {
        const formGroup = service.createTruckTypeFormGroup(sampleWithNewData);

        const truckType = service.getTruckType(formGroup) as any;

        expect(truckType).toMatchObject(sampleWithNewData);
      });

      it('should return NewTruckType for empty TruckType initial value', () => {
        const formGroup = service.createTruckTypeFormGroup();

        const truckType = service.getTruckType(formGroup) as any;

        expect(truckType).toMatchObject({});
      });

      it('should return ITruckType', () => {
        const formGroup = service.createTruckTypeFormGroup(sampleWithRequiredData);

        const truckType = service.getTruckType(formGroup) as any;

        expect(truckType).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ITruckType should not enable id FormControl', () => {
        const formGroup = service.createTruckTypeFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewTruckType should disable id FormControl', () => {
        const formGroup = service.createTruckTypeFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
