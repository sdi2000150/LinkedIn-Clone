import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MyContactsPostsComponent } from './my-contacts-posts.component';

describe('MyContactsPostsComponent', () => {
  let component: MyContactsPostsComponent;
  let fixture: ComponentFixture<MyContactsPostsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MyContactsPostsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MyContactsPostsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
