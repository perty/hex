# Hexagonal architecture, ports and adapters

This is a (third) attempt to implement the Hexagonal architecture with Spring Boot.

## Goals

### Obvious

The package structure and class names should make it obvious where in the architecture you are when reading the code. It
should be obvious where to add new code.

The first part of the package name, is one of `domain`, `config`, `in` or `out`.

The architecture has two sides, in and out, hence they are put on top. For each side, there are ports and adapters, so
those are the next level.

### Configurable

The architecture promises that we can swap adapters as we please. To prove that, at least two adapters should support
the same port.

For outgoing port to persistence, Spring Boot injection with `@Qualify` is used. It would have been great if a property
could be used as argument but sadly not. Instead, a level of indirection had to be added where every possible
qualification is represented. [See `DbConfig`](src/main/java/se/artcomputer/edu/hex/config/DbConfig.java) for details
where a database or stub adapter is selected, based on the property `se.artcomputer.edu.db`
in [application.properties](src/main/resources/application.properties).

### Dependency control

The architecture has a clear idea about how dependencies go. The code must not take shortcuts and become tangled.
Therefore, there is a test that scans the import statements and verifies that the rules are obeyed.
[See`DependencyTest`](src/test/java/se/artcomputer/edu/hex/DependencyTest.java).

#### Allowed dependencies

The rule is that the `domain` logic should be in the center and independent of technical aspects such as persistence mechanisms. 

It is easy to believe that `in` and `out` are symmetric but `in` is the driving side while `out` is the driven side.

As an example, when a REST request arrives, the REST adapter uses the in port to call into the domain. The domain logic reaches for an outgoing port and the configuration controls which adapter will be selected for the outgoing port.

The config package only needs to know the `out` port, the adapters are injected by Spring.

````mermaid
classDiagram
    InAdapter --> Domain
    InAdapter --> InPort
    OutAdapter --> OutPort
    OutPort --> Domain
    OutAdapter --> Domain
    InPort --> Domain
    InPort --> Config
    Config --> OutPort
````

