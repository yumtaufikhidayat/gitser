# gitser
This application is made to complete Belajar Fundamental Aplikasi Android course by Dicoding

### Hardware
- CPU : Intel© Core™ i3-6006U CPU @ 2.0GHz
- Memory : 2 x 4 GB RAM
- Graphics : Intel HD Graphics 520

### Software
#### Operating System
- OS Name : Zorin OS (based on Ubuntu 18.04 LTS)
- Version : 15.2
- Platform : 64 bit

#### Programming Language
- Language Name : Kotlin
- Version : 1.3.72

#### IDE (Integrated Development Environment)
- IDE Name : Android Studio
- Version : 4.0

#### Java Build Tools
- Java Build Tools : Gradle
- Android Gradle Plugin Version : 4.0.0
- Android Gradle : 6.1.1

#### SDK Version and SDK Tools
- Target SDK Version : 29
- Min SDK Version : 22
- Android SDK Tools : 26.1.1

#### AndroidX
- Migrate to AndroidX : Yes

#### Dependencies
##### By Gradle
        - implementation fileTree(dir: "libs", include: ["*.jar"])
        - implementation "org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version"
        - implementation 'androidx.core:core-ktx:1.3.0'
        - implementation 'androidx.appcompat:appcompat:1.1.0'
        - implementation 'androidx.constraintlayout:constraintlayout:1.1.3'
        - testImplementation 'junit:junit:4.12'
        - androidTestImplementation 'androidx.test.ext:junit:1.1.1'
        - androidTestImplementation 'androidx.test.espresso:espresso-core:3.2.0'

##### By Third Parties
- Material design

        - implementation 'com.google.android.material:material:1.1.0'

- Circle image

        - implementation 'de.hdodenhof:circleimageview:3.1.0'

- Glide

        - implementation 'com.github.bumptech.glide:glide:4.11.0'
