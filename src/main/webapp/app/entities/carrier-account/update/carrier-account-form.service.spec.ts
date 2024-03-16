import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../carrier-account.test-samples';

import { CarrierAccountFormService } from './carrier-account-form.service';

describe('CarrierAccount Form Service', () => {
  let service: CarrierAccountFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CarrierAccountFormService);
  });

  describe('Service methods', () => {
    describe('createCarrierAccountFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCarrierAccountFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            phone: expect.any(Object),
            email: expect.any(Object),
            address: expect.any(Object),
            carrier: expect.any(Object),
          }),
        );
      });

      it('passing ICarrierAccount should create a new form with FormGroup', () => {
        const formGroup = service.createCarrierAccountFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            phone: expect.any(Object),
            email: expect.any(Object),
            address: expect.any(Object),
            carrier: expect.any(Object),
          }),
        );
      });
    });

    describe('getCarrierAccount', () => {
      it('should return NewCarrierAccount for default CarrierAccount initial value', () => {
        const formGroup = service.createCarrierAccountFormGroup(sampleWithNewData);

        const carrierAccount = service.getCarrierAccount(formGroup) as any;

        expect(carrierAccount).toMatchObject(sampleWithNewData);
      });

      it('should return NewCarrierAccount for empty CarrierAccount initial value', () => {
        const formGroup = service.createCarrierAccountFormGroup();

        const carrierAccount = service.getCarrierAccount(formGroup) as any;

        expect(carrierAccount).toMatchObject({});
      });

      it('should return ICarrierAccount', () => {
        const formGroup = service.createCarrierAccountFormGroup(sampleWithRequiredData);

        const carrierAccount = service.getCarrierAccount(formGroup) as any;

        expect(carrierAccount).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICarrierAccount should not enable id FormControl', () => {
        const formGroup = service.createCarrierAccountFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCarrierAccount should disable id FormControl', () => {
        const formGroup = service.createCarrierAccountFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
