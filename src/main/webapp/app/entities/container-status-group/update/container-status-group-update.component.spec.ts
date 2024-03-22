import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ContainerStatusGroupService } from '../service/container-status-group.service';
import { IContainerStatusGroup } from '../container-status-group.model';
import { ContainerStatusGroupFormService } from './container-status-group-form.service';

import { ContainerStatusGroupUpdateComponent } from './container-status-group-update.component';

describe('ContainerStatusGroup Management Update Component', () => {
  let comp: ContainerStatusGroupUpdateComponent;
  let fixture: ComponentFixture<ContainerStatusGroupUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let containerStatusGroupFormService: ContainerStatusGroupFormService;
  let containerStatusGroupService: ContainerStatusGroupService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), ContainerStatusGroupUpdateComponent],
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
      .overrideTemplate(ContainerStatusGroupUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ContainerStatusGroupUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    containerStatusGroupFormService = TestBed.inject(ContainerStatusGroupFormService);
    containerStatusGroupService = TestBed.inject(ContainerStatusGroupService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const containerStatusGroup: IContainerStatusGroup = { id: 456 };

      activatedRoute.data = of({ containerStatusGroup });
      comp.ngOnInit();

      expect(comp.containerStatusGroup).toEqual(containerStatusGroup);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IContainerStatusGroup>>();
      const containerStatusGroup = { id: 123 };
      jest.spyOn(containerStatusGroupFormService, 'getContainerStatusGroup').mockReturnValue(containerStatusGroup);
      jest.spyOn(containerStatusGroupService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ containerStatusGroup });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: containerStatusGroup }));
      saveSubject.complete();

      // THEN
      expect(containerStatusGroupFormService.getContainerStatusGroup).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(containerStatusGroupService.update).toHaveBeenCalledWith(expect.objectContaining(containerStatusGroup));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IContainerStatusGroup>>();
      const containerStatusGroup = { id: 123 };
      jest.spyOn(containerStatusGroupFormService, 'getContainerStatusGroup').mockReturnValue({ id: null });
      jest.spyOn(containerStatusGroupService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ containerStatusGroup: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: containerStatusGroup }));
      saveSubject.complete();

      // THEN
      expect(containerStatusGroupFormService.getContainerStatusGroup).toHaveBeenCalled();
      expect(containerStatusGroupService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IContainerStatusGroup>>();
      const containerStatusGroup = { id: 123 };
      jest.spyOn(containerStatusGroupService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ containerStatusGroup });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(containerStatusGroupService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
