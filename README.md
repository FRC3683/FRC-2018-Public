# Dave code for 2018

Instead of using the regular 'ol WPI lib plugin with eclipse, we switch to gradle for our building and deployment. More info on gradle can be read here https://gradle.org/.

More specifically, we are use the GradleRIO plugin. More info can be read about it here https://github.com/Open-RIO/GradleRIO.

This change will allow us to develop on any IDE and deploy from any Windows, Mac, or Linux machines. It also allows us to handle all of our libraries and dependencies automatically, without everyone needing to separately download all the required libraries to build the code.  

For more info on dependency management, see https://developer.android.com/studio/build/dependencies.html. It was meant for Android, but Android uses gradle for its build system, so all the library specific info should be valid.



