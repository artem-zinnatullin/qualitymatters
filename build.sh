#!/bin/bash
set -xe

# You can run it from any directory.
PROJECT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

# This will: compile the project, run lint, run tests under JVM, package apk, check the code quality and run tests on the device/emulator.
"$PROJECT_DIR"/gradlew --no-daemon --info clean build -PdisablePreDex -PwithDexcount -Dscan

# Disable instrumentation tests for now, Travis is too flaky with Android Emulator.
# See https://github.com/artem-zinnatullin/qualitymatters/issues/220
# "$PROJECT_DIR"/gradlew --no-daemon --info connectedAndroidTest -PdisablePreDex -PwithDexcount
