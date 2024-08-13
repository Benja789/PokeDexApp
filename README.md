
# PokeDex App

Applicacion donde se muestra un listado de pokemon


Requisitos desarrollo:
- Android Studio o compilador que soporte Kotlin
- Java Development Kotlin
- SDK de Android. SDK minimo 29, pero compilado para 34


Requsisitos de dispositivo Android: 
- Tener disposito android con android 10 o superior
- Acceso a internet 



## Instalación

Compilacion del proyecto mediante terminal o cmd

```bash
  cd PokeDexApp
  ./gradlew clean
  ./gradlew build
  ./gradlew assembleDebug
  adb devices
  adb install -r app/build/outputs/apk/debug/app-debug.apk
  adb shell am start -n com.pokedex.app/.MainActivity
  adb logcat
```

## Instalación mediante Android Studio
Abrir el proyecto con Android Studio e esperar que se termine de sincronizar el gradlew.
Una vez terminado, se conectaria el dispositivo android con la Depuracion USB activa (mas detalle en la documentación), o se ejecutaria el emulador.
Por ultimo se presionaria en le cuadro verde, con el simbolo de play o se presionaria Shift + F10 (en caso de Windows/Linux) o Control + R   
De ahi solo quedaria esperar que se instale en dispositivo fisico o virtual.
## Screenshots

![App Screenshot](https://benjadevsoft.com/Screenshot_1723572535.png)


## Autor

- [@Benja789](https://github.com/Benja789)
- https://benjadevsoft.com/

