# MnemoniK 
[![](https://jitpack.io/v/aballano/mnemonik.svg)](https://jitpack.io/#aballano/mnemonik)
  [![Hex.pm](https://img.shields.io/hexpm/l/plug.svg)](http://www.apache.org/licenses/LICENSE-2.0)

Simple [memoization](https://en.wikipedia.org/wiki/Memoization) extension function for Kotlin 


## Rationale
> Suppose you have a performance-intensive function that you must call repeatedly. 
A common solution is to build an internal cache (...) Memoization is a feature built into a programming language that enables automatic caching of recurring function-return values.
>
> Functional Thinking - Neal Ford

Kotlin doesn't have yet any similar feature in it's tools. Although it might have it at some point I wanted to experiment a bit with this technique so that's why I created the lib. 


## Important
Functions must be pure for the caching technique to work.

A pure function is one that has no side effects: it references no other mutable class fields,
doesn't set any values other than the return value, and relies only on the parameters for input.

In other words, you can reuse cached results successfully only if the function reliably returns the
same values for a given set of parameters.

Also, when passing or returning Objects, make sure to implement both equals and hashcode for the cache to work properly.

## Usage
Having a function like:

```kotlin
fun anExpensiveFun(someArg: Int, someOtherArg: Boolean): String = { /*...*/ }
```

You can create a memoized version of it by just calling an extension function over its reference like this:

```kotlin
val memoized = ::anExpensiveFun.memoize()
```

Now `memoized` is the same function as `anExpensiveFun` but is wrapped in a lambda that contains an internal cache, meaning that the first call to:
 ```kotlin
memoized(5, true)
```
Will just execute the function and return the value. But a second call **with the same arguments** will retrieve the previous value from cache.

Note that we're storing values in a **memory cache**, so try to have that in consideration when doing a relatively big amount of calls to your memoized function or if you use big objects as parameters or return type.

If you want to specify how big the cache has to be you can do it like the following:

```kotlin
val memoized = ::anExpensiveFun.memoize(50)
```
By default the cache size is initialized with 256.


By default `HashMap` and `ConcurrentHashMap` are used as caches but you can also pass any `MutableMap` and `ConcurrentMap` instances which allows custom control of the cache.

```kotlin
val map = ConcurrentHashMap<Int, Boolean>(50)
val memoized = ::anExpensiveFun.memoize(cache = map)

// clear the cache at the end
map.clear
```

Note: The same approach also works for **suspend functions**.

## Limitations

Currently this library only supports up to 5 function parameters.

Note that the memoization **might not be thread safe** for the first call, subsequent calls are safe as they will simply retrieve from cache.

## Distribution

[Add as a dependency to your `build.gradle` with Jitpack](https://jitpack.io/private#aballano/mnemonik/2.1.1)

## License

````
MIT License

Copyright (c) 2022 aballano

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
