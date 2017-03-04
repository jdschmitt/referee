import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SecondPotComponent } from './second-pot.component';

describe('SecondPotComponent', () => {
  let component: SecondPotComponent;
  let fixture: ComponentFixture<SecondPotComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SecondPotComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SecondPotComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
