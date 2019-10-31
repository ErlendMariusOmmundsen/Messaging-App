# How we have handled warnings from code quality tools

We have used tools like checkstyles and spotbugs to try to improve our code quality, but some few of the warnings that these tools give, have we agreed to not care about.

### Checkstyle

**Missing javadoc** - Some of the warnings Checkstyle gives are that it is missing javadoc, but it is complaining for very simple methods 
lik getters and setters. Otherwise, we have javadoc for most of more complex methods.

**Package name must match pattern '^[a-z]+(\.[a-z][a-z0-9]*)*$** - This is becuase all of our package have project_module as name. This is maybe not good, but we had it from the start because we couldn't have name like core, fxui, etc. in eclipse because it was already taken from other projects (simpleexample2).

### Spotgbugs 

Some things in core.