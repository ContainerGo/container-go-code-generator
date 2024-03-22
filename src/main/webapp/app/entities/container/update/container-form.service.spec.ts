import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../container.test-samples';

import { ContainerFormService } from './container-form.service';

describe('Container Form Service', () => {
  let service: ContainerFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ContainerFormService);
  });

  describe('Service methods', () => {
    describe('createContainerFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createContainerFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            contNo: expect.any(Object),
            estimatedPrice: expect.any(Object),
            distance: expect.any(Object),
            desiredPrice: expect.any(Object),
            additionalRequirements: expect.any(Object),
            dropoffContact: expect.any(Object),
            dropoffContactPhone: expect.any(Object),
            dropoffAddress: expect.any(Object),
            dropoffLat: expect.any(Object),
            dropoffLng: expect.any(Object),
            dropoffUntilDate: expect.any(Object),
            state: expect.any(Object),
            shipperId: expect.any(Object),
            carrierId: expect.any(Object),
            totalWeight: expect.any(Object),
            pickupFromDate: expect.any(Object),
            biddingFromDate: expect.any(Object),
            biddingUntilDate: expect.any(Object),
            dropoffProvice: expect.any(Object),
            dropoffDistrict: expect.any(Object),
            dropoffWard: expect.any(Object),
            type: expect.any(Object),
            status: expect.any(Object),
            truckType: expect.any(Object),
            truck: expect.any(Object),
          }),
        );
      });

      it('passing IContainer should create a new form with FormGroup', () => {
        const formGroup = service.createContainerFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            contNo: expect.any(Object),
            estimatedPrice: expect.any(Object),
            distance: expect.any(Object),
            desiredPrice: expect.any(Object),
            additionalRequirements: expect.any(Object),
            dropoffContact: expect.any(Object),
            dropoffContactPhone: expect.any(Object),
            dropoffAddress: expect.any(Object),
            dropoffLat: expect.any(Object),
            dropoffLng: expect.any(Object),
            dropoffUntilDate: expect.any(Object),
            state: expect.any(Object),
            shipperId: expect.any(Object),
            carrierId: expect.any(Object),
            totalWeight: expect.any(Object),
            pickupFromDate: expect.any(Object),
            biddingFromDate: expect.any(Object),
            biddingUntilDate: expect.any(Object),
            dropoffProvice: expect.any(Object),
            dropoffDistrict: expect.any(Object),
            dropoffWard: expect.any(Object),
            type: expect.any(Object),
            status: expect.any(Object),
            truckType: expect.any(Object),
            truck: expect.any(Object),
          }),
        );
      });
    });

    describe('getContainer', () => {
      it('should return NewContainer for default Container initial value', () => {
        const formGroup = service.createContainerFormGroup(sampleWithNewData);

        const container = service.getContainer(formGroup) as any;

        expect(container).toMatchObject(sampleWithNewData);
      });

      it('should return NewContainer for empty Container initial value', () => {
        const formGroup = service.createContainerFormGroup();

        const container = service.getContainer(formGroup) as any;

        expect(container).toMatchObject({});
      });

      it('should return IContainer', () => {
        const formGroup = service.createContainerFormGroup(sampleWithRequiredData);

        const container = service.getContainer(formGroup) as any;

        expect(container).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IContainer should not enable id FormControl', () => {
        const formGroup = service.createContainerFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewContainer should disable id FormControl', () => {
        const formGroup = service.createContainerFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
