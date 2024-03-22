import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { WardDetailComponent } from './ward-detail.component';

describe('Ward Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [WardDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: WardDetailComponent,
              resolve: { ward: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(WardDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load ward on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', WardDetailComponent);

      // THEN
      expect(instance.ward).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
