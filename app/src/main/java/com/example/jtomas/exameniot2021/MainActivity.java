package com.example.jtomas.exameniot2021;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {


    // 5. RECICLERVIEW
    Adaptador adaptador;

    // 6. MQTT
    public static final String topicRoot="examen/";
    public static final int qos = 1;
    public static final String broker = "tcp://broker.hivemq.com:1883";
    public static final String clientId = "343134789";
    private static MqttClient client;

    // 1. JAVA
    static String SENSOR[]={"a","b","c","c","a","b","a","a"};
    static long   TIEMPO[]={  1,  3,  4,  5,  6,  7,  8, 10};
    static double  VALOR[]={1.2,1.1,1.2,1.1,1.1,1.3,1.4,1.3};

    List<Lectura> obtenerLecturas(String[] sensor, long[] tiempo, double[] valor) {
        List<Lectura> lista = new ArrayList<>();
        for (int n = 0; n < sensor.length; n++) {
            lista.add(new Lectura(sensor[n], tiempo[n], valor[n],
                    "https://dawe.gg/img.png"));
        }
        return lista;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 1. JAVA
        List<Lectura> lecturas = obtenerLecturas(SENSOR, TIEMPO, VALOR);
        Log.d("EXAMEN", lecturas.toString());

        // 2. ALGORITMO
        // Mejor poner el algoritmo en un método aparte.
        // En un examen podemos poner el código directamente para ganar tiempo.
        Set<String> sensores = new HashSet<>(); //Buscamos tipos de programa
        for (int n = 0; n < SENSOR.length; n++) {
            sensores.add(SENSOR[n]);
        }
        for (String sensor : sensores) {// para cada sensor
            long masLargo = 0;          // duración del programa más largo hasta ahora
            long tiempoAnterior = -1;   // la primera vez no hay tiempo anterior
            for (int n = 0; n < SENSOR.length; n++) {     //Recorremos los arrays
                if (sensor.equals(SENSOR[n])) {           //Solo para el sensor actual
                    if (tiempoAnterior!=-1) {
                        long duracion = TIEMPO[n]-tiempoAnterior; //tiempo entre activaciones
                        if (masLargo < duracion) {                //nuevo máximo
                            masLargo = duracion;
                        }
                    }
                    tiempoAnterior = TIEMPO[n];
                }
            }
            Log.d("EXAMEN", sensor+" -> "+masLargo);
        }

        //3. ESCRITURA DE DATOS
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        for (Lectura lectura:lecturas){
            db.collection("lecturas").document(""+lectura.getTiempo()).set(lectura);
        }

        //4.LECTURA DE DATOS
        Button button =findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = findViewById(R.id.editText);
                String tiempo = editText.getText().toString();
                TextView textView = findViewById(R.id.textView);

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("lecturas").document(tiempo).get()
                        .addOnCompleteListener(
                                new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task){
                                        if (task.isSuccessful()) {
                                            double valor = task.getResult().getDouble("valor");
                                            textView.setText(""+valor);
                                        }
                                    }
                                });
            }
        });

        //5. RECICLERVIEW
        RecyclerView recyclerView = findViewById(R.id.reciclerView);
        Query query = FirebaseFirestore.getInstance()
                .collection("lecturas")
                //6. CONSULTA
                //NOTA: Estamos filtrando por "tipo" y ordenado por "nombre" -> necesario índice doble
                // Podemos ir a la consola Firbase y crearlo. Mejor ejecutar, en el logCat aparece un error,
                // pulsamos sobre el link que generará el índice doble.
                .whereEqualTo("sensor", "a")
                .orderBy("valor", Query.Direction.DESCENDING)
                .limit(3);

        FirestoreRecyclerOptions<Lectura> opciones = new FirestoreRecyclerOptions
                .Builder<Lectura>().setQuery(query, Lectura.class).build();
        adaptador = new Adaptador(this, opciones);
        recyclerView.setAdapter(adaptador);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 7. MQTT
        try {
            Log.d("EXAMEN", "Conectando al broker " + broker);
            client = new MqttClient(broker, clientId, new MemoryPersistence());
            client.connect();
        } catch (MqttException e) {
            Log.e("EXAMEN", "Error al conectar.", e);
        }
        Button buttonMqtt =findViewById(R.id.buttonMqtt);
        buttonMqtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = findViewById(R.id.editText);
                String texto = editText.getText().toString();
                publicarMqtt("tokenA", texto);
            }
        });
    }

    public static void publicarMqtt(String topic, String mensageStr) {
        try {
            MqttMessage message = new MqttMessage(mensageStr.getBytes());
            message.setQos(qos);
            message.setRetained(false);
            client.publish(topicRoot + topic, message);
            Log.i("EXAMEN", "Publicando mensaje: " + topic+ "->"+mensageStr);
        } catch (MqttException e) {
            Log.e("EXAMEN", "Error al publicar." + e);
        }
    }

    //5. RECICLERVIEW
    @Override
    public void onStart() {
        super.onStart();
        adaptador.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adaptador.stopListening();
    }

}