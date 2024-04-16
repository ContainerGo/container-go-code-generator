import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { ContainerOwnerService } from '../service/container-owner.service';
import { IContainerOwner } from '../container-owner.model';
import { ContainerOwnerFormService } from './container-owner-form.service';

import { ContainerOwnerUpdateComponent } from './container-owner-update.component';

describe('ContainerOwner Management Update Component', () => {
  let comp: ContainerOwnerUpdateComponent;
  let fixture: ComponentFixture<ContainerOwnerUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let containerOwnerFormService: ContainerOwnerFormService;
  let containerOwnerService: ContainerOwnerService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, ContainerOwnerUpdateComponent],
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
      .overrideTemplate(ContainerOwnerUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ContainerOwnerUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    containerOwnerFormService = TestBed.inject(ContainerOwnerFormService);
    containerOwnerService = TestBed.inject(ContainerOwnerService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const containerOwner: IContainerOwner = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };

      activatedRoute.data = of({ containerOwner });
      comp.ngOnInit();

      expect(comp.containerOwner).toEqual(containerOwner);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IContainerOwner>>();
      const containerOwner = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
      jest.spyOn(containerOwnerFormService, 'getContainerOwner').mockReturnValue(containerOwner);
      jest.spyOn(containerOwnerService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ containerOwner });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: containerOwner }));
      saveSubject.complete();

      // THEN
      expect(containerOwnerFormService.getContainerOwner).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(containerOwnerService.update).toHaveBeenCalledWith(expect.objectContaining(containerOwner));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IContainerOwner>>();
      const containerOwner = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
      jest.spyOn(containerOwnerFormService, 'getContainerOwner').mockReturnValue({ id: null });
      jest.spyOn(containerOwnerService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ containerOwner: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: containerOwner }));
      saveSubject.complete();

      // THEN
      expect(containerOwnerFormService.getContainerOwner).toHaveBeenCalled();
      expect(containerOwnerService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IContainerOwner>>();
      const containerOwner = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
      jest.spyOn(containerOwnerService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ containerOwner });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(containerOwnerService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
