# SpiderWeb 🕸

The SpiderWeb is a custom data structure designed to organize elements in a hierarchical and indexed manner. It implements a linked structure with nodes organized in levels and indices. This library provides methods for adding, querying, and removing elements based on their levels and indices within the SpiderWeb.

## Table of Contents
1. [Installation](#installation)
2. [Usage](#usage)
3. [Examples](#examples)
4. [API Reference](#api-reference)


## Installation

As of now, the SpiderWeb Java Library is not available on Maven Central. You can include it in your project by following these steps:

1. Clone the repository or download the source code.
2. Manually add the library to your Java project.

```xml
<!-- Example: Include SpiderWeb library locally -->
<dependency>
    <groupId>com.spiderweb</groupId>
    <artifactId>spiderweb</artifactId>
    <version>1.0</version>
    <scope>system</scope>
    <systemPath>/path/to/spiderweb.jar</systemPath>
</dependency>
```
Note: Replace /path/to/spiderweb.jar with the actual path to the JAR file.



## Usage
Create an instance of the SpiderWeb class with the default maximum number of elements per level (6) or specify a custom maximum number of elements per level.

```java
// Default constructor with a maximum of 6 elements per level
SpiderWeb<Integer> spiderWeb = new SpiderWeb<>();

// Constructor with a custom maximum number of elements per level (e.g., 8)
SpiderWeb<Integer> customSpiderWeb = new SpiderWeb<>(8);
```

Add elements to the SpiderWeb using the add method.

```java
spiderWeb.add(42);
spiderWeb.add(56);
spiderWeb.add(78);
```

Query elements based on their level and index using the get method.

```java
int level = 1;
int index = 2;
Integer element = spiderWeb.get(level, index);
System.out.println("Element at level " + level + " and index " + index + ": " + element);
```

Remove elements from the SpiderWeb using the removeFirst or removeLast methods.
```java
Integer removedElement = spiderWeb.removeFirst();
System.out.println("Removed element: " + removedElement);
```

## Examples

```java
SpiderWeb<String> stringSpiderWeb = new SpiderWeb<>();

stringSpiderWeb.add("One");
stringSpiderWeb.add("Two");
stringSpiderWeb.add("Three");

System.out.println("SpiderWeb size: " + stringSpiderWeb.size());
System.out.println("First element: " + stringSpiderWeb.getFirst());
System.out.println("Last element: " + stringSpiderWeb.getLast());
stringSpiderWeb.print();

```

## Documentation

For detailed information about the classes and methods provided by the SpiderWeb Java Library, refer to the [API Reference](https://common-kestrel.github.io/spider-web/com/spiderweb/package-summary.html).
