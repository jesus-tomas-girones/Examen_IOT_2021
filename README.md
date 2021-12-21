Solución examen práctico "Proyecto IoT y Apps" de 2021

### Proyecto IoT y Apps Móviles

### Grado en Tecnologías Interactivas (2º cuatr. A)  Universidad Politécnica de Valencia

# Examen Final Practico (A)

### Alumno: _______________________ Hora inicio: ______ entrega: ________   Fecha: 20/12/2021

**ADVERTENCIA:** Puede consultarse cualquier tipo de información propia o disponible a través de Internet. No obstante, queda terminantemente prohibido la comunicación con cualquier persona durante la realización del examen. El envío o recepción de correos electrónicos, WhatsApp, compartición de ficheros, uso de redes sociales, etc. supondrá el suspenso automático de la asignatura.

**DESCRIPCIÓN:** Dispones de un máximo de dos horas y 15 minutos para realizar el mayor número de pasos y entregar el resultado en una tarea de Poliformat. Ver instrucciones de entrega al final: 

**0.**	Crea un nuevo proyecto con los datos … (reemplaza usuario_upv por tu usuario de correo sin @...): 

```
Project: Phone and Tablet / Empty Activity
Name: Examen usuario_upv
Package name: com.example.usuario_upv.examentipo_a
Minimum API level: 19 Android 4.4 (KitKat)
```

**1. JAVA**: NOTA: Ha de funcionar, aunque se cambie los array de entrada. (15 min)

a) Añade las siguientes constantes. Representan los valores registrados para diferentes sensores y el tiempo en el que se realizó la lectura. La lista está ordenada de forma cronológica.
```
static String SENSOR[]={"a", "b", "c", "a", "c", "b", "a", "a"};
static long   TIEMPO[]={  1,   2,   4,   5,   6,   7,   8,  9};
static double  VALOR[]={0.2, 0.1, 0.2, 0.1, 0.1, 0.3, 0.4, 0.3};
```
b) Crea una clase POJO con cuatro propiedades. Tres sacadas de las listas anteriores y una con nombre urlFoto de tipo String. Ha de tener un constructor que inicialice todas las propiedades y el método toString(). 
c) Crea un método que reciba como parámetros las tres listas que hemos creado. El método debe devolver una lista, con tantos objetos de la clase POJO como elementos haya en las listas. El campo urlFoto tendrá siempre el valor https://dawe.gg/img.png. Muestra el resultado en el LogCat. (10 min)

**2. ALGORITMO**: Para cada uno de los sensores obtén el tiempo máximo entre activaciones. Es decir, si un sensor se ha activado tres veces, calcula el tiempo trascurrido de la primera a la segunda activación, luego de la segunda a la tercera. El valor a mostrar es el mayor de los dos. Muestra el tiempo máximo entre activaciones para cada sensor en el LogCat. (20 min)

**CONEXIÓN A FIREBASE**: Conecta la aplicación creada a un proyecto Firebase. Puedes crear un nuevo proyecto o conectar la aplicación a un proyecto previamente creado. (5 min)

**3. ESCRITURA DE DATOS**: En el método onCreate() crea una nueva colección utilizando Firestore, donde se almacene la lista con los 8 POJOS creados en el punto 1. Usa como clave (nombre de los documentos) el valor de TIEMPO (NOTA: has de pasarlo a String).  (15 min)

**4. LECTURA DE DATOS**: Añade un EditText, un Button y TextView en la parte superior del layout. Al pulsar el botón se leerá el documento con nombre igual al valor del EditText anterior y se mostrará el VALOR correspondiente a esa lectura en el TextView. Puedes suponer que siempre se introducen valores correctos. (15 min)

**5. RECICLERVIEW**: Añade un RecyclerView en la parte inferior del layout principal. En el RecyclerView se mostrará toda la información de cada POJO almacenado. Para el campo urlFoto se visualizará la foto correspondiente a la url almacenada.(20 min)

**6. CONSULTA**: Haz que en el RecyclerView anterior se muestren solo los POJOS del sensor “a” ordenados por VALOR de forma descendiente y con un máximo de 3 elementos. (10 min)

**7. MQTT**: Añade un botón en la parte superior con nombre “MQTT”. Al pulsarlo se conectará al bróker MQTT broker.hivemq.com y escribirá en el token examen/tokenB. el valor introducido en el EditText de la parte superior.  (15 min)

**8. LINUX**: Conéctate por ssh a una Raspberry Pi en la dirección 136.244.105.156. Utiliza tu usuario de UPV y como contraseña tu DNI.
a) Ejecuta el comando libcamera-jpeg -o imagen.jpg para tomar una foto con la cámara.
b) Copia el fichero creado a tu ordenador usando el comando scp <origen> <destino>. Visualiza el fichero, ha de mostrar una TV. (10 min)  

### Entrega de la práctica:

9.	Verifica que todo funciona correctamente antes de realizar el siguiente punto.

10.	Selecciona la opción Build / Build APK. Así generamos de nuevo el apk. 

11.	Selecciona la opción File / Export to Zip file...  ó File / Manage IDE Settings / Export to Zip file… para guardar el código del proyecto. 

12.	Para entregar usa la opción Tareas del menú de poli[Format]. Adjunta los ficheros jpeg, zip y apk.

13.	Para facilitar la corrección en el cuadro de texto, que encontrarás al entrar en la entrega de la tarea, indica los ejercicios realizados de la siguiente lista introduciendo una X entre los paréntesis. Añade los comentarios oportunos:

(_) 1.- JAVA 

(_)	2.- ALGORITMO

(_)	3.- ESCRITURA DE DATOS

(_)	4.- LECTURA DE DATOS

(_) 5.- RECICLERVIEW

(_) 6.- CONSULTA

(_) 7.- MQTT

(_) 8.- LINUX

Pega a continuación el fragmento de código realizado en el punto 2 ALGORITMO.

14.	Ejecuta el proyecto en un terminal o emulador, visualiza el jpeg y abre la consola de Firebase. Avisa al profesor para que lo evalúe.
