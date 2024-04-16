import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { ContainerStatusDetailComponent } from './container-status-detail.component';

describe('ContainerStatus Management Detail Component', () => {
  let comp: ContainerStatusDetailComponent;
  let fixture: ComponentFixture<ContainerStatusDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ContainerStatusDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: ContainerStatusDetailComponent,
              resolve: { containerStatus: () => of({ id: '9fec3727-3421-4967-b213-ba36557ca194' }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(ContainerStatusDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ContainerStatusDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load containerStatus on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', ContainerStatusDetailComponent);

      // THEN
      expect(instance.containerStatus()).toEqual(expect.objectContaining({ id: '9fec3727-3421-4967-b213-ba36557ca194' }));
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
