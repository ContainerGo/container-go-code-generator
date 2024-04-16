import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { ShipmentPlanDetailComponent } from './shipment-plan-detail.component';

describe('ShipmentPlan Management Detail Component', () => {
  let comp: ShipmentPlanDetailComponent;
  let fixture: ComponentFixture<ShipmentPlanDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ShipmentPlanDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: ShipmentPlanDetailComponent,
              resolve: { shipmentPlan: () => of({ id: '9fec3727-3421-4967-b213-ba36557ca194' }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(ShipmentPlanDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ShipmentPlanDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load shipmentPlan on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', ShipmentPlanDetailComponent);

      // THEN
      expect(instance.shipmentPlan()).toEqual(expect.objectContaining({ id: '9fec3727-3421-4967-b213-ba36557ca194' }));
    });
  });

  describe('PreviousState', () => {
    it('Should navigate to previous state', () => {
      jest.spyOn(window.history, 'back');
      comp.previousState();
      expect(window.history.back).toHaveBeenCalled();
    });
  });
});
