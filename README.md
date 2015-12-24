# QualityMatters

This is the app that follows all principles of [Android Development Culture described here](http://artemzin.com/blog/android-development-culture-the-document-qualitymatters/).

What does it have:

* CI (Travis)
* Unit tests (some under Robolectric, some are under plain JUnit runner with mocked `android.jar`).
* Integration tests to see that Http, REST, JSON parsing and RxJava work well in composition.
* Functional (UI) tests (Espresso with custom rules, mocked server and Screen-architecture) to check that app works according to the expectations from the user's point of view.
* Static code analysis (FindBugs, PMD, Android Lint, Checkstyle) (see root `build.gradle`).
* Code coverage (currently in process of fighting with jacoco-coverage plugin to fail the build if coverage is not big enough).
* Developer Settings Menu where you can enable/disable [Stetho](http://facebook.github.io/stetho/), [LeakCanary](https://github.com/square/leakcanary), etc. See full list below (feel free to add more tools!).
* MVP, RxJava, Dagger 2, Retrofit 2 and so on.

---
>Made with ❤️ by Artem Zinnatullin [https://twitter.com/artem_zin](https://twitter.com/artem_zin).

To build the project run `sh ci.sh` (yep, that easy, because it should be easy).

Screenshots:

<img src="/site/screenshot1.png" width="400"> <img src="/site/screenshot2.png" width="400">

###Tests

####Unit tests

App has unit tests and they live mostly in [`src/unitTests`](/app/src/unitTests/)., but app also has **debug** and **release** specific code, so there are also [`debugUnitTests`](/app/src/debugUnitTests/) and [`releaseUnitTests`](/app/src/releaseUnitTests/).

Unit tests check classes/methods in isolation from others, all dependencies are mocked.

>All unit tests run on the JVM, no emulator or device is required.
Mostly, unit tests run with mocked `android.jar` (it's a builtin feature of Android Gradle Plugin) but some of tests need things like `Intent`, etc and such tests run under `Robolectric`.

Also, you might notice that app has custom [`unit test runner`](/app/src/unitTests/java/com/artemzin/qualitymatters/QualityMattersRobolectricUnitTestRunner.java). It's required to override and mock some dependencies under Robolectric, like `Analytics`, who needs real `Analytics` in Unit tests?

####Integration tests

App has integration tests and they live in [`src/integrationTests`](/app/src/integrationTests/).

Integration tests check composition of multiple classes, for example OkHttp + Retrofit + Jackson + RxJava == API level, mostly all classes are real and not mocked, but for instance, we mock web server in integration tests.

>All integration tests run on the JVM under `Robolectric`.

Also, you might notice that app has custom [`integration test runner`](/app/src/integrationTests/java/com/artemzin/qualitymatters/QualityMattersIntegrationRobolectricTestRunner.java). It's required to override and mock some dependencies, like `Analytics`, who needs real `Analytics` in integration tests?

####Functional (UI) tests

App has functional (UI) tests and they live in [`src/functionalTests`](/app/src/functionalTests/).

Functional tests check how the product (Android app) works from the point of User's view, so basically, functional test of Android app check UI of the app and different use cases.

>All functional tests run on connected emulator/device via Instrumentation API.

Also, you might notice that app has custom [`functional test runner`](/app/src/functionalTests/java/com/artemzin/qualitymatters/functional_tests/QualityMattersFunctionalTestsRunner.java) (yep). It's required to override and change implementation of some dependencies, like `Analytics`, instead of posting tons of useless data to `Analytics` during functional tests we simply output it to the LogCat!

###Developer Settings

**Tools:**

* [Stetho](http://facebook.github.io/stetho/) — inspect the app via Chromium Developer Tools (network requests, db, preferences and so on). Must have for developers.
* [LeakCanary](https://github.com/square/leakcanary) — detect memory leaks without IDE! Must have for QAs and developers.
* [TinyDancer](https://github.com/brianPlummer/TinyDancer) — see frame rate right on your screen. Must have for QAs and developers.

**Details of implementation**

Developer Settings presented only in `debug` build type, libraries and resources used for Developer Settings compiled only into `debug` build and main source set knows only little abstractions over Developer Settings just to initialize real implementation in the `debug` build code. In release build type `DeveloperSettingsModule` (Dagger) just returns `no-op` implementation of `DeveloperSettingsModel`.

**Why only debug builds?**
The Answer is simple — dex limit. LeakCanary brings about 3k of methods, Stetho brings about 2k and so on. The more tools you add to Developer Settings — the bigger apk you receive. Situation is even worse if your main code is near to 65k methods. In our production app we had to turn on `multidex` for `debug` builds.

###Packages structure

Many people ask why app has component-based structure of the packages: `presenters`, `models`, etc. instead of feature-based structure: `itemslist`, `developersettings`, etc.

With component-based structure of packages new persons on the project (like those who read the code of this app) can easily find what `presenters` does the app have, what `views`, `models` and so on. If you read the code and you want to quickly move to some class related to current one you can [simply press `t` right on the GitHub and search](https://github.com/blog/793-introducing-the-file-finder) for the required file!
