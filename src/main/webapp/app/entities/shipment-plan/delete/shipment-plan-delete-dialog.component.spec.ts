jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ShipmentPlanService } from '../service/shipment-plan.service';

import { ShipmentPlanDeleteDialogComponent } from './shipment-plan-delete-dialog.component';

describe('ShipmentPlan Management Delete Component', () => {
  let comp: ShipmentPlanDeleteDialogComponent;
  let fixture: ComponentFixture<ShipmentPlanDeleteDialogComponent>;
  let service: ShipmentPlanService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, ShipmentPlanDeleteDialogComponent],
      providers: [NgbActiveModal],
    })
      .overrideTemplate(ShipmentPlanDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ShipmentPlanDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(ShipmentPlanService);
    mockActiveModal = TestBed.inject(NgbActiveModal);
  });

  describe('confirmDelete', () => {
    it('Should call delete service on confirmDelete', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({ body: {} })));

        // WHEN
        comp.confirmDelete('9fec3727-3421-4967-b213-ba36557ca194');
        tick();

        // THEN
        expect(service.delete).toHaveBeenCalledWith('9fec3727-3421-4967-b213-ba36557ca194');
        expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
      }),
    ));

    it('Should not call delete service on clear', () => {
      // GIVEN
      jest.spyOn(service, 'delete');

      // WHEN
      comp.cancel();

      // THEN
      expect(service.delete).not.toHaveBeenCalled();
      expect(mockActiveModal.close).not.toHaveBeenCalled();
      expect(mockActiveModal.dismiss).toHaveBeenCalled();
    });
  });
});
