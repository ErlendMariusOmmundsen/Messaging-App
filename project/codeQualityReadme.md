# How we have handled warnings from code quality tools

We have used tools like checkstyles and spotbugs to try to improve our code quality, but some few of the warnings that these tools give, have we agreed to not care about.

### Checkstyle

**Missing javadoc** - Some of the warnings Checkstyle gives are that it is missing javadoc, but it is complaining for very simple methods like getters and setters, or methods that are overriding or implementing (but the overriden methods do have javadoc comments). Otherwise, we have javadoc for most methods were we see it necessary, we don't want to overcomment.

**Package name must match pattern '^[a-z]+(\.[a-z][a-z0-9]*)*$** - This is becuase all of our package have project_module as name. This is maybe not good, but we had it from the start because we couldn't have name like core, fxui, etc. in eclipse because it was already taken from other projects (simpleexample2).

There might have been other warnings slipped by somewhere of course.

### Spotgbugs

**SXWN9001: A variable with no following sibling instructions has no effect** - This warning has always been there and happends every time you run the spotbugTest, but it isn't anything wrong with the test.

**reads a file whose location might be specified by user input** - In core when handling file-IO this happends because each account have an inbox with their name as filename (as inbox). This can of course be a security warning, but it is essentialy a part of our application. SpotBugs suggests a fix, and that is using FilenameUtils.getName(filepath), but then we would have to import org.apache.commons.io.FilenameUtils, which is a whole dependency to org.appache.commons that we don't want. There doesn't seem to be an easy fix in just using standard java libraries.