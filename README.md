# QualityMatters

This is the app that follows all principles of [Android Development Culture described here](http://artemzin.com/blog/android-development-culture-the-document-qualitymatters/).

What does it have:

* CI (Travis)
* Unit tests (some under Robolectric, some are under plain JUnit runner with mocked `android.jar`).
* Integration tests to see that Http, REST, JSON parsing and RxJava work good in composition.
* Functional (UI) tests (Espresso with custom rules, mocked server and Screen-architecure) to check that app works according to the expectations.
* Static code analysis (FindBugs, PMD, Android Lint, Checkstyle) (see root `build.gradle`).
* Code coverage (currently in process of fighting with jacoco-coverage plugin to fail the build if coverage is not big enough).
* Developer Settings Menu where you can enable/disable [Stetho](http://facebook.github.io/stetho/), [LeakCanary](https://github.com/square/leakcanary), etc. See full list below (feel free to add more tools!).
* MVP, RxJava, Dagger 2, Retrofit 2 and so on.

You can download apk from the [releases page](https://github.com/artem-zinnatullin/qualitymatters/releases).

---
>Made with ❤️ by Artem Zinnatullin [https://twitter.com/artem_zin](https://twitter.com/artem_zin).

To build the project run `sh ci.sh` (yep, that easy, because it should be easy).

Screenshots:

<img src="/site/screenshot1.png" width="400"> <img src="/site/screenshot2.png" width="400">

####Developer Settings

* [Stetho](http://facebook.github.io/stetho/) — inspect the app via Chromium Developer Tools (network requests, db, preferences and so on). Must have for developers.
* [LeakCanary](https://github.com/square/leakcanary) — detect memory leaks without IDE! Must have for QAs and developers.
* [TinyDancer](https://github.com/brianPlummer/TinyDancer) — see frame rate right on your screen. Must have for QAs and developers.
