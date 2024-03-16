import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ShipperPersonDetailComponent } from './shipper-person-detail.component';

describe('ShipperPerson Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ShipperPersonDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: ShipperPersonDetailComponent,
              resolve: { shipperPerson: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(ShipperPersonDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load shipperPerson on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', ShipperPersonDetailComponent);

      // THEN
      expect(instance.shipperPerson).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
