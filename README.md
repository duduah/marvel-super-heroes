# Marvel Heroes 💪🏻

Marvel Heroes es una app para Android que lista algunos de los súperheroes de Marvel con sus características.


![Demo][videoDemo]

## Dev environment and tools

- Kotlin
- MVP Clean (simplificado)
- Dagger2
- RxJava2
- Room

## Arquitectura
- Se ha adaptado una aplicación que inicialmente estaba implementada usando una arquitectura **MVP** Clean simplificada. Dicha aplicación se ha replanteado con una arquitectura **MVVM**.
- Se ha implementado la persistencia de datos con [Room][Android Room].

- Los componentes como el ActionBar, el uso de `notifyDataSetChanged()`, [Android KTX], etc. que había en la aplicación inicial (MVP) se han mantenido.

## Descripción de la práctica
### Objetivos alcanzados
- Se ha implementado la arquitectura MVVP.
  - Para la **lista de Marvel Heroes**. A través del `ViewModel` se **consulta** y se **actualiza** el modelo.
  - Para el **detalle de cada Marvel Hero**, donde también se **carga** y se **actualiza** la información del superhéroe correspondiente mediante el `ViewModel`.
- Se ha implementado el modelo de bbdd con **Room**. Para casar el modelo de datos con la API se ha creado una nueva que incluye, además, los campos **`id`** (pk) y **`favourite`**.
- Se ha implementado la funcionalidad de marcar un *Marvel Hero* como **favorito** desde ambas vistas: lista y detalle.

> **Nota**  
> Se ha invertido la carga de datos del repositorio: primero se carga la API y después la bbdd.  
> El motivo es que se ha invertido mucho tiempo en hacerlo en el orden lógico (primero bbdd y después API) pero se han encontrado muchos inconvenientes y no había tiempo).

### Objetivos pendientes
Debido al tiempo invertido en el punto anterior (orden de carga del repositorio):

- No se han implementado más tests.
- No se ha implementado el review.


<!-- Images -->
[videoDemo]: ./demo.gif "Demo de la práctica de Android Avanzado | Diego Gay Sáez"

<!-- Links -->
[performanceEnv]: http://expressjs.com/en/advanced/best-practice-performance.html "Performance Best Practices Usin Express | expressjs"

[GivenWhenThen]: https://martinfowler.com/bliki/GivenWhenThen.html "Given When Then | Martin Fowler"

[Android KTX]: https://github.com/android/android-ktx "Android KTX | Android GitHub"

[Android Room]: https://developer.android.com/topic/libraries/architecture/room "Room Persistence Library | Android Developers"