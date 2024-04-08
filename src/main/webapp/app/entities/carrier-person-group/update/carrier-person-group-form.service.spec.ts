import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../carrier-person-group.test-samples';

import { CarrierPersonGroupFormService } from './carrier-person-group-form.service';

describe('CarrierPersonGroup Form Service', () => {
  let service: CarrierPersonGroupFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CarrierPersonGroupFormService);
  });

  describe('Service methods', () => {
    describe('createCarrierPersonGroupFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCarrierPersonGroupFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
          }),
        );
      });

      it('passing ICarrierPersonGroup should create a new form with FormGroup', () => {
        const formGroup = service.createCarrierPersonGroupFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
          }),
        );
      });
    });

    describe('getCarrierPersonGroup', () => {
      it('should return NewCarrierPersonGroup for default CarrierPersonGroup initial value', () => {
        const formGroup = service.createCarrierPersonGroupFormGroup(sampleWithNewData);

        const carrierPersonGroup = service.getCarrierPersonGroup(formGroup) as any;

        expect(carrierPersonGroup).toMatchObject(sampleWithNewData);
      });

      it('should return NewCarrierPersonGroup for empty CarrierPersonGroup initial value', () => {
        const formGroup = service.createCarrierPersonGroupFormGroup();

        const carrierPersonGroup = service.getCarrierPersonGroup(formGroup) as any;

        expect(carrierPersonGroup).toMatchObject({});
      });

      it('should return ICarrierPersonGroup', () => {
        const formGroup = service.createCarrierPersonGroupFormGroup(sampleWithRequiredData);

        const carrierPersonGroup = service.getCarrierPersonGroup(formGroup) as any;

        expect(carrierPersonGroup).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICarrierPersonGroup should not enable id FormControl', () => {
        const formGroup = service.createCarrierPersonGroupFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCarrierPersonGroup should disable id FormControl', () => {
        const formGroup = service.createCarrierPersonGroupFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
