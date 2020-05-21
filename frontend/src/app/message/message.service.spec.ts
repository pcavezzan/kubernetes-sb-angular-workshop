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

  it('getMessage should call get /welcome', () => {
    messageService.getMessage();

    expect(httpServiceSpy.get).toHaveBeenCalledWith('/welcome');
  });

  it('getMessage should call get /whoami', () => {
    messageService.getMessage();

    expect(httpServiceSpy.get).toHaveBeenCalledWith('/whoami');
  });


  it(`getMessage should return Observable<Message>`, () => {
    httpServiceSpy.get.withArgs('/welcome').and.returnValue(cold('-m-', {m: {payload: 'Hello World'}}));
    httpServiceSpy.get.withArgs('/whoami').and.returnValue(cold('-m-', {m: {payload: 'localhost'}}));

    expect(messageService.getMessage()).toBeObservable(cold('-m-', {m: {payload: 'Hello World - localhost'}}));
  });

});
