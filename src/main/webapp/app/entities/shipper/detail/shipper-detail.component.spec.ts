import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ShipperDetailComponent } from './shipper-detail.component';

describe('Shipper Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ShipperDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: ShipperDetailComponent,
              resolve: { shipper: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(ShipperDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load shipper on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', ShipperDetailComponent);

      // THEN
      expect(instance.shipper).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
