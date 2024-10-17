import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BackBtnComponent } from './back-btn.component';
import { Router } from '@angular/router';

describe('BackBtnComponent', () => {
  let component: BackBtnComponent;
  let fixture: ComponentFixture<BackBtnComponent>;

  const routerMock = {
    navigate: jasmine.createSpy('navigate')
  }

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BackBtnComponent],
      providers : [
        { provide: Router, useValue: routerMock }
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BackBtnComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('without history goBack should navigate to /', () => {
    spyOnProperty(window.history, 'length').and.returnValue(1);
    component.goBack();
    expect(routerMock.navigate).toHaveBeenCalledWith(['/']);
    routerMock.navigate.calls.reset();
  });

  it('with history goBack should goBack in history', () => {
    spyOnProperty(window.history, 'length').and.returnValue(2);
    spyOn(window.history, "back");
    component.goBack();
    expect(window.history.back).toHaveBeenCalled();
  });

});
