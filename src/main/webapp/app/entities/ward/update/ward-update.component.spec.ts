import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { WardService } from '../service/ward.service';
import { IWard } from '../ward.model';
import { WardFormService } from './ward-form.service';

import { WardUpdateComponent } from './ward-update.component';

describe('Ward Management Update Component', () => {
  let comp: WardUpdateComponent;
  let fixture: ComponentFixture<WardUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let wardFormService: WardFormService;
  let wardService: WardService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, WardUpdateComponent],
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
      .overrideTemplate(WardUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(WardUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    wardFormService = TestBed.inject(WardFormService);
    wardService = TestBed.inject(WardService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const ward: IWard = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };

      activatedRoute.data = of({ ward });
      comp.ngOnInit();

      expect(comp.ward).toEqual(ward);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IWard>>();
      const ward = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
      jest.spyOn(wardFormService, 'getWard').mockReturnValue(ward);
      jest.spyOn(wardService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ward });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: ward }));
      saveSubject.complete();

      // THEN
      expect(wardFormService.getWard).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(wardService.update).toHaveBeenCalledWith(expect.objectContaining(ward));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IWard>>();
      const ward = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
      jest.spyOn(wardFormService, 'getWard').mockReturnValue({ id: null });
      jest.spyOn(wardService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ward: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: ward }));
      saveSubject.complete();

      // THEN
      expect(wardFormService.getWard).toHaveBeenCalled();
      expect(wardService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IWard>>();
      const ward = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
      jest.spyOn(wardService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ward });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(wardService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
