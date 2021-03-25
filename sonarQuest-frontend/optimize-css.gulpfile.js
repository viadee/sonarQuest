const gulp = require("gulp");
const filter = require("gulp-filter");
const purify = require("gulp-purify-css");
const gzip = require("gulp-gzip");
const rename = require("gulp-rename");
const brotli = require("gulp-brotli");
const clean = require("gulp-clean");
const {series, parallel} = require("gulp");

// #1 | Optimize CSS
/*
  Steps:
  Filter the CSS files to be optimized
  Based on their usage in Build JS files
  & Store the optimize file in the given location
*/
gulp.task("css", () => {
  return gulp
    .src("./dist/*")
    .pipe(
      filter([
        /*
        glob pattern for CSS files,
        point to files generated post angular prod build
        */
        "**/styles.*.css",
      ])
    )
    .pipe(
      /*
       glob pattern for JS files, to look for the styles usage
       the styles will be filtered based on the usage in the below files.
       Pointing to JS build output of Angular prod build
       */
      purify(["./dist/*.js"], {
        info: true,
        minify: true,
        rejected: true,
        whitelist: []
      })
    )
    .pipe(gulp.dest("./dist/temp/"));/* Optimized file output location */
});

// # 2 | Generate GZIP files
/*
Steps:
Read the optimized CSS in the Step #1
Apply gzip compression
*/
gulp.task("css-gzip", () => {
  return gulp
    .src("./dist/temp/*")
    .pipe(filter(["**/*.css", "!**/*.br.*", "!**/*.gzip.*"]))
    .pipe(gzip({append: false}))
    .pipe(
      rename(path => {
        path.extname = ".gzip" + path.extname;
      })
    )
    .pipe(gulp.dest("./dist/temp/"));
});

// # 3 | Generate BROTLI files
/*
Steps:
Read the optimized CSS in the Step #1
Apply brotli compression
*/
gulp.task("css-br", () => {
  return gulp
    .src("./dist/temp/*")
    .pipe(filter(["**/*.css", "!**/*.br.*", "!**/*.gzip.*"]))
    .pipe(brotli.compress())
    .pipe(
      rename(path => {
        path.extname =
          ".br" +
          path.basename.substring(
            path.basename.lastIndexOf("."),
            path.basename.length
          );
        path.basename = path.basename.substring(
          0,
          path.basename.lastIndexOf(".")
        );
      })
    )
    .pipe(gulp.dest("./dist/temp"));
});

// # 4 | Clear ng-build CSS
/*
Delete style output of Angular prod build
*/
gulp.task("clear-ng-css", () => {
  return gulp
    .src("./dist/*")
    .pipe(filter(["**/styles*.css"]))
    .pipe(clean({force: true}));
});

// # 5 | Copy optimized CSS
/*
Once the optimization & compression is done,
Replace the files in angular build output location
*/
gulp.task("copy-op-css", () => {
  return gulp.src("./dist/temp/*").pipe(gulp.dest("./dist/"));
});

// #6 | Clear temp folder
gulp.task("clear-temp", () => {
  return gulp
    .src("./dist/temp/", {read: false})
    .pipe(clean({force: true}));
});

/*
 ### Order of Tasks ###
 * Optimize the styles generated from ng build
 * Create compressed files for optimized css
 * Clear the angular build output css
 * Copy the optimized css to ng-bundle folder
 * Clear the temp folder
 */
exports.default = series(
  "css",
  parallel("css-gzip", "css-br"),
  "clear-ng-css",
  "copy-op-css",
  "clear-temp"
);
