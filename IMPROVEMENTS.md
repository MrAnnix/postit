# Mejoras

- Añadida casilla "remember me" al formulario de login. Si esta casilla se marca, se otorga una cookie con una expiración de un día, en vez de solo la de sesión. De esta forma, aunque el usuario cierre el navegador, al volverlo a abrir, mantiene su sesión iniciada.

- En el formulario de registro, se verifica desde el lado de cliente mediante JS que las contraseñas sean iguales y que cumplan una mínima longitud (8 caracteres). Si esto no es así, el botón de envío del formulario aparecerá desactivado y habrá un indicativo visual de que algo sucede con las contraseñas.

- En el formulario de escritura de un nuevo mensaje, se verifica que dicho mensaje no supere la cantidad total de 280 caracteres. Habiendo aquí también un indicativo visual. Si el mensaje supera dicha cantidad, el contador aparecerá en rojo y el botón enviar (avión de papel) estará desactivado. Si no se escribe nada, también estará desactivado.

- Se han añadido páginas de error personalizadas (hay sorpresas). Ahora ya no aparece la traza del error, para verla, habrá que recurrir a los logs.

- Se usa el servicio reCaptcha v3 de Google en el formulario de registro. Se ha añadido el correspondiente controlador. Para configurarlo hay que editar el fichero de propiedades (application.properties) con las claves correspondientes.

- Implementada función de seguir a los usuarios. En la vista principal, si no se sigue a ningún usuario, se muestran los últimos 10 mensajes de la red, si no, se mostrarán los 10 últimos mensajes de los perfiles a los que sigues (si los hubiera). Se ha añadido una pestaña llamada "discover" que muestra los 20 últimos mensajes de la red, para facilitar el descubrimiento de nuevos usuarios.

- Se ha añadido la funcionalidad "me gusta" a los mensajes. Ahora, los usuarios pueden marcar como favoritos los mensajes que ellos elijan.

- Los usuarios obtienen una imagen de fondo de perfil de forma aleatoria durante el registro.

- Los usuarios pueden establecer una imagen de perfil y una descripción a traves de Gravatar. Pueden elegir sincronizar la información al registrarse o posteriormente desde la pestaña de ajustes (///settings/).
