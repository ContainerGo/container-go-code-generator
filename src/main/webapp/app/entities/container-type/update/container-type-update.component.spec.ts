import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { ContainerTypeService } from '../service/container-type.service';
import { IContainerType } from '../container-type.model';
import { ContainerTypeFormService } from './container-type-form.service';

import { ContainerTypeUpdateComponent } from './container-type-update.component';

describe('ContainerType Management Update Component', () => {
  let comp: ContainerTypeUpdateComponent;
  let fixture: ComponentFixture<ContainerTypeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let containerTypeFormService: ContainerTypeFormService;
  let containerTypeService: ContainerTypeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, ContainerTypeUpdateComponent],
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
      .overrideTemplate(ContainerTypeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ContainerTypeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    containerTypeFormService = TestBed.inject(ContainerTypeFormService);
    containerTypeService = TestBed.inject(ContainerTypeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const containerType: IContainerType = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };

      activatedRoute.data = of({ containerType });
      comp.ngOnInit();

      expect(comp.containerType).toEqual(containerType);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IContainerType>>();
      const containerType = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
      jest.spyOn(containerTypeFormService, 'getContainerType').mockReturnValue(containerType);
      jest.spyOn(containerTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ containerType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: containerType }));
      saveSubject.complete();

      // THEN
      expect(containerTypeFormService.getContainerType).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(containerTypeService.update).toHaveBeenCalledWith(expect.objectContaining(containerType));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IContainerType>>();
      const containerType = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
      jest.spyOn(containerTypeFormService, 'getContainerType').mockReturnValue({ id: null });
      jest.spyOn(containerTypeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ containerType: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: containerType }));
      saveSubject.complete();

      // THEN
      expect(containerTypeFormService.getContainerType).toHaveBeenCalled();
      expect(containerTypeService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IContainerType>>();
      const containerType = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
      jest.spyOn(containerTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ containerType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(containerTypeService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
