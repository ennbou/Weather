# TP5

[![Watch the video](https://i.imgur.com/vKb2F1B.png)](/screens/app_meteo.mp4)

[Demo](/screens/app_meteo.gif)

API utlise dans cette simple application 
[![API](https://img.shields.io/badge/openweathermap-orange?label=API&query=https://openweathermap.org/api)](https://openweathermap.org/api) 

Ces sont les deux chemain URL qui j'utlise pour recupaier les information de meteo.


```java
final String URL = "https://api.openweathermap.org/data/2.5/weather?q=%s&units=metric&appid=key";
final String URL_ICON = "http://openweathermap.org/img/wn/%s@2x.png";
```