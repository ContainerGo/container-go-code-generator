import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../container-type.test-samples';

import { ContainerTypeFormService } from './container-type-form.service';

describe('ContainerType Form Service', () => {
  let service: ContainerTypeFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ContainerTypeFormService);
  });

  describe('Service methods', () => {
    describe('createContainerTypeFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createContainerTypeFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            name: expect.any(Object),
            description: expect.any(Object),
          }),
        );
      });

      it('passing IContainerType should create a new form with FormGroup', () => {
        const formGroup = service.createContainerTypeFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            name: expect.any(Object),
            description: expect.any(Object),
          }),
        );
      });
    });

    describe('getContainerType', () => {
      it('should return NewContainerType for default ContainerType initial value', () => {
        const formGroup = service.createContainerTypeFormGroup(sampleWithNewData);

        const containerType = service.getContainerType(formGroup) as any;

        expect(containerType).toMatchObject(sampleWithNewData);
      });

      it('should return NewContainerType for empty ContainerType initial value', () => {
        const formGroup = service.createContainerTypeFormGroup();

        const containerType = service.getContainerType(formGroup) as any;

        expect(containerType).toMatchObject({});
      });

      it('should return IContainerType', () => {
        const formGroup = service.createContainerTypeFormGroup(sampleWithRequiredData);

        const containerType = service.getContainerType(formGroup) as any;

        expect(containerType).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IContainerType should not enable id FormControl', () => {
        const formGroup = service.createContainerTypeFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewContainerType should disable id FormControl', () => {
        const formGroup = service.createContainerTypeFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
