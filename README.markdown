clojure-stub
------------

clojure-stub is an ant file to jump start clojure projects. It will
download and setup required files to have a clojure application in
seconds.

Setup
-----

After cloning the project, edit your project name and run "ant
setup". It will download necessary jar files and setup your project. So
you don't have to mess with all that Class-Path stuff.

By default clojure.jar and clojure-contrib.jar is downloaded from this
repository. But you can point the download url to anywhere you like.

Targets
-------

* run     - will setup required class path's and run the application
* repare - will unzip necessary jar's to create a single executable jar.
* compile - will build the application and create single executable jar.
* clean   - will clean up the build folder and created jar.
* deps    - can redownload needed jar's.
* setup   - is ran once to create all files required to create a "Hello,
World" application with your settings.
