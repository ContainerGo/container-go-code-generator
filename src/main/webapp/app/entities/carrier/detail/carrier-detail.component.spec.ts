import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { CarrierDetailComponent } from './carrier-detail.component';

describe('Carrier Management Detail Component', () => {
  let comp: CarrierDetailComponent;
  let fixture: ComponentFixture<CarrierDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CarrierDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: CarrierDetailComponent,
              resolve: { carrier: () => of({ id: '9fec3727-3421-4967-b213-ba36557ca194' }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(CarrierDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CarrierDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load carrier on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', CarrierDetailComponent);

      // THEN
      expect(instance.carrier()).toEqual(expect.objectContaining({ id: '9fec3727-3421-4967-b213-ba36557ca194' }));
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
