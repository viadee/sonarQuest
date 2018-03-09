import { SonarquestFrontendPage } from './app.po';

describe('sonarquest-frontend App', () => {
  let page: SonarquestFrontendPage;

  beforeEach(() => {
    page = new SonarquestFrontendPage();
  });

  it('should display welcome message', done => {
    page.navigateTo();
    page.getParagraphText()
      .then(msg => expect(msg).toEqual('Welcome to app!!'))
      .then(done, done.fail);
  });
});
