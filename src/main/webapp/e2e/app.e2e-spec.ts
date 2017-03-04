import { NgShieldPage } from './app.po';

describe('ng-shield App', () => {
  let page: NgShieldPage;

  beforeEach(() => {
    page = new NgShieldPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
