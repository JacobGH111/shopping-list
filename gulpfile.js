'use strict';
 
var gulp = require('gulp');
var sass = require('gulp-sass');
 
gulp.task('sass', function () {


  return gulp.src('./src/main/resources/public/sass/**/*.scss')
    .pipe(sass().on('error', sass.logError))
    .pipe(gulp.dest('./src/main/resources/public/css'));
});
 
gulp.task('sass:watch', function () {
  gulp.watch('./src/main/resources/public/sass/**/*.scss', ['sass']);
});