#!/bin/bash
# Please run it from root project directory

# This will: compile the project, run lint, run tests under JVM, package apk, scan (profile) build,
# check the code quality and run tests on the device/emulator.
./gradlew clean build connectedAndroidTest -PdisablePreDex -PwithDexcount -Dscan --stacktrace
