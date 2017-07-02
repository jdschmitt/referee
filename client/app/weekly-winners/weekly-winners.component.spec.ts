import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WeeklyWinnersComponent } from './weekly-winners.component';

describe('WeeklyWinnersComponent', () => {
  let component: WeeklyWinnersComponent;
  let fixture: ComponentFixture<WeeklyWinnersComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WeeklyWinnersComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WeeklyWinnersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
