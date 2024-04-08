import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ProviceService } from '../service/provice.service';
import { IProvice } from '../provice.model';
import { ProviceFormService } from './provice-form.service';

import { ProviceUpdateComponent } from './provice-update.component';

describe('Provice Management Update Component', () => {
  let comp: ProviceUpdateComponent;
  let fixture: ComponentFixture<ProviceUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let proviceFormService: ProviceFormService;
  let proviceService: ProviceService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), ProviceUpdateComponent],
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
      .overrideTemplate(ProviceUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ProviceUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    proviceFormService = TestBed.inject(ProviceFormService);
    proviceService = TestBed.inject(ProviceService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const provice: IProvice = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };

      activatedRoute.data = of({ provice });
      comp.ngOnInit();

      expect(comp.provice).toEqual(provice);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProvice>>();
      const provice = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
      jest.spyOn(proviceFormService, 'getProvice').mockReturnValue(provice);
      jest.spyOn(proviceService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ provice });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: provice }));
      saveSubject.complete();

      // THEN
      expect(proviceFormService.getProvice).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(proviceService.update).toHaveBeenCalledWith(expect.objectContaining(provice));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProvice>>();
      const provice = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
      jest.spyOn(proviceFormService, 'getProvice').mockReturnValue({ id: null });
      jest.spyOn(proviceService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ provice: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: provice }));
      saveSubject.complete();

      // THEN
      expect(proviceFormService.getProvice).toHaveBeenCalled();
      expect(proviceService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProvice>>();
      const provice = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
      jest.spyOn(proviceService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ provice });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(proviceService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
