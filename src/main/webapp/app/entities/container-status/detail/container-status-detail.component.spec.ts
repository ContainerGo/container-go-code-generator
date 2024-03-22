import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ContainerStatusDetailComponent } from './container-status-detail.component';

describe('ContainerStatus Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ContainerStatusDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: ContainerStatusDetailComponent,
              resolve: { containerStatus: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(ContainerStatusDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load containerStatus on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', ContainerStatusDetailComponent);

      // THEN
      expect(instance.containerStatus).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
