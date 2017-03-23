# KotlinMemoization [![](https://jitpack.io/v/aballano/KotlinMemoization.svg)](https://jitpack.io/#aballano/KotlinMemoization)
Simple memoization extension function for Kotlin https://en.wikipedia.org/wiki/Memoization


## Rationale
> Suppose you have a performance-intensive function that you must call repeatedly. 
A common solution is to build an internal cache (...) Memoization is a feature built into a programming language that enables automatic caching of recurring function-return values.

Kotlin doesn't have yet any similar feature in it's tools. Although it might have it at some point I wanted to experiment a bit with this technique so that's why I created the lib. 

> Note: Functions must be pure for the caching technique to work. A pure function is one that has no side effects: it references no other mutable class fields, doesn't set any values other than the return value, and relies only on the parameters for input.
Obviously, you can reuse cached results successfully only if the function reliably returns the same values for a given set of parameters.

Functional Thinking - Neal Ford

## Usage
Having a function like

```kotlin
fun anExpensiveFun(someArg: Int, someOtherArg: Boolean): String = { ... }
```

You can create a memoized version of it by just calling an extension function over it like this:

```kotlin
val memoized = ::anExpensiveFun.aballano.memoize()
```

Now `memoized` is the same function as `anExpensiveFun` but is wrapped in a Closure that contains an internal cache, meaning that the first call to:
 ```kotlin
memoized(5, true)
```
Will just execute the function. But the second call **with the same arguments** will retrieve the already calculated value from cache.

Note that we're storing values in a **memory cache**, so try to have that in consideration when doing a relatively big amount of calls to your memoized function or if you use big objects as parameters or return type.

If you want to specify how big the cache has to be you can do it like the following:

```kotlin
val memoized = ::anExpensiveFun.aballano.memoize(50)
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
    compile 'com.github.aballano:KotlinMemoization:1.0.0'
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
    <artifactId>KotlinMemoization</artifactId>
    <version>1.0.0</version>
</dependency>
```

## License

Copyright (C) 2017 Alberto Ballano

The Apache Software License, Version 2.0

See LICENSE
