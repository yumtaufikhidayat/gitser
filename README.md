# gitser
This application is made to complete Belajar Fundamental Aplikasi Android course by Dicoding

### Hardware
- CPU : Intel© Core™ i3-6006U CPU @ 2.0GHz
- Memory : 2 x 4 GB RAM
- Graphics : Intel HD Graphics 520

### Software
#### Operating System
- OS Name : Linux Mint (based on Ubuntu 18.04 LTS)
- Version : 19.3
- Platform : 64 bit

#### Programming Language
- Language Name : Java
- Version : 11.0.8

#### IDE (Integrated Development Environment)
- IDE Name : Android Studio
- Version : 4.0.1

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
##### By Default
        - implementation fileTree(dir: "libs", include: ["*.jar"])
        - implementation 'androidx.appcompat:appcompat:1.2.0'
        - implementation 'androidx.constraintlayout:constraintlayout:2.0.0'
        - implementation 'androidx.legacy:legacy-support-v4:1.0.0'
        - testImplementation 'junit:junit:4.12'
        - androidTestImplementation 'androidx.test.ext:junit:1.1.1'
        - androidTestImplementation 'androidx.test.espresso:espresso-core:3.2.0'

##### By Third Parties
- Material design

        - implementation 'com.google.android.material:material:1.2.0'
        - implementation 'androidx.swiperefreshlayout:swiperefreshlayout:1.1.0'

- Circle image

        - implementation 'de.hdodenhof:circleimageview:3.1.0'

- Glide

        - implementation 'com.github.bumptech.glide:glide:4.11.0'

- View Model

        - implementation 'androidx.lifecycle:lifecycle-extensions:2.2.0'
        - annotationProcessor 'androidx.lifecycle:lifecycle-compiler:2.2.0'

- Toasty

        - implementation 'com.github.GrenderG:Toasty:1.5.0'
