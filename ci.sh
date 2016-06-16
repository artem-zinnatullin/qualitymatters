#!/bin/bash
set -xe

# You can run it from any directory.
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

# This will: 
#    1. clean the project
#    2. run lint
#    3. run tests under JVM
#    4. package apk
#    5. check the code quality 
#    6. run tests on the device/emulator.

# We run each step separately to consume less memory (free CI sometimes fails with OOM)

GRADLE=""$DIR"/gradlew -PdisablePreDex --no-daemon"

# 1
eval "$GRADLE clean"

# 2
eval "$GRADLE lintDebug"
eval "$GRADLE lintRelease"

# 3
eval "$GRADLE testDebugUnitTestCoverage"
eval "$GRADLE testReleaseUnitTestCoverage"

# 4
eval "$GRADLE assembleDebug"
eval "$GRADLE assembleRelease"

# 5
eval "$GRADLE check"

#6 
eval "$GRADLE connectedAndroidTest"