This is an application for determining the level of illumination in a room made as a course project for the university.
This is one of the parts of the project.
The general structure of the Arduino system -> server -> mobile application




## Screenshots
![Image alt](https://github.com/MaksimNK/illumination-checker/raw/main/Screenshot_20240515_052210.png)
![photo_2022-03-05_18-46-15](https://user-images.githubusercontent.com/70539485/156884720-3ffde86d-b96a-4b63-85fb-af163943d416.jpg)


## Dependency 

```javascript
   implementation 'androidx.appcompat:appcompat:1.4.1'
    implementation 'com.google.android.material:material:1.5.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.3'
    implementation 'androidx.legacy:legacy-support-v4:1.0.0'
    testImplementation 'junit:junit:4.13.2'
    androidTestImplementation 'androidx.test.ext:junit:1.1.3'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.4.0'

    implementation 'com.gauravk.bubblenavigation:bubblenavigation:1.0.7'
```


## Tech Stack

**Client:** Java, Android studio, xml

**Server:** 
Firebase


## Authors

- [@AbhishekGupta](https://github.com/Tesla-gamer)







## Important settings for third party library

```javascript
Add this statement in gradle properties --> android.enableJetifier=true
Add this statement in settings.gradle-->plugin management -->> maven{
                                                                   url("https://jcenter.bintray.com")
      
                                                                 }

dependency resoulution maven{
                             url("https://jcenter.bintray.com")
      
                           }
```
