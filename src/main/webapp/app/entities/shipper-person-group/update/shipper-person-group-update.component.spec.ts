import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { ShipperPersonGroupService } from '../service/shipper-person-group.service';
import { IShipperPersonGroup } from '../shipper-person-group.model';
import { ShipperPersonGroupFormService } from './shipper-person-group-form.service';

import { ShipperPersonGroupUpdateComponent } from './shipper-person-group-update.component';

describe('ShipperPersonGroup Management Update Component', () => {
  let comp: ShipperPersonGroupUpdateComponent;
  let fixture: ComponentFixture<ShipperPersonGroupUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let shipperPersonGroupFormService: ShipperPersonGroupFormService;
  let shipperPersonGroupService: ShipperPersonGroupService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, ShipperPersonGroupUpdateComponent],
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
      .overrideTemplate(ShipperPersonGroupUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ShipperPersonGroupUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    shipperPersonGroupFormService = TestBed.inject(ShipperPersonGroupFormService);
    shipperPersonGroupService = TestBed.inject(ShipperPersonGroupService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const shipperPersonGroup: IShipperPersonGroup = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };

      activatedRoute.data = of({ shipperPersonGroup });
      comp.ngOnInit();

      expect(comp.shipperPersonGroup).toEqual(shipperPersonGroup);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IShipperPersonGroup>>();
      const shipperPersonGroup = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
      jest.spyOn(shipperPersonGroupFormService, 'getShipperPersonGroup').mockReturnValue(shipperPersonGroup);
      jest.spyOn(shipperPersonGroupService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ shipperPersonGroup });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: shipperPersonGroup }));
      saveSubject.complete();

      // THEN
      expect(shipperPersonGroupFormService.getShipperPersonGroup).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(shipperPersonGroupService.update).toHaveBeenCalledWith(expect.objectContaining(shipperPersonGroup));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IShipperPersonGroup>>();
      const shipperPersonGroup = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
      jest.spyOn(shipperPersonGroupFormService, 'getShipperPersonGroup').mockReturnValue({ id: null });
      jest.spyOn(shipperPersonGroupService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ shipperPersonGroup: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: shipperPersonGroup }));
      saveSubject.complete();

      // THEN
      expect(shipperPersonGroupFormService.getShipperPersonGroup).toHaveBeenCalled();
      expect(shipperPersonGroupService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IShipperPersonGroup>>();
      const shipperPersonGroup = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
      jest.spyOn(shipperPersonGroupService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ shipperPersonGroup });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(shipperPersonGroupService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
