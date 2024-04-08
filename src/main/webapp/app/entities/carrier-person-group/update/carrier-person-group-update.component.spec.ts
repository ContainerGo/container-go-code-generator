import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CarrierPersonGroupService } from '../service/carrier-person-group.service';
import { ICarrierPersonGroup } from '../carrier-person-group.model';
import { CarrierPersonGroupFormService } from './carrier-person-group-form.service';

import { CarrierPersonGroupUpdateComponent } from './carrier-person-group-update.component';

describe('CarrierPersonGroup Management Update Component', () => {
  let comp: CarrierPersonGroupUpdateComponent;
  let fixture: ComponentFixture<CarrierPersonGroupUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let carrierPersonGroupFormService: CarrierPersonGroupFormService;
  let carrierPersonGroupService: CarrierPersonGroupService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), CarrierPersonGroupUpdateComponent],
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
      .overrideTemplate(CarrierPersonGroupUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CarrierPersonGroupUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    carrierPersonGroupFormService = TestBed.inject(CarrierPersonGroupFormService);
    carrierPersonGroupService = TestBed.inject(CarrierPersonGroupService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const carrierPersonGroup: ICarrierPersonGroup = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };

      activatedRoute.data = of({ carrierPersonGroup });
      comp.ngOnInit();

      expect(comp.carrierPersonGroup).toEqual(carrierPersonGroup);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICarrierPersonGroup>>();
      const carrierPersonGroup = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
      jest.spyOn(carrierPersonGroupFormService, 'getCarrierPersonGroup').mockReturnValue(carrierPersonGroup);
      jest.spyOn(carrierPersonGroupService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ carrierPersonGroup });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: carrierPersonGroup }));
      saveSubject.complete();

      // THEN
      expect(carrierPersonGroupFormService.getCarrierPersonGroup).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(carrierPersonGroupService.update).toHaveBeenCalledWith(expect.objectContaining(carrierPersonGroup));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICarrierPersonGroup>>();
      const carrierPersonGroup = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
      jest.spyOn(carrierPersonGroupFormService, 'getCarrierPersonGroup').mockReturnValue({ id: null });
      jest.spyOn(carrierPersonGroupService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ carrierPersonGroup: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: carrierPersonGroup }));
      saveSubject.complete();

      // THEN
      expect(carrierPersonGroupFormService.getCarrierPersonGroup).toHaveBeenCalled();
      expect(carrierPersonGroupService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICarrierPersonGroup>>();
      const carrierPersonGroup = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
      jest.spyOn(carrierPersonGroupService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ carrierPersonGroup });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(carrierPersonGroupService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
