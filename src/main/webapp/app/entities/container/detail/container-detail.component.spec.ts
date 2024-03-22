import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ContainerDetailComponent } from './container-detail.component';

describe('Container Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ContainerDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: ContainerDetailComponent,
              resolve: { container: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(ContainerDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load container on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', ContainerDetailComponent);

      // THEN
      expect(instance.container).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
