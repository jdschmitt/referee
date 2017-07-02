import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MyPicksComponent } from './my-picks.component';

describe('MyPicksComponent', () => {
  let component: MyPicksComponent;
  let fixture: ComponentFixture<MyPicksComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MyPicksComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MyPicksComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
