const gulp = require('gulp')
const gap = require('gulp-append-prepend')

gulp.task('licenses', async function () {
  gulp
    .src('dist/js/*.js', { base: './' })
    .pipe(
      gap.prependText(`/*!

=========================================================================
* ByteHub Community - v1.0.0 based on Java/Spring with Vue 3 and Tailwind
=========================================================================

* Project Page:
* Copyright 2024 Rafael Vieira ()
* Licensed under MIT ()

* Coded by Rafael Vieira

==========================================================================

* The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

*/`),
    )
    .pipe(gulp.dest('./', { overwrite: true }))

  gulp
    .src('dist/index.html', { base: './' })
    .pipe(
      gap.prependText(`<!--

=========================================================================
* ByteHub Community - v1.0.0 based on Java/Spring with Vue 3 and Tailwind
=========================================================================

* Project Page:
* Copyright 2024 Rafael Vieira ()
* Licensed under MIT ()

* Coded by Rafael Vieira

==========================================================================

* The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

-->`),
    )
    .pipe(gulp.dest('./', { overwrite: true }))

  gulp
    .src('dist/css/*.css', { base: './' })
    .pipe(
      gap.prependText(`/*!

=========================================================================
* ByteHub Community - v1.0.0 based on Java/Spring with Vue 3 and Tailwind
=========================================================================

* Project Page:
* Copyright 2024 Rafael Vieira ()
* Licensed under MIT ()

* Coded by Rafael Vieira

==========================================================================

* The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

*/`),
    )
    .pipe(gulp.dest('./', { overwrite: true }))
  return
})
