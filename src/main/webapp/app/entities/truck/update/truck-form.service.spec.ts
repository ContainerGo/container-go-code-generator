import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../truck.test-samples';

import { TruckFormService } from './truck-form.service';

describe('Truck Form Service', () => {
  let service: TruckFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TruckFormService);
  });

  describe('Service methods', () => {
    describe('createTruckFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createTruckFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            name: expect.any(Object),
          }),
        );
      });

      it('passing ITruck should create a new form with FormGroup', () => {
        const formGroup = service.createTruckFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            name: expect.any(Object),
          }),
        );
      });
    });

    describe('getTruck', () => {
      it('should return NewTruck for default Truck initial value', () => {
        const formGroup = service.createTruckFormGroup(sampleWithNewData);

        const truck = service.getTruck(formGroup) as any;

        expect(truck).toMatchObject(sampleWithNewData);
      });

      it('should return NewTruck for empty Truck initial value', () => {
        const formGroup = service.createTruckFormGroup();

        const truck = service.getTruck(formGroup) as any;

        expect(truck).toMatchObject({});
      });

      it('should return ITruck', () => {
        const formGroup = service.createTruckFormGroup(sampleWithRequiredData);

        const truck = service.getTruck(formGroup) as any;

        expect(truck).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ITruck should not enable id FormControl', () => {
        const formGroup = service.createTruckFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewTruck should disable id FormControl', () => {
        const formGroup = service.createTruckFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
