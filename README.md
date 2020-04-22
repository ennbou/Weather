# TP5
## Partie de UI dans le rapport
[rapport](screens/repport.pdf)
##  Partie backend

<img src="/screens/app_meteo.gif" width="30%">

[![API](https://img.shields.io/badge/openweathermap-orange?label=API)]
(https://openweathermap.org/api) 

[![Volley](https://img.shields.io/badge/Volley-green?label=library&logo=android)]
(https://developer.android.com/training/volley) 
 
 
Premièrement, les dépendances de Volley et Picasso.

```
implementation 'com.android.volley:volley:1.1.1'
implementation 'com.squareup.picasso:picasso:2.71828'
```

Deuxièmement, la permission pour access a l'internet
`android.permission.INTERNET` est une 
[Normal permissions](https://developer.android.com/guide/topics/permissions/overview#normal_permissions).
```xml
<manifest>
    <uses-permission android:name="android.permission.INTERNET" />
    <application><activity android:name=".MainActivity"></activity></application>
</manifest>
```

 
Volley initialisation (dans le main activity). Ensuite, nous pouvons ajouter des tâches pour le faire 
et tous les tâches sont executent dans un autre thread,c'est-à-dire, la tache va fait indépendamment
du `UI THREAD`.
```java
RequestQueue requestQueue = Volley.newRequestQueue(this); // this : context
``` 

le premier URL pour les donnees, et le deuxieme pour demande l'image de etat de meteo. 
[documentation](https://openweathermap.org/current)
```java
final String URL = "https://api.openweathermap.org/data/2.5/weather?q=%s&units=metric&appid=key";
final String URL_ICON = "http://openweathermap.org/img/wn/%s@2x.png";
```


Creation une demande du donnees de meteo
```java
StringRequest getData = new StringRequest(Request.Method.GET, URL,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    // met les données à son place sur le layout
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // traiter l'erreur
                                }
                            });
;    
```

[`Simple Request`](https://developer.android.com/training/volley/simple) 
Comme on peut l'utilise le [JsonObjectRequest](https://developer.android.com/training/volley/request) 
pour analyse et traduise les donnees a formes des objets dans un thread separi.
L'objet `StringRequest` il me donne une resultat à se forme de String, alors on a besoin de `Parsing Responses`.

