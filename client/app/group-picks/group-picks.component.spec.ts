import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GroupPicksComponent } from './group-picks.component';

describe('GroupPicksComponent', () => {
  let component: GroupPicksComponent;
  let fixture: ComponentFixture<GroupPicksComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GroupPicksComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GroupPicksComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
