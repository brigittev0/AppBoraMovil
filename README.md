<a name="readme-top"></a>
<div align="center">
<a href="https://github.com/brigittev0/AppBoraMovil">
  <img width="300px" src="./images/logotipo.png" alt="Logo" width="800" />
</a>
<a href="https://git.io/typing-svg"><img src="https://readme-typing-svg.demolab.com?font=Fira+Code&weight=600&size=42&pause=1000&color=D3D302&background=FFFFFF00&center=true&random=false&width=710&height=70&lines=APP+M%C3%B3vil+-+Bodega+Bora+Bora;Android+-+Kotlin" alt="Typing SVG" /></a>
</div>

<br>

![core-ktx](https://img.shields.io/badge/core--ktx-1.9.0-blue)
![appcompat](https://img.shields.io/badge/appcompat-1.6.1-blue)
![material](https://img.shields.io/badge/material-1.11.0-blue)
![constraintlayout](https://img.shields.io/badge/constraintlayout-2.1.4-blue)
![lifecycle-livedata-ktx](https://img.shields.io/badge/lifecycle--livedata--ktx-2.7.0-blue)
![lifecycle-viewmodel-ktx](https://img.shields.io/badge/lifecycle--viewmodel--ktx-2.7.0-blue)
![navigation-fragment-ktx](https://img.shields.io/badge/navigation--fragment--ktx-2.7.7-blue)
![navigation-ui-ktx](https://img.shields.io/badge/navigation--ui--ktx-2.7.7-blue)
![retrofit](https://img.shields.io/badge/retrofit-2.9.0-blue)
![gson](https://img.shields.io/badge/gson-2.8.9-blue)
![glide](https://img.shields.io/badge/glide-4.16.0-blue)
![firebase-auth](https://img.shields.io/badge/firebase--auth-21.0.1-blue)
![play-services-auth](https://img.shields.io/badge/play--services--auth-19.2.0-blue)
![room-runtime](https://img.shields.io/badge/room--runtime-2.6.1-blue)
![kotlinx-coroutines-core](https://img.shields.io/badge/kotlinx--coroutines--core-1.3.6-blue)
![kotlinx-coroutines-android](https://img.shields.io/badge/kotlinx--coroutines--android-1.3.6-blue)

<br>

## 📌 Repositorios relacionados
Este proyecto consta de dos partes principales:
1. [API-REST-BoraBora-Kotlin](https://github.com/CarlosAcosta4/apirest-borabora-android-kotlin): Este es el repositorio de la API REST que proporciona los servicios backend para las aplicaciones.
2. [AppBoraBora-Kotlin](https://github.com/brigittev0/AppBoraMovil): Este es el repositorio de la aplicación móvil que consume los servicios proporcionados por la API REST.

Para más información, por favor visita cada uno de los repositorios mencionados.

<br>

## 💻 Requerimientos
| Requerimientos | Versión |
|-----------|---------|
| Gradle | 8.0 |
| Android Gradle Plugin | 8.1.2 |
| Kotlin | 1.9.0 |
| SDK de Android | 34 |
| Nivel de API mínimo | 28 |
| Nivel de API objetivo | 33 |
| Java | 17 |

<br>

## 🚀 Pasos para la Ejecución
1. Clona el repositorio del proyecto:
```bash 
https://github.com/brigittev0/AppBoraMovil
```
2. Abre el proyecto en Android Studio.
3. Actualiza tu dirección IP en el archivo `util/Constants.kt`:
    ```kotlin
    object Constants {
        //Base URL
        const val BASE_URL = "http://<TU_IP>:8070/api/v1/"
    }
    ```
    Reemplaza `<TU_IP>` con tu dirección IP.

4. Actualiza tu dirección IP en el archivo `res/xml/network_security_config.xml`:
    ```xml
    <network-security-config>
        <domain-config cleartextTrafficPermitted="true">
            <domain includeSubdomains="true"><TU_IP></domain>
        </domain-config>
    </network-security-config>
    ```
    Reemplaza `<TU_IP>` con tu dirección IP.
5. Compila y ejecuta la aplicación en un emulador o dispositivo físico.

Por favor, verifica el cuadro de requerimientos y asegúrate de tener instalado todo lo que se menciona allí en tu entorno de desarrollo.

<br>

## ❗ Detalles Importantes del Proyecto
- IDE Utilizado: Este proyecto fue desarrollado en Android Studio Giraffe, versión 2022.3.1. Es importante tener en cuenta esto al configurar tu entorno de desarrollo para trabajar en este proyecto."

<br>

<div align="center">
  
## 🔹 Arquitectura del Proyecto Bodega BoraBora
![Arquitectura](./images/Arquitectura.png)
</div>

<br>

## 📷 Capturas de pantalla

<table style="width: 100%">
  <tr>
    <td style="width: 33.33%"><img src="CapturasPantalla/img00.jpg" width=100% height=auto></td>
    <td style="width: 33.33%"><img src="CapturasPantalla/img01.jpg" width=100% height=auto></td>
    <td style="width: 33.33%"><img src="CapturasPantalla/img02.jpg" width=100% height=auto></td>
  </tr>
  <tr>
    <td><img src="CapturasPantalla/img03.jpg" width=100% height=auto></td>
    <td><img src="CapturasPantalla/img04.jpg" width=100% height=auto></td>
    <td><img src="CapturasPantalla/img05.jpg" width=100% height=auto></td>
  </tr>
  <tr>
    <td><img src="CapturasPantalla/img06.jpg" width=100% height=auto></td>
    <td><img src="CapturasPantalla/img07.jpg" width=100% height=auto></td>
    <td><img src="CapturasPantalla/img08.jpg" width=100% height=auto></td>
  </tr>
  <tr>
    <td><img src="CapturasPantalla/img09.jpg" width=100% height=auto></td>
    <td><img src="CapturasPantalla/img10.jpg" width=100% height=auto></td>
    <td><img src="CapturasPantalla/img11.jpg" width=100% height=auto></td>
  </tr>
  <tr>
    <td><img src="CapturasPantalla/img12.jpg" width=100% height=auto></td>
    <td><img src="CapturasPantalla/img13.jpg" width=100% height=auto></td>
    <td><img src="CapturasPantalla/img14.jpg" width=100% height=auto></td>
  </tr>
  <tr>
    <td><img src="CapturasPantalla/img15.jpg" width=100% height=auto></td>
    <td><img src="CapturasPantalla/img16.jpg" width=100% height=auto></td>
    <td><img src="CapturasPantalla/img17.jpg" width=100% height=auto></td>
  </tr>
  <tr>
    <td><img src="CapturasPantalla/img18.jpg" width=100% height=auto></td>
    <td><img src="CapturasPantalla/img19.jpg" width=100% height=auto></td>
    <td><img src="CapturasPantalla/img20.jpg" width=100% height=auto></td>
  </tr>
  <tr>
    <td><img src="CapturasPantalla/img21.jpg" width=100% height=auto></td>
    <td><img src="CapturasPantalla/img22.jpg" width=100% height=auto></td>
    <td><img src="CapturasPantalla/img23.jpg" width=100% height=auto></td>
  </tr>
  <tr>
    <td><img src="CapturasPantalla/img24.jpg" width=100% height=auto></td>
    <td><img src="CapturasPantalla/img25.jpg" width=100% height=auto></td>
    <td><img src="CapturasPantalla/img26.jpg" width=100% height=auto></td>
  </tr>
  <tr>
    <td><img src="CapturasPantalla/img27.jpg" width=100% height=auto></td>
    <td><img src="CapturasPantalla/img28.jpg" width=100% height=auto></td>
    <td><img src="CapturasPantalla/img29.jpg" width=100% height=auto></td>
  </tr>
</table>


<br>

## 💻 Autores
<a href="https://github.com/CarlosAcosta4/apirest-borabora-android-kotlin/graphs/contributors">
  <img src="https://contrib.rocks/image?repo=CarlosAcosta4/apirest-borabora-android-kotlin" />
</a>

<p align="right">(<a href="#readme-top">volver arriba</a>)</p>
