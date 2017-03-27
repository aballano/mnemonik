# MnemoniK 
[![](https://jitpack.io/v/aballano/MnemoniK.svg)](https://jitpack.io/#aballano/MnemoniK) <a href="http://www.methodscount.com/?lib=com.github.aballano%3AMnemoniK%3A1.0.0"><img src="https://img.shields.io/badge/Methods and size-core: 69 | 13 KB-e91e63.svg"/></a>
[![Kotlin version badge](https://img.shields.io/badge/kotlin-1.1.1-blue.svg)](http://kotlinlang.org/)
  [![Hex.pm](https://img.shields.io/hexpm/l/plug.svg)](http://www.apache.org/licenses/LICENSE-2.0)

Simple [memoization](https://en.wikipedia.org/wiki/Memoization) extension function for Kotlin 


## Rationale
> Suppose you have a performance-intensive function that you must call repeatedly. 
A common solution is to build an internal cache (...) Memoization is a feature built into a programming language that enables automatic caching of recurring function-return values.

Functional Thinking - Neal Ford

Kotlin doesn't have yet any similar feature in it's tools. Although it might have it at some point I wanted to experiment a bit with this technique so that's why I created the lib. 


## Important
**Functions must be pure** for the caching technique to work. A pure function is one that has no side effects: it references no other mutable class fields, doesn't set any values other than the return value, and relies only on the parameters for input.
Obviously, you can reuse cached results successfully only if the function reliably returns the same values for a given set of parameters.

Also, when passing or returning Objects, make sure to **implement both `equals` and `hashcode`** for the cache to work properly!

## Usage
Having a function like

```kotlin
fun anExpensiveFun(someArg: Int, someOtherArg: Boolean): String = { ... }
```

You can create a memoized version of it by just calling an extension function over it like this:

```kotlin
val memoized = ::anExpensiveFun.memoize()
```

Now `memoized` is the same function as `anExpensiveFun` but is wrapped in a Closure that contains an internal cache, meaning that the first call to:
 ```kotlin
memoized(5, true)
```
Will just execute the function. But the second call **with the same arguments** will retrieve the already calculated value from cache.

Note that we're storing values in a **memory cache**, so try to have that in consideration when doing a relatively big amount of calls to your memoized function or if you use big objects as parameters or return type.

If you want to specify how big the cache has to be you can do it like the following:

```kotlin
val memoized = ::anExpensiveFun.memoize(50)
```
By default the cache size is initialized with 256.

###### Note: Memoize works on functions with up to 5 parameters. For more parameters feel free to create a pull request.
##### You can see a full example in the Sample project.

## Distribution

Add as a dependency to your `build.gradle`
```groovy
repositories {
    ...
    maven { url 'https://jitpack.io' }
}

dependencies {
    compile 'com.github.aballano:MnemoniK:1.0.0'
}
```
or to your `pom.xml`

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependency>
    <groupId>com.github.aballano</groupId>
    <artifactId>MnemoniK</artifactId>
    <version>1.0.0</version>
</dependency>
```

## License

Copyright (C) 2017 Alberto Ballano

The Apache Software License, Version 2.0

See LICENSE
