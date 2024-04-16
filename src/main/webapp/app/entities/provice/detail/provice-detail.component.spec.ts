import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { ProviceDetailComponent } from './provice-detail.component';

describe('Provice Management Detail Component', () => {
  let comp: ProviceDetailComponent;
  let fixture: ComponentFixture<ProviceDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProviceDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: ProviceDetailComponent,
              resolve: { provice: () => of({ id: '9fec3727-3421-4967-b213-ba36557ca194' }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(ProviceDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ProviceDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load provice on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', ProviceDetailComponent);

      // THEN
      expect(instance.provice()).toEqual(expect.objectContaining({ id: '9fec3727-3421-4967-b213-ba36557ca194' }));
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
