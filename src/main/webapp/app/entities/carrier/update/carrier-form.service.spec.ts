import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../carrier.test-samples';

import { CarrierFormService } from './carrier-form.service';

describe('Carrier Form Service', () => {
  let service: CarrierFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CarrierFormService);
  });

  describe('Service methods', () => {
    describe('createCarrierFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCarrierFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            name: expect.any(Object),
            address: expect.any(Object),
            taxCode: expect.any(Object),
            bankAccount: expect.any(Object),
            bankName: expect.any(Object),
            accountName: expect.any(Object),
            branchName: expect.any(Object),
          }),
        );
      });

      it('passing ICarrier should create a new form with FormGroup', () => {
        const formGroup = service.createCarrierFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            name: expect.any(Object),
            address: expect.any(Object),
            taxCode: expect.any(Object),
            bankAccount: expect.any(Object),
            bankName: expect.any(Object),
            accountName: expect.any(Object),
            branchName: expect.any(Object),
          }),
        );
      });
    });

    describe('getCarrier', () => {
      it('should return NewCarrier for default Carrier initial value', () => {
        const formGroup = service.createCarrierFormGroup(sampleWithNewData);

        const carrier = service.getCarrier(formGroup) as any;

        expect(carrier).toMatchObject(sampleWithNewData);
      });

      it('should return NewCarrier for empty Carrier initial value', () => {
        const formGroup = service.createCarrierFormGroup();

        const carrier = service.getCarrier(formGroup) as any;

        expect(carrier).toMatchObject({});
      });

      it('should return ICarrier', () => {
        const formGroup = service.createCarrierFormGroup(sampleWithRequiredData);

        const carrier = service.getCarrier(formGroup) as any;

        expect(carrier).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICarrier should not enable id FormControl', () => {
        const formGroup = service.createCarrierFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCarrier should disable id FormControl', () => {
        const formGroup = service.createCarrierFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
