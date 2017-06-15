# DownloadProgress
Telegram and whatsapp like download progress indicator

<img width="250" heigth="444" src="http://i.imgur.com/q8DqqnY.gif" />

## Usage

Add the library to your build.gradle
```gradle
compile 'com.aloj.progress:download-progress:1.0'
```
Add support for vector drawable compat
```gradle
android {
    defaultConfig { 
        vectorDrawables.useSupportLibrary = true
    }
}
```


Add DownloadProgressView to your xml
```xml
<com.aloj.progress.DownloadProgressView
        android:layout_width="45dp"
        android:layout_height="45dp"/>
```

Set progress
```java
setProgress() //min 0, max 1
```


## Developed By

Alonso Sanchez 

<a href="https://twitter.com/aloj22">
  <img alt="Follow me on Twitter" width="30" heigth="30"
       src="http://icons.iconarchive.com/icons/danleech/simple/96/twitter-icon.png" />
</a>
<a href="https://plus.google.com/100388148261962342252">
  <img alt="Follow me on Google+"  width="30" heigth="30"
       src="http://icons.iconarchive.com/icons/danleech/simple/96/google-plus-icon.png" />
</a>
<a href="https://www.linkedin.com/in/alonsosanchezlinkedin">
  <img alt="Follow me on LinkedIn" width="30" heigth="30"
       src="http://icons.iconarchive.com/icons/danleech/simple/96/linkedin-icon.png" />
</a>


## License

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
