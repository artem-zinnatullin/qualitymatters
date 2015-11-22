#!/bin/bash
# Please run it from root project directory

# This will: compile the project, run lint, run tests under JVM, package apk and check the code quality
./gradlew clean build findbugs pmd