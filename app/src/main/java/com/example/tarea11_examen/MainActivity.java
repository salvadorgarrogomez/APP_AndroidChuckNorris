package com.example.tarea11_examen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    ChuckNorrisService chuckNorrisService;
    Spinner spinner;
    Toolbar toolbar;
    ListView listView;
    TextView textViewJson;
    ArrayAdapter<String> listAdapter;
    private List<String> jokeList = new ArrayList<>(); // Array para hacer persistente el ListView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = findViewById(R.id.spinner);
        textViewJson = findViewById(R.id.textViewJson);
        listView = findViewById(R.id.listJson);

        listAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, jokeList);
        listView.setAdapter(listAdapter);

        // Crear una instancia de Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.chucknorris.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Crear una instancia del servicio
        chuckNorrisService = retrofit.create(ChuckNorrisService.class);
        // Configuracion de la Toolbar o Barra de Herramientas como ActionBar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Llamada al metodo nombvrado, para obtener una broma aleatoria al inicio
        obtenerBromaAleatoria();
        // Obtenemos todas las categorías disponibles  en la API llamando a este metodo
        // y se muestran en el Spinner
        obtenerCategorias();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Obtener la categoría seleccionada
                String categoriaSeleccionada = parent.getItemAtPosition(position).toString();
                obtenerBromaPorCategoria(categoriaSeleccionada);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Excepcion, en caso de no seleccionar ninguna opcion
            }
        });

        //  Boton de actualizar, de tal forma que al darle, obtenemos mas bromas, mas mensajes desde la API
        Button btnActualizar = findViewById(R.id.btnActualizar);
        btnActualizar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerBromaAleatoria();
            }
        });

        // Configuración del evento de selección del Spinner, necesario, para que este no modifique
        // no haga llamadas directamente a la API
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //  Con este metodo, lo que conseguimos, es que los mensajes de la API se vayan quedando guardados
        // en el array del ListView, y tambien la particularidad, de que al seleccionar una de sus lineas, esta
        // se muestra en el TextView del fragment dinamico.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Obtener el contenido del elemento seleccionado en el ListView
                String selectedContent = (String) parent.getItemAtPosition(position);
                // Mostrar el contenido seleccionado en el TextView
                textViewJson.setText(selectedContent);
            }
        });
    }

    private void obtenerBromaAleatoria() {
        // Llamada a la API para obtener una broma aleatoria
        Call<ChuckNorrisJoke> call = chuckNorrisService.getRandomJoke();
        call.enqueue(new Callback<ChuckNorrisJoke>() {
            @Override
            public void onResponse(Call<ChuckNorrisJoke> call, Response<ChuckNorrisJoke> response) {
                if (response.isSuccessful()) {
                    ChuckNorrisJoke joke = response.body();
                    // Llamada al método para mostrar la broma obtenida
                    mostrarDatos(joke);
                } else {
                    mostrarError("Error al obtener la broma aleatoria");
                }
            }

            @Override
            public void onFailure(Call<ChuckNorrisJoke> call, Throwable t) {
                mostrarError("Error de conexión al obtener la broma aleatoria");
            }
        });
    }


    private void obtenerCategorias() {
        // Realizar la llamada a la API para obtener las categorías disponibles, que seran mostradas en el
        // spinner
        Call<List<String>> call = chuckNorrisService.getCategories();
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful()) {
                    List<String> categories = response.body();
                    // Se actualiza el adaptador del Spinner con las categorías disponibles
                    ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_item, categories);
                    spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(spinnerAdapter);
                } else {
                    mostrarError("Error al obtener las categorías");
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                mostrarError("Error de conexión al obtener las categorías");
            }
        });
    }

    private void mostrarDatos(ChuckNorrisJoke joke) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (joke != null) {
                    // Se muestra la broma en el TextView
                    String jokeString = joke.getValue();
                    textViewJson.setText(jokeString);
                    // Se agrega la nueva broma a la lista existente en el ListView
                    jokeList.add(jokeString);
                    // Se notifica al adaptador de que los datos han cambiado
                    listAdapter.notifyDataSetChanged();
                } else {
                    textViewJson.setText("No se encontró ninguna broma");
                }
            }
        });
    }

    // Método para obtener una broma basada en la categoría seleccionada en el Spinner
    private void obtenerBromaPorCategoria(String categoria) {
        // Se realiza la llamada a la API para obtener una broma basada en la categoría seleccionada en el spinner
        Call<ChuckNorrisJoke> call = chuckNorrisService.getJokeByCategory(categoria);
        call.enqueue(new Callback<ChuckNorrisJoke>() {
            @Override
            public void onResponse(Call<ChuckNorrisJoke> call, Response<ChuckNorrisJoke> response) {
                if (response.isSuccessful()) {
                    ChuckNorrisJoke joke = response.body();
                    // Mostramos la broma relacionada con la categoría seleccionada en el TextView
                    textViewJson.setText(joke.getValue());
                } else {
                    mostrarError("Error al obtener la broma basada en la categoría seleccionada");
                }
            }

            @Override
            public void onFailure(Call<ChuckNorrisJoke> call, Throwable t) {
                mostrarError("Error de conexión al obtener la broma basada en la categoría seleccionada");
            }
        });
    }

    private void mostrarError(String mensaje) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, mensaje, Toast.LENGTH_SHORT).show();
            }
        });
    }

    //  Metoddo para dar utilidad y funcionamiento al boton de menu, creado en res/menu(main_menu.xml
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    //  Con este ultimo metodo, lo que conseguimos, es que al darle a los 3 puntos de la barra de herramientas
    // nos aparezca el nombre de la pantalla secundaria a la que queremos ir.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_imagenes) {
            Intent intent = new Intent(MainActivity.this, Imagenes_Picasso.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

