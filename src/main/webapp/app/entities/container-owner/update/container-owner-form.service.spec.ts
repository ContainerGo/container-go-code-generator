import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../container-owner.test-samples';

import { ContainerOwnerFormService } from './container-owner-form.service';

describe('ContainerOwner Form Service', () => {
  let service: ContainerOwnerFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ContainerOwnerFormService);
  });

  describe('Service methods', () => {
    describe('createContainerOwnerFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createContainerOwnerFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            phone: expect.any(Object),
            email: expect.any(Object),
            address: expect.any(Object),
          }),
        );
      });

      it('passing IContainerOwner should create a new form with FormGroup', () => {
        const formGroup = service.createContainerOwnerFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            phone: expect.any(Object),
            email: expect.any(Object),
            address: expect.any(Object),
          }),
        );
      });
    });

    describe('getContainerOwner', () => {
      it('should return NewContainerOwner for default ContainerOwner initial value', () => {
        const formGroup = service.createContainerOwnerFormGroup(sampleWithNewData);

        const containerOwner = service.getContainerOwner(formGroup) as any;

        expect(containerOwner).toMatchObject(sampleWithNewData);
      });

      it('should return NewContainerOwner for empty ContainerOwner initial value', () => {
        const formGroup = service.createContainerOwnerFormGroup();

        const containerOwner = service.getContainerOwner(formGroup) as any;

        expect(containerOwner).toMatchObject({});
      });

      it('should return IContainerOwner', () => {
        const formGroup = service.createContainerOwnerFormGroup(sampleWithRequiredData);

        const containerOwner = service.getContainerOwner(formGroup) as any;

        expect(containerOwner).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IContainerOwner should not enable id FormControl', () => {
        const formGroup = service.createContainerOwnerFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewContainerOwner should disable id FormControl', () => {
        const formGroup = service.createContainerOwnerFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
