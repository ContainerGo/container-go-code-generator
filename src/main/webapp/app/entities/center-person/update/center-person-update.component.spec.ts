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
      const centerPerson: ICenterPerson = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
      const group: ICenterPersonGroup = { id: 'af3ef79c-3c81-4468-bb01-7ab1f510c9c2' };
      centerPerson.group = group;
      const groups: ICenterPersonGroup[] = [{ id: 'e17c523c-4fe5-4ced-950d-135b33cb7024' }];
      centerPerson.groups = groups;

      const centerPersonGroupCollection: ICenterPersonGroup[] = [{ id: '39817a30-d0aa-4c0e-ace3-1c2af5856a06' }];
      jest.spyOn(centerPersonGroupService, 'query').mockReturnValue(of(new HttpResponse({ body: centerPersonGroupCollection })));
      const additionalCenterPersonGroups = [group, ...groups];
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
      const centerPerson: ICenterPerson = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
      const group: ICenterPersonGroup = { id: '5c94ec8e-0fef-44ac-89c6-658ce6ef6904' };
      centerPerson.group = group;
      const groups: ICenterPersonGroup = { id: '9141d01d-bc73-4c33-a70d-c666203bd4aa' };
      centerPerson.groups = [groups];

      activatedRoute.data = of({ centerPerson });
      comp.ngOnInit();

      expect(comp.centerPersonGroupsSharedCollection).toContain(group);
      expect(comp.centerPersonGroupsSharedCollection).toContain(groups);
      expect(comp.centerPerson).toEqual(centerPerson);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICenterPerson>>();
      const centerPerson = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
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
      const centerPerson = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
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
      const centerPerson = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
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
        const entity = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
        jest.spyOn(centerPersonGroupService, 'compareCenterPersonGroup');
        comp.compareCenterPersonGroup(entity, entity2);
        expect(centerPersonGroupService.compareCenterPersonGroup).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
