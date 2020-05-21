import { enableProdMode } from '@angular/core';
import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';

import { AppModule } from './app/app.module';
import { environment } from './environments/environment';
import { environmentLoader } from './environments/environment-loader';

environmentLoader.then( env => {
  environment.config = env.config;

  if (environment.production) {
    enableProdMode();
  }


  platformBrowserDynamic().bootstrapModule(AppModule)
    .catch(err => console.error(err));
});
