# RJUG - RXJava Functional Reactive Programming

This is the code of my presentation RxJava - Functional Reactive Programming at the Richmond Java Users Group.

A static presentation is available [here](http://ciraxwe.github.io/rjug-rxjava). The code examples will not work since they require the application to execute the code. The Marvel API [demo](http://ciraxwe.github.io/rjug-rxjava/iframes/demo/index.html) uses local resources and does not connect to the API.

## Building and Running

It is a Spring boot application. The project uses Gradle 2.2 as build tool. To generate the JAR file execute.

    gradlew bootRun
    
It will download the Gradle distribution and start the application on port `8080`. Visit http://localhost:8080/ to view the presentation. Press `CTRL+C` to terminate the task.

### Code Examples

The presentation comes with a Ace Editor. Press `E` to bring the Editor up. You will be able to run the examples by pressing `CTRL+ENTER`. All examples will work but the ones that use the Marvel API.

If you want to test the Marvel API you will need to create a developer account at http://developer.marvel.com/ and update `src\main\resources\application.properties`

    app.marvel.api.key:YOUR KEY HERE
    app.marvel.api.private:YOUR KEY HERE

## Open Source Projects

I'm listing all the open source tools I used to make this presentation possible.

* The whole presentation is a Spring Boot application. Why do I need an application? To run Groovy code from the editor.
* The app has a REST endpoint that receives the Script, and then returns JSON. It has the start time and end time of the script, the original script, the output and the error in case something bad happened.

  http://projects.spring.io/spring-boot/

* The reason I picked Groovy is because it is dynamic and great to run as script. So in the examples you saw I was not importing anything and declaring all the classes I was using. That's because you can customize the Shell that executes the script. Similar to what tools like Gradle do. You write what is important and the rest is configurable.

  http://groovy.codehaus.org

* These are the 3 Netflix projects I talked about.

  - RxJava https://github.com/ReactiveX/RxJava
  - Feign https://github.com/Netflix/feign
  - Hystrix https://github.com/Netflix/Hystrix

* For the presentation I used Shower. A HTML presentation engine. I made a few changes to customize fonts, padding's, margins, etc.

  http://shwr.me

* The editor is Ace. A pretty amazing HTML code editor. I used the embedded version, and the Spring Boot app serves the static content. You can add commands to Ace to execute particular actions. `CTRL+ENTER` sends code to the REST endpoint in the Spring Boot app. `CTRL+M` brings the left menu and `ESC` hides the menu and the console output.
  
  http://ace.c9.io

* I integrated Ace and Shower from inspiration on the following 2 articles. So when you press `E` from the Shower presentation it opens an Overlay with an IFRAME that contains the Editor.

  http://tympanus.net/codrops/2014/09/16/off-canvas-menu-effects/
  http://tympanus.net/codrops/2014/02/06/fullscreen-overlay-effects/

* Icons are from Font-Awasome.
  
  http://fortawesome.github.io/Font-Awesome/

* Hi Res Images are from Flickr.
  
  https://www.flickr.com/photos/markusspiske/sets/72157646779778414/
