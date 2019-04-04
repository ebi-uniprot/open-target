### Architectural decisions

#### Programming languages

This project is using Java because it is one of the most widely used
languages in the current team which makes it easier to get the feedback.

The tests used Groovy because it is very expressive and provides a nice
DSL for writing tests.


#### Framework

The project doesn't require any user interaction and is better suited for
offline processing. Spring batch is already already used by many organisations
and it seems a de facto for such use cases, also it is built on Java.