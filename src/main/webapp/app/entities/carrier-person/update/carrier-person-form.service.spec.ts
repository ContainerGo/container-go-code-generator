import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../carrier-person.test-samples';

import { CarrierPersonFormService } from './carrier-person-form.service';

describe('CarrierPerson Form Service', () => {
  let service: CarrierPersonFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CarrierPersonFormService);
  });

  describe('Service methods', () => {
    describe('createCarrierPersonFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCarrierPersonFormGroup();

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

      it('passing ICarrierPerson should create a new form with FormGroup', () => {
        const formGroup = service.createCarrierPersonFormGroup(sampleWithRequiredData);

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

    describe('getCarrierPerson', () => {
      it('should return NewCarrierPerson for default CarrierPerson initial value', () => {
        const formGroup = service.createCarrierPersonFormGroup(sampleWithNewData);

        const carrierPerson = service.getCarrierPerson(formGroup) as any;

        expect(carrierPerson).toMatchObject(sampleWithNewData);
      });

      it('should return NewCarrierPerson for empty CarrierPerson initial value', () => {
        const formGroup = service.createCarrierPersonFormGroup();

        const carrierPerson = service.getCarrierPerson(formGroup) as any;

        expect(carrierPerson).toMatchObject({});
      });

      it('should return ICarrierPerson', () => {
        const formGroup = service.createCarrierPersonFormGroup(sampleWithRequiredData);

        const carrierPerson = service.getCarrierPerson(formGroup) as any;

        expect(carrierPerson).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICarrierPerson should not enable id FormControl', () => {
        const formGroup = service.createCarrierPersonFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCarrierPerson should disable id FormControl', () => {
        const formGroup = service.createCarrierPersonFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
