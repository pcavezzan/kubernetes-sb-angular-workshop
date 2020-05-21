import { TestBed, async } from '@angular/core/testing';
import { InfoComponent } from './info.component';


describe('InfoComponent', () => {
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        InfoComponent,
      ],
    }).compileComponents();
  }));

  it('should create the app-info', () => {
    const fixture = TestBed.createComponent(InfoComponent);
    const messageComponent = fixture.componentInstance;

    expect(messageComponent).toBeTruthy();
  });

  it(`should have init version`, () => {
    const fixture = TestBed.createComponent(InfoComponent);
    const component = fixture.componentInstance;

    component.ngOnInit();

    expect(component.currentVersion).toBeDefined();
  });

  it(`should have init env`, () => {
    const fixture = TestBed.createComponent(InfoComponent);
    const component = fixture.componentInstance;

    component.ngOnInit();

    expect(component.env).toBeDefined();
  });

});
