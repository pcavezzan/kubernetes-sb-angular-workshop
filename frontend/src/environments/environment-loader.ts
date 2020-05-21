import {environment as defaultEnvironment} from './environment';

export const environmentLoader = new Promise<any>((resolve, reject) => {

  if (defaultEnvironment.production) {
    const xhr = new XMLHttpRequest();
    xhr.open('GET', './assets/environment.json', true);
    xhr.setRequestHeader( 'Content-Type', 'application/json' );
    xhr.onload = () => {
      if (xhr.status === 200) {
        resolve(JSON.parse(xhr.responseText));
      } else {
        resolve(defaultEnvironment);
      }
    };
    xhr.send();
  } else {
    resolve(defaultEnvironment);
  }

});
