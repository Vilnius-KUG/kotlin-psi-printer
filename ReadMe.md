# Psi Printer

A simple tool, which prints out Kotlin AST in order to demonstrate the basics of its parsing and traversal. Check out the sources to see how `kotlin-compiler-embeddable` library can be used to build both File AST and PSI trees and how these trees can be traversed.

You can run the tool using Gradle
`./gradlew run --args="-h"`

Or build the it and run as a Java app:
```
./gradlew fatJar
java -jar build/libs/psi-printer.jar
```

## Options

- `-t ast` argument is equivalent to how KtLint traverses the tree
- `-t kt_visitor` argument is equivalent to how Detekt traverses the tree

The full options doc is available via `-h` option.
