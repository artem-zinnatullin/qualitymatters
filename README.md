# QualityMatters

This app follows all principles of [Android Development Culture Document](http://artemzin.com/blog/android-development-culture-the-document-qualitymatters/).

What does it have:

* CI ([Travis](https://travis-ci.org/artem-zinnatullin/qualitymatters))
* Unit tests (some are Robolectric, some are plain JUnit tests with mocked `android.jar`).
* Integration tests to see that HTTP, REST, JSON parsing and RxJava work well in composition.
* Functional (UI) tests (Espresso with custom rules, mocked server and Screen-architecture) to check that app works according to the expectations from the user's point of view.
* Static code analysis (FindBugs, PMD, Android Lint, Checkstyle) (see root `build.gradle`).
* Code coverage [![codecov.io](https://codecov.io/github/artem-zinnatullin/qualitymatters/coverage.svg?branch=master)](https://codecov.io/github/artem-zinnatullin/qualitymatters?branch=master)
* Developer Settings Menu where you can enable/disable [Stetho](http://facebook.github.io/stetho/), [LeakCanary](https://github.com/square/leakcanary), etc. See full list below (feel free to add more tools!).
* Git sha & build time without breaking incremental compilation! (Thanks to [Paperwork](https://github.com/zsoltk/paperwork))
* MVP, RxJava, Dagger 2, Retrofit 2 and so on.

---
>Made with ❤️ by Artem Zinnatullin [https://twitter.com/artem_zin](https://twitter.com/artem_zin).

To build the project run `sh build.sh` (yep, that easy, because it should be easy).

Screenshots:

<img src="/site/screenshot1.png" width="400"> <img src="/site/screenshot2.png" width="400">

### Tests

#### Unit tests

Unit tests live mostly in [`src/unitTests`](/app/src/unitTests/) but the app also has **debug** and **release** specific code—so there are also [`debugUnitTests`](/app/src/debugUnitTests/) and [`releaseUnitTests`](/app/src/releaseUnitTests/).

Unit tests check classes/methods in isolation from others, all dependencies are mocked.

>All unit tests run on the JVM, no emulator or device is required.
Mostly, unit tests run with mocked `android.jar` (built-in feature of Android Gradle Plugin). Some tests require things like `Intent`, etc. and such tests run using Robolectric.

The app has custom [`unit test runner`](/app/src/unitTests/java/com/artemzin/qualitymatters/QualityMattersRobolectricUnitTestRunner.java) which is required in order to override and mock some dependencies using Robolectric, like `Analytics`. Who needs real `Analytics` in Unit tests?

#### Integration tests

Integration tests live in [`src/integrationTests`](/app/src/integrationTests/).

Integration tests check composition of multiple classes, for example OkHttp + Retrofit + Jackson + RxJava == API level, mostly all classes are real and not mocked, but for instance, we mock web server in integration tests.

>All integration tests run on the JVM using Robolectric.

Also, you might notice that the app has custom [`integration test runner`](/app/src/integrationTests/java/com/artemzin/qualitymatters/QualityMattersIntegrationRobolectricTestRunner.java). It's required in order to override and mock some dependencies, like `Analytics`. Again, who needs real `Analytics` in integration tests?

#### Functional (UI) tests

The app has functional (UI) tests that live in [`src/functionalTests`](/app/src/functionalTests/).

Functional tests check how the product (Android app) works from user's point of view. Basically, functional tests check app's UI for different use cases.

>All functional tests run on connected emulator/device via Instrumentation API.

Also, you might notice that the app has custom [`functional test runner`](/app/src/functionalTests/java/com/artemzin/qualitymatters/functional_tests/QualityMattersFunctionalTestsRunner.java) (yep). It's required to override and change implementation of some dependencies, like `Analytics`—instead of posting tons of useless data to `Analytics` during functional tests, we simply output it to the LogCat!

### Developer Settings

**Tools:**

* [Stetho](http://facebook.github.io/stetho/) — inspect the app via Chromium Developer Tools (network requests, db, preferences and so on). Must-have for developers.
* [LeakCanary](https://github.com/square/leakcanary) — detect memory leaks without IDE! Must-have for QAs and developers.
* [TinyDancer](https://github.com/brianPlummer/TinyDancer) — see frame rate right on your screen. Must-have for QAs and developers.
* [Lynx](https://github.com/pedrovgs/Lynx) — see LogCat output right in the app, useful for QAs and developers.

**Details of implementation**

Developer Settings are present only in `debug` build type. Libraries and resources used for Developer Settings are compiled into `debug` build only—main source set knows only little abstractions over Developer Settings just to initialize real implementation in the `debug` build code. In release build type `DeveloperSettingsModule` (Dagger) just returns `no-op` implementation of `DeveloperSettingsModel`.

**Why only debug builds?**
The answer is simple — dex limit. LeakCanary brings about 3k methods, Stetho brings about 2k and so on. The more tools you add to Developer Settings, the bigger the apk you receive. Situation is even worse if your main code gets near 65k methods. In our production app we had to turn on `multidex` for `debug` builds.

### Package structure

Many people ask why this app has component-based package structure: `presenters`, `models`, etc. instead of the feature-based one: `itemslist`, `developersettings`, etc.

With component-based structure of packages, new persons on the project (like those who read the code of this app) can easily find `presenters`, `views`, `models`, etc. within the app. If you read the code and want to quickly move to some class related to the current one you can [simply press `t` right on the GitHub and search](https://github.com/blog/793-introducing-the-file-finder) for the required file!
