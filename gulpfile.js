// include gulp
var gulp = require('gulp');

// include plug-ins
var jshint = require('gulp-jshint');
var jsmin = require('gulp-jsmin');
var embedTemplates = require('gulp-angular-embed-templates');
var concat = require('gulp-concat');
var uglify = require('gulp-uglify');
var imagemin = require('gulp-imagemin');
var cleanCSS = require('gulp-clean-css');
var del = require('del');
var fs = require('fs');

var resolveDir = function(dirname, root){
    return root + '/' + dirname;
};

gulp.task('clean', function(){
    return del([
        opSettings.dist
    ]);
});

gulp.task('applicationJavascript',['clean'], function(){
    return gulp.src(opFiles.applicationJavascript)
        .pipe(embedTemplates({ basePath: 'src/main/webapp/resources' }))
        .pipe(concat(opSettings.minifiedApplicationJavascriptFile))
        .pipe(uglify())
        .pipe(gulp.dest(resolveDir('js',opSettings.assets)));
});

gulp.task('dmFonts',['clean'], function(){
    return gulp.src(opFiles.dmFonts)
        .pipe(gulp.dest(resolveDir('fonts',opSettings.assets)));
});

gulp.task('dmJavascript',['clean'], function(){
    return gulp.src(opFiles.dmJavascript)
        .pipe(gulp.dest(resolveDir('js',opSettings.assets)));
});

gulp.task('dmCss',['clean'], function(){
    return gulp.src(opFiles.dmCss)
        .pipe(gulp.dest(resolveDir('css',opSettings.assets)));
});

gulp.task('clearAssets',['clean'], function(){
    return del([
        '../../resources/static/assets/**'
    ],{force: true});
});

gulp.task('createIndex',['clean'],function(){
    var indexHTML = [];
    indexHTML.push('<html ng-app="');
    indexHTML.push(opSettings.ngModule);
    indexHTML.push('">');
    indexHTML.push("\n");
    indexHTML.push('<head>');
    indexHTML.push("\n");
    for(var i in opFiles.dmCss){
        indexHTML.push('<link rel="stylesheet" href="assets/css/');
        indexHTML.push(opFiles.dmCss[i].replace(/^.*[\\\/]/, ''));
        indexHTML.push('">');
        indexHTML.push("\n");
    }
    indexHTML.push("\n");
    indexHTML.push('<title>');
    indexHTML.push(opSettings.title);
    indexHTML.push('</title>');
    indexHTML.push("\n");
    indexHTML.push('</head>');
    indexHTML.push("\n");
    indexHTML.push('<body ng-cloak>');
    indexHTML.push("\n");
    indexHTML.push('<app></app>');
    indexHTML.push("\n");
    for(var i in opFiles.dmJavascript){
        indexHTML.push('<script src="assets/js/');
        indexHTML.push(opFiles.dmJavascript[i].replace(/^.*[\\\/]/, ''));
        indexHTML.push('"></script>');
        indexHTML.push("\n");
    }
    indexHTML.push('<script src="assets/js/');
    indexHTML.push(opSettings.minifiedApplicationJavascriptFile);
    indexHTML.push('"></script>');
    indexHTML.push("\n");
    indexHTML.push('</body>');
    indexHTML.push("\n");
    indexHTML.push('</html>');

    if (!fs.existsSync(opSettings.dist)){
        fs.mkdirSync(opSettings.dist);
    }
    return fs.writeFile(opSettings.dist+"/index.html", indexHTML.join(''));
});

gulp.task('transfer',
    [
        'applicationJavascript',
        'dmCss',
        'dmFonts',
        'dmJavascript',
        'createIndex',
        'clearAssets'
    ], function() {
    return gulp.src(resolveDir('/**',opSettings.dist))
        .pipe(gulp.dest('src/main/resources/static'));
});

gulp.task('build',['transfer']);

var opSettings = {
    dist: './dist',
    assets: './dist/assets',
    minifiedApplicationJavascriptFile: 'app.min.js',
    title: 'File Uploader',
    ngModule: 'fileUploader'
};

var opFiles = {
    applicationJavascript: [
        'src/main/webapp/resources/app/module.js',
        'src/main/webapp/resources/app/*.component.js',
        'src/main/webapp/resources/app/*.service.js'
    ], applicationCss: [], applicationImages: [], dmFonts: [
        'src/main/webapp/resources/bower_components/bootstrap/dist/fonts/*'
    ], dmCss: [
        'src/main/webapp/resources/bower_components/bootstrap/dist/css/bootstrap.min.css'
    ], dmJavascript: [
        'src/main/webapp/resources/bower_components/angular/angular.min.js',
        'src/main/webapp/resources/bower_components/angular-environment/dist/angular-environment.min.js'
    ]
};

