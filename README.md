# File Uploader

An application for uploading files with associated title, description, and date of upload.
Files are uploaded to existing directory specified by configuration
**fileUploader.localUploadDirectory** in *application.properties*.

## Specifications

Spring Boot with embedded Tomcat.  Angular 1.5 SPA front-end.  Embedded H2 database.

### Configurable Properties
* Local Upload Directory (**fileUploader.localUploadDirectory**)

## Setup
First, it's best to have a recent version of npm installed.  Also, if you don't plan on using Gradle Wrapper, please install a recent version of Gradle.  I have included Gradle tasks for installing npm, bower and gulp.

### Steps
1. Clone project, checkout 'master' branch
2. Setup environment
Install npm & bower
    ```
    $ gradle npmInstall installBowerGlobal 
    ```

3. Clean & build
    ```
    $ gradle clean bowerInstall gulp_build bootRepackage
    ```

4. Run .jar
After starting the application, it should be visible on 127.0.0.1:8080 
    ```
    $ java -jar build/libs/fileUploader-1.0.0.jar
    ```

### Using Gradle Wrapper

Substitute gradle command for gradlew script.  The correct version of Gradle will be downloaded for you.

## Considerations
### Design Restrictions & Future Enhancements
* Currently there is no security, this could be easily solved by implementing authentication through Spring Security
* Uploaded files are stored on the local filesystem); instead, files could be stored on a larger, remote file system
* Database is in-memory and therefore not truly persistent; instead, the database could be on a remote server and persist
between instances
* Aggregated file capacity is limited to host server resources
* Database capacity is limited to host server resources
* Any user can see any other user's files.  This is not very practical and could be solved by implementing authorization
through Spring Security

## Contributing

1. Fork it
2. Create your feature branch: `git checkout -b my-new-feature`
3. Commit your changes: `git commit -am 'Add some feature'`
4. Push to the branch: `git push origin my-new-feature`
5. Submit a pull request








