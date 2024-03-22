import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../container-status-group.test-samples';

import { ContainerStatusGroupFormService } from './container-status-group-form.service';

describe('ContainerStatusGroup Form Service', () => {
  let service: ContainerStatusGroupFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ContainerStatusGroupFormService);
  });

  describe('Service methods', () => {
    describe('createContainerStatusGroupFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createContainerStatusGroupFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            name: expect.any(Object),
            description: expect.any(Object),
          }),
        );
      });

      it('passing IContainerStatusGroup should create a new form with FormGroup', () => {
        const formGroup = service.createContainerStatusGroupFormGroup(sampleWithRequiredData);

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

    describe('getContainerStatusGroup', () => {
      it('should return NewContainerStatusGroup for default ContainerStatusGroup initial value', () => {
        const formGroup = service.createContainerStatusGroupFormGroup(sampleWithNewData);

        const containerStatusGroup = service.getContainerStatusGroup(formGroup) as any;

        expect(containerStatusGroup).toMatchObject(sampleWithNewData);
      });

      it('should return NewContainerStatusGroup for empty ContainerStatusGroup initial value', () => {
        const formGroup = service.createContainerStatusGroupFormGroup();

        const containerStatusGroup = service.getContainerStatusGroup(formGroup) as any;

        expect(containerStatusGroup).toMatchObject({});
      });

      it('should return IContainerStatusGroup', () => {
        const formGroup = service.createContainerStatusGroupFormGroup(sampleWithRequiredData);

        const containerStatusGroup = service.getContainerStatusGroup(formGroup) as any;

        expect(containerStatusGroup).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IContainerStatusGroup should not enable id FormControl', () => {
        const formGroup = service.createContainerStatusGroupFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewContainerStatusGroup should disable id FormControl', () => {
        const formGroup = service.createContainerStatusGroupFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
