import { MessageService } from './message.service';
import { cold } from 'jasmine-marbles';
import { HttpService } from '../shared/http.service';

describe('MessageService', () => {
  let httpServiceSpy: jasmine.SpyObj<HttpService>;
  let messageService: MessageService;

  beforeEach(() => {
    httpServiceSpy = jasmine.createSpyObj('HttpService', ['get']);
    messageService = new MessageService(httpServiceSpy);
  });

  it('getFrontPageMessage should call get /welcome', () => {
    messageService.getFrontPageMessage();

    expect(httpServiceSpy.get).toHaveBeenCalledWith('/welcome');
  });

  it('getFrontPageMessage should call get /whoami', () => {
    messageService.getFrontPageMessage();

    expect(httpServiceSpy.get).toHaveBeenCalledWith('/whoami');
  });


  it(`getFrontPageMessage should return Observable<Message>`, () => {
    httpServiceSpy.get.withArgs('/welcome').and.returnValue(cold('-m-', {m: {payload: 'Hello World'}}));
    httpServiceSpy.get.withArgs('/whoami').and.returnValue(cold('-m-', {m: {payload: 'localhost'}}));

    expect(messageService.getFrontPageMessage()).toBeObservable(cold('-m-', {m: {payload: 'Hello World from localhost'}}));
  });

  it('getInfoMessage should call get /info', () => {
    messageService.getInfoMessage();

    expect(httpServiceSpy.get).toHaveBeenCalledWith('/info');
  });

  it(`getInfoMessage should return Observable<Message>`, () => {
    httpServiceSpy.get.withArgs('/info').and.returnValue(cold('-m-', {m: {payload: 'Running v1 from localhost'}}));

    expect(messageService.getInfoMessage()).toBeObservable(cold('-m-', {m: {payload: 'Running v1 from localhost'}}));
  });

});
