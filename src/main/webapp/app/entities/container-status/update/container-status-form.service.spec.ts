import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../container-status.test-samples';

import { ContainerStatusFormService } from './container-status-form.service';

describe('ContainerStatus Form Service', () => {
  let service: ContainerStatusFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ContainerStatusFormService);
  });

  describe('Service methods', () => {
    describe('createContainerStatusFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createContainerStatusFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            name: expect.any(Object),
            description: expect.any(Object),
            group: expect.any(Object),
          }),
        );
      });

      it('passing IContainerStatus should create a new form with FormGroup', () => {
        const formGroup = service.createContainerStatusFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            name: expect.any(Object),
            description: expect.any(Object),
            group: expect.any(Object),
          }),
        );
      });
    });

    describe('getContainerStatus', () => {
      it('should return NewContainerStatus for default ContainerStatus initial value', () => {
        const formGroup = service.createContainerStatusFormGroup(sampleWithNewData);

        const containerStatus = service.getContainerStatus(formGroup) as any;

        expect(containerStatus).toMatchObject(sampleWithNewData);
      });

      it('should return NewContainerStatus for empty ContainerStatus initial value', () => {
        const formGroup = service.createContainerStatusFormGroup();

        const containerStatus = service.getContainerStatus(formGroup) as any;

        expect(containerStatus).toMatchObject({});
      });

      it('should return IContainerStatus', () => {
        const formGroup = service.createContainerStatusFormGroup(sampleWithRequiredData);

        const containerStatus = service.getContainerStatus(formGroup) as any;

        expect(containerStatus).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IContainerStatus should not enable id FormControl', () => {
        const formGroup = service.createContainerStatusFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewContainerStatus should disable id FormControl', () => {
        const formGroup = service.createContainerStatusFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
