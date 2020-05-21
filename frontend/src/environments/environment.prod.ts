import * as npm from '../../package.json';

export const environment = {
  production: true,
  apiUrl: '/api',
  version: npm.version,
  config: {
    env: 'prod'
  }
};
