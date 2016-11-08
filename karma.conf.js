module.exports = function(config) {
  config.set({
    basePath: '',
        basePath: "src/main/webapp/resources",
        frameworks: ["jasmine"],
        files: [
            "bower_components/angular/angular.js",
            "bower_components/angular-environment/dist/angular-environment.js",
            "app/**.js"
        ],
        exclude: [
        ],
        plugins: [
          'karma-chrome-launcher',
          'karma-firefox-launcher',
          'karma-jasmine',
          'karma-junit-reporter'
        ],
        preprocessors: {
        },
        reporters: ["progress"],
        port: 9876,
        colors: true,
        logLevel: config.LOG_INFO,
        autoWatch: true,
        browsers: ["Firefox","Chrome"],
        singleRun: false,
        concurrency: Infinity,
        junitReporter: {
          outputFile: 'test_out/unit.xml',
          suite: 'unit'
        }
      });
};