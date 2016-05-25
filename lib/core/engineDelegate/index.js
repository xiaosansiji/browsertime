'use strict';

let BmpDelegate = require('./bmpDelegate'),
  ChromeDelegate = require('./chromeDelegate'),
  FirefoxDelegate = require('./firefoxDelegate'),
  nullDelegate = require('./nullDelegate'),
  trafficShapeParser = require('../../support/trafficShapeParser');

module.exports = {
  createDelegate(options){
    this.trafficShapeConfig = trafficShapeParser.parseTrafficShapeConfig(options);
    this.createHar = !options.skipHar;

    if (this.trafficShapeConfig || options.basicAuth || options.proxy) {
      return new BmpDelegate(options);
    }

    if (options.browser === 'firefox')
      return new FirefoxDelegate(options);
    if (options.browser === 'chrome')
      return new ChromeDelegate(options);

    return nullDelegate;
  }
};