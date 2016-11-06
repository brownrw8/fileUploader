# File Uploader

An application for uploading files with associated title, description, and date of upload.
Files are uploaded to existing directory specified by configuration
**fileUploader.localUploadDirectory** in *application.properties*.


## Specifications

Spring Boot with embedded Tomcat.  Angular 1.5 SPA front-end.  Embedded H2 database.

### Configurable Properties
* Local Upload Directory (**fileUploader.localUploadDirectory**)

## Setup
First, make sure you have NPM installed.  Also, if you don't plan on using Gradle Wrapper, please install a recent
version of Gradle.  I have included a Gradle task for installing bower and gulp (setup)
1. Clone Project
2. Clean and build
```
./gradlew steuclean bootRepackage
```
3. Run .jar

```
$ java -jar <jarfile>
```

### Using Gradle Wrapper

Substitute gradle command for gradlew script (.bat for Windows, .sh for Unix).  The correct version of Gradle will be downloaded for you.

## Contributing

1. Fork it
2. Create your feature branch: `git checkout -b my-new-feature`
3. Commit your changes: `git commit -am 'Add some feature'`
4. Push to the branch: `git push origin my-new-feature`
5. Submit a pull request




