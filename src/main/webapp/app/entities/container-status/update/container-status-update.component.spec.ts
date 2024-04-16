import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IContainerStatusGroup } from 'app/entities/container-status-group/container-status-group.model';
import { ContainerStatusGroupService } from 'app/entities/container-status-group/service/container-status-group.service';
import { ContainerStatusService } from '../service/container-status.service';
import { IContainerStatus } from '../container-status.model';
import { ContainerStatusFormService } from './container-status-form.service';

import { ContainerStatusUpdateComponent } from './container-status-update.component';

describe('ContainerStatus Management Update Component', () => {
  let comp: ContainerStatusUpdateComponent;
  let fixture: ComponentFixture<ContainerStatusUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let containerStatusFormService: ContainerStatusFormService;
  let containerStatusService: ContainerStatusService;
  let containerStatusGroupService: ContainerStatusGroupService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, ContainerStatusUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(ContainerStatusUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ContainerStatusUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    containerStatusFormService = TestBed.inject(ContainerStatusFormService);
    containerStatusService = TestBed.inject(ContainerStatusService);
    containerStatusGroupService = TestBed.inject(ContainerStatusGroupService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call ContainerStatusGroup query and add missing value', () => {
      const containerStatus: IContainerStatus = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
      const group: IContainerStatusGroup = { id: '8336ce1d-43c3-458e-ab7d-a47a97cc2bbd' };
      containerStatus.group = group;

      const containerStatusGroupCollection: IContainerStatusGroup[] = [{ id: '4a0a95a7-4a15-4a14-a2a0-171b086ee853' }];
      jest.spyOn(containerStatusGroupService, 'query').mockReturnValue(of(new HttpResponse({ body: containerStatusGroupCollection })));
      const additionalContainerStatusGroups = [group];
      const expectedCollection: IContainerStatusGroup[] = [...additionalContainerStatusGroups, ...containerStatusGroupCollection];
      jest.spyOn(containerStatusGroupService, 'addContainerStatusGroupToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ containerStatus });
      comp.ngOnInit();

      expect(containerStatusGroupService.query).toHaveBeenCalled();
      expect(containerStatusGroupService.addContainerStatusGroupToCollectionIfMissing).toHaveBeenCalledWith(
        containerStatusGroupCollection,
        ...additionalContainerStatusGroups.map(expect.objectContaining),
      );
      expect(comp.containerStatusGroupsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const containerStatus: IContainerStatus = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
      const group: IContainerStatusGroup = { id: '906099ab-3246-40b4-8156-a0231054b6c4' };
      containerStatus.group = group;

      activatedRoute.data = of({ containerStatus });
      comp.ngOnInit();

      expect(comp.containerStatusGroupsSharedCollection).toContain(group);
      expect(comp.containerStatus).toEqual(containerStatus);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IContainerStatus>>();
      const containerStatus = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
      jest.spyOn(containerStatusFormService, 'getContainerStatus').mockReturnValue(containerStatus);
      jest.spyOn(containerStatusService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ containerStatus });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: containerStatus }));
      saveSubject.complete();

      // THEN
      expect(containerStatusFormService.getContainerStatus).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(containerStatusService.update).toHaveBeenCalledWith(expect.objectContaining(containerStatus));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IContainerStatus>>();
      const containerStatus = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
      jest.spyOn(containerStatusFormService, 'getContainerStatus').mockReturnValue({ id: null });
      jest.spyOn(containerStatusService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ containerStatus: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: containerStatus }));
      saveSubject.complete();

      // THEN
      expect(containerStatusFormService.getContainerStatus).toHaveBeenCalled();
      expect(containerStatusService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IContainerStatus>>();
      const containerStatus = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
      jest.spyOn(containerStatusService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ containerStatus });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(containerStatusService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareContainerStatusGroup', () => {
      it('Should forward to containerStatusGroupService', () => {
        const entity = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
        jest.spyOn(containerStatusGroupService, 'compareContainerStatusGroup');
        comp.compareContainerStatusGroup(entity, entity2);
        expect(containerStatusGroupService.compareContainerStatusGroup).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
