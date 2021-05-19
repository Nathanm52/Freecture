# Freecture

The main idea of our project is to provide to the user royalty-free images that are offered on the site "Unsplash". 
In order to provide this solution, we have developed a mobile application in Kotlin. 
This application uses the Unsplash API to retrieve images that the user can then use freely.

https://github.com/Nathanm52/Freecture

### Prerequisites

Android minimum API 21+
Android Studio 3.3+
Kotlin 1.3+
Use AndroidX artifacts when creating your project
Unsplash API Access Key and Secret Key

### Installing

### Gradle

To integrate `UnsplashPhotoPicker` into your Android Studio project using Gradle, specify in your project `build.gradle` file:

```gradle
allprojects {
   repositories {
      maven { url  "https://dl.bintray.com/unsplash/unsplash-photopicker-android" }
   }
}
```
And in your app module `build.gradle` file:

```gradle
dependencies {
   implementation 'com.unsplash.pickerandroid:photopicker:x.y.z'
}
```

## Usage

❗️Before you get started, you need to register as a developer on our [Developer](https://unsplash.com/developers) portal. Once registered, create a new app to get an **Access Key** and a **Secret Key**.


## License

MIT License

Copyright (c) 2019 Unsplash Inc.
