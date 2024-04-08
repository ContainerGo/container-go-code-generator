import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CenterPersonGroupService } from '../service/center-person-group.service';
import { ICenterPersonGroup } from '../center-person-group.model';
import { CenterPersonGroupFormService } from './center-person-group-form.service';

import { CenterPersonGroupUpdateComponent } from './center-person-group-update.component';

describe('CenterPersonGroup Management Update Component', () => {
  let comp: CenterPersonGroupUpdateComponent;
  let fixture: ComponentFixture<CenterPersonGroupUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let centerPersonGroupFormService: CenterPersonGroupFormService;
  let centerPersonGroupService: CenterPersonGroupService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), CenterPersonGroupUpdateComponent],
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
      .overrideTemplate(CenterPersonGroupUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CenterPersonGroupUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    centerPersonGroupFormService = TestBed.inject(CenterPersonGroupFormService);
    centerPersonGroupService = TestBed.inject(CenterPersonGroupService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const centerPersonGroup: ICenterPersonGroup = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };

      activatedRoute.data = of({ centerPersonGroup });
      comp.ngOnInit();

      expect(comp.centerPersonGroup).toEqual(centerPersonGroup);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICenterPersonGroup>>();
      const centerPersonGroup = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
      jest.spyOn(centerPersonGroupFormService, 'getCenterPersonGroup').mockReturnValue(centerPersonGroup);
      jest.spyOn(centerPersonGroupService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ centerPersonGroup });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: centerPersonGroup }));
      saveSubject.complete();

      // THEN
      expect(centerPersonGroupFormService.getCenterPersonGroup).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(centerPersonGroupService.update).toHaveBeenCalledWith(expect.objectContaining(centerPersonGroup));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICenterPersonGroup>>();
      const centerPersonGroup = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
      jest.spyOn(centerPersonGroupFormService, 'getCenterPersonGroup').mockReturnValue({ id: null });
      jest.spyOn(centerPersonGroupService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ centerPersonGroup: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: centerPersonGroup }));
      saveSubject.complete();

      // THEN
      expect(centerPersonGroupFormService.getCenterPersonGroup).toHaveBeenCalled();
      expect(centerPersonGroupService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICenterPersonGroup>>();
      const centerPersonGroup = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
      jest.spyOn(centerPersonGroupService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ centerPersonGroup });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(centerPersonGroupService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
