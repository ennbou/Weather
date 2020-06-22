# Application du meteo

App meteo est une tres simple application qui consommer un API, ce dernier est un API de meteo gratuit pour une limite utilisation, et cette application se fait en cadre de projet evalue au sein de l'ecole.

<table border="0">
 <tr>
    <td><b style="font-size:30px">Record</b></td>
    <td><b style="font-size:30px">description</b></td>
 </tr>
 <tr>
    <td  width="30%"><img src="/screens/app_meteo.gif"></td>
    <td>
    L’application Météo besoin d'accès à l'internet pour envoyer une demande et recevoir les données du serveur par l’API.</br>
    Pendant le démarrage de l’application, une auto-demande les informations de météo de la ville Casablanca, cette demande faire par la libraire Volley, qui exécute la demande dans un thread indépendamment a le thread de UI. Lorsque les données reçus et la demande complété avec succès, les donnes sont stockées dans des variables locales, pour par la suite met les dans l'interface graphique.</br>
    Le nom d’image de statuts de météo elle existe dans les données reçus par la premier demande (Volley), pour récupère cette image, j’ai utilisé la libraire Picasso, c’est très simple à utiliser et très utile pour chargement les images.</br>
    Le menu contient à une seule action pour faire la recherche ou pour saisir le nom de la ville, pour demande par la suite les informations de météo de cette ville, si la ville existe et le demande complète avec succès, les donnees mettent à l'interface graphique, sinon l'interface graphique ne change pas.</td>
 </tr>
</table>


## Partie de UI 

<img src="/screens/ui.jpg" width="35%">

##  Partie backend



[![API](https://img.shields.io/badge/openweathermap-orange?label=API)](https://openweathermap.org/api) 
[![Volley](https://img.shields.io/badge/Volley-green?label=library&logo=android)](https://developer.android.com/training/volley) 
[![Picasso](https://img.shields.io/badge/Picasso-red?label=library&logo=square)](https://square.github.io/picasso/) 
 
 
 
**Premièrement**, les dépendances de Volley et Picasso.

```
implementation 'com.android.volley:volley:1.1.1'
implementation 'com.squareup.picasso:picasso:2.71828'
```

**Deuxièmement**, la permission pour access a l'internet
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


Creation une demande du donnees de meteo. [`Simple Request`](https://developer.android.com/training/volley/simple) 
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
```
Comme on peut l'utiliser le [JsonObjectRequest](https://developer.android.com/training/volley/request) 
pour analyser et traduire les donnees à se formes des objets dans un thread séparer.
L'objet `StringRequest` il me donne une resultat à se forme d'un String, alors on a besoin de `Parsing Responses`. 
et parfois le parsing des donnees besoin d'un temps, alors c'est obligé pour le faire ca dans un thread separer.




