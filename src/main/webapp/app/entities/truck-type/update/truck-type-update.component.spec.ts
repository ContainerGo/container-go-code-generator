import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TruckTypeService } from '../service/truck-type.service';
import { ITruckType } from '../truck-type.model';
import { TruckTypeFormService } from './truck-type-form.service';

import { TruckTypeUpdateComponent } from './truck-type-update.component';

describe('TruckType Management Update Component', () => {
  let comp: TruckTypeUpdateComponent;
  let fixture: ComponentFixture<TruckTypeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let truckTypeFormService: TruckTypeFormService;
  let truckTypeService: TruckTypeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), TruckTypeUpdateComponent],
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
      .overrideTemplate(TruckTypeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TruckTypeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    truckTypeFormService = TestBed.inject(TruckTypeFormService);
    truckTypeService = TestBed.inject(TruckTypeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const truckType: ITruckType = { id: 456 };

      activatedRoute.data = of({ truckType });
      comp.ngOnInit();

      expect(comp.truckType).toEqual(truckType);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITruckType>>();
      const truckType = { id: 123 };
      jest.spyOn(truckTypeFormService, 'getTruckType').mockReturnValue(truckType);
      jest.spyOn(truckTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ truckType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: truckType }));
      saveSubject.complete();

      // THEN
      expect(truckTypeFormService.getTruckType).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(truckTypeService.update).toHaveBeenCalledWith(expect.objectContaining(truckType));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITruckType>>();
      const truckType = { id: 123 };
      jest.spyOn(truckTypeFormService, 'getTruckType').mockReturnValue({ id: null });
      jest.spyOn(truckTypeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ truckType: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: truckType }));
      saveSubject.complete();

      // THEN
      expect(truckTypeFormService.getTruckType).toHaveBeenCalled();
      expect(truckTypeService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITruckType>>();
      const truckType = { id: 123 };
      jest.spyOn(truckTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ truckType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(truckTypeService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
