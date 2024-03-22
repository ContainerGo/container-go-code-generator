import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ProviceDetailComponent } from './provice-detail.component';

describe('Provice Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProviceDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: ProviceDetailComponent,
              resolve: { provice: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(ProviceDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load provice on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', ProviceDetailComponent);

      // THEN
      expect(instance.provice).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
