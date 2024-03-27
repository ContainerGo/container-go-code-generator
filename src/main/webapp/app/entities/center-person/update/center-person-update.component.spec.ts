import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ICenterPersonGroup } from 'app/entities/center-person-group/center-person-group.model';
import { CenterPersonGroupService } from 'app/entities/center-person-group/service/center-person-group.service';
import { CenterPersonService } from '../service/center-person.service';
import { ICenterPerson } from '../center-person.model';
import { CenterPersonFormService } from './center-person-form.service';

import { CenterPersonUpdateComponent } from './center-person-update.component';

describe('CenterPerson Management Update Component', () => {
  let comp: CenterPersonUpdateComponent;
  let fixture: ComponentFixture<CenterPersonUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let centerPersonFormService: CenterPersonFormService;
  let centerPersonService: CenterPersonService;
  let centerPersonGroupService: CenterPersonGroupService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), CenterPersonUpdateComponent],
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
      .overrideTemplate(CenterPersonUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CenterPersonUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    centerPersonFormService = TestBed.inject(CenterPersonFormService);
    centerPersonService = TestBed.inject(CenterPersonService);
    centerPersonGroupService = TestBed.inject(CenterPersonGroupService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call CenterPersonGroup query and add missing value', () => {
      const centerPerson: ICenterPerson = { id: 456 };
      const groups: ICenterPersonGroup[] = [{ id: 32544 }];
      centerPerson.groups = groups;

      const centerPersonGroupCollection: ICenterPersonGroup[] = [{ id: 7291 }];
      jest.spyOn(centerPersonGroupService, 'query').mockReturnValue(of(new HttpResponse({ body: centerPersonGroupCollection })));
      const additionalCenterPersonGroups = [...groups];
      const expectedCollection: ICenterPersonGroup[] = [...additionalCenterPersonGroups, ...centerPersonGroupCollection];
      jest.spyOn(centerPersonGroupService, 'addCenterPersonGroupToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ centerPerson });
      comp.ngOnInit();

      expect(centerPersonGroupService.query).toHaveBeenCalled();
      expect(centerPersonGroupService.addCenterPersonGroupToCollectionIfMissing).toHaveBeenCalledWith(
        centerPersonGroupCollection,
        ...additionalCenterPersonGroups.map(expect.objectContaining),
      );
      expect(comp.centerPersonGroupsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const centerPerson: ICenterPerson = { id: 456 };
      const groups: ICenterPersonGroup = { id: 30199 };
      centerPerson.groups = [groups];

      activatedRoute.data = of({ centerPerson });
      comp.ngOnInit();

      expect(comp.centerPersonGroupsSharedCollection).toContain(groups);
      expect(comp.centerPerson).toEqual(centerPerson);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICenterPerson>>();
      const centerPerson = { id: 123 };
      jest.spyOn(centerPersonFormService, 'getCenterPerson').mockReturnValue(centerPerson);
      jest.spyOn(centerPersonService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ centerPerson });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: centerPerson }));
      saveSubject.complete();

      // THEN
      expect(centerPersonFormService.getCenterPerson).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(centerPersonService.update).toHaveBeenCalledWith(expect.objectContaining(centerPerson));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICenterPerson>>();
      const centerPerson = { id: 123 };
      jest.spyOn(centerPersonFormService, 'getCenterPerson').mockReturnValue({ id: null });
      jest.spyOn(centerPersonService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ centerPerson: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: centerPerson }));
      saveSubject.complete();

      // THEN
      expect(centerPersonFormService.getCenterPerson).toHaveBeenCalled();
      expect(centerPersonService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICenterPerson>>();
      const centerPerson = { id: 123 };
      jest.spyOn(centerPersonService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ centerPerson });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(centerPersonService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareCenterPersonGroup', () => {
      it('Should forward to centerPersonGroupService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(centerPersonGroupService, 'compareCenterPersonGroup');
        comp.compareCenterPersonGroup(entity, entity2);
        expect(centerPersonGroupService.compareCenterPersonGroup).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
