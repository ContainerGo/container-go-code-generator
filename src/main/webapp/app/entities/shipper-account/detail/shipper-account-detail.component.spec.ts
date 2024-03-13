import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ShipperAccountDetailComponent } from './shipper-account-detail.component';

describe('ShipperAccount Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ShipperAccountDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: ShipperAccountDetailComponent,
              resolve: { shipperAccount: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(ShipperAccountDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load shipperAccount on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', ShipperAccountDetailComponent);

      // THEN
      expect(instance.shipperAccount).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
