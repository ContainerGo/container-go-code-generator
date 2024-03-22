import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { TruckDetailComponent } from './truck-detail.component';

describe('Truck Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TruckDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: TruckDetailComponent,
              resolve: { truck: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(TruckDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load truck on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', TruckDetailComponent);

      // THEN
      expect(instance.truck).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
