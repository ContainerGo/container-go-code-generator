import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TruckService } from '../service/truck.service';
import { ITruck } from '../truck.model';
import { TruckFormService } from './truck-form.service';

import { TruckUpdateComponent } from './truck-update.component';

describe('Truck Management Update Component', () => {
  let comp: TruckUpdateComponent;
  let fixture: ComponentFixture<TruckUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let truckFormService: TruckFormService;
  let truckService: TruckService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), TruckUpdateComponent],
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
      .overrideTemplate(TruckUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TruckUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    truckFormService = TestBed.inject(TruckFormService);
    truckService = TestBed.inject(TruckService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const truck: ITruck = { id: 456 };

      activatedRoute.data = of({ truck });
      comp.ngOnInit();

      expect(comp.truck).toEqual(truck);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITruck>>();
      const truck = { id: 123 };
      jest.spyOn(truckFormService, 'getTruck').mockReturnValue(truck);
      jest.spyOn(truckService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ truck });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: truck }));
      saveSubject.complete();

      // THEN
      expect(truckFormService.getTruck).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(truckService.update).toHaveBeenCalledWith(expect.objectContaining(truck));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITruck>>();
      const truck = { id: 123 };
      jest.spyOn(truckFormService, 'getTruck').mockReturnValue({ id: null });
      jest.spyOn(truckService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ truck: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: truck }));
      saveSubject.complete();

      // THEN
      expect(truckFormService.getTruck).toHaveBeenCalled();
      expect(truckService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITruck>>();
      const truck = { id: 123 };
      jest.spyOn(truckService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ truck });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(truckService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
