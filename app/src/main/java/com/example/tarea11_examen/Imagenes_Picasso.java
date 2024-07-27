package com.example.tarea11_examen;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.tarea11_examen.R;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class Imagenes_Picasso extends AppCompatActivity {

    ImageView image1;
    ImageView image2;
    ImageView image3;
    ImageView image4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imagenes_picasso);

        // Configuracion de la Toolbar o Barra de Herramientas como ActionBar
        Toolbar toolbar = findViewById(R.id.action_imagenes);
        setSupportActionBar(toolbar);
        // Habilitar el botón de retroceso en la barra de herramientas
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        image1 = findViewById(R.id.image1);
        image2 = findViewById(R.id.image2);
        image3 = findViewById(R.id.image3);
        image4 = findViewById(R.id.image4);

        // Cargado de las imágenes usando Picasso
        Picasso.get()
                .load("https://www.guiarepsol.com/content/dam/repsol-guia/contenidos-imagenes/viajar/vamos-de-excursion/paseo-centro-murcia-catedral-casino/gr-cms-media-featured_images-none-399a270f-8ad8-4e45-b613-5eb1861afda8-_01catedral-murcia.jpg")
                .resize(300, 300)
                .centerCrop()
                .into(image1);

        Picasso.get()
                .load("https://imagenes.20minutos.es/files/image_1920_1080/uploads/imagenes/2023/04/05/la-ciudad-de-las-artes-y-las-ciencias-valencia.jpeg")
                .resize(300, 300)
                .centerCrop()
                .into(image2);

        Picasso.get()
                .load("https://res.cloudinary.com/hello-tickets/image/upload/c_limit,f_auto,q_auto,w_1300/v1659907688/ffogv2qkwl1hqqxe0y97.jpg")
                .resize(300, 300)
                .centerCrop()
                .into(image3);

        Picasso.get()
                .load("https://images.ecestaticos.com/BxsG7Df49rOLuMsjIJGxlpFWuvA=/0x0:2272x1515/1200x900/filters:fill(white):format(jpg)/f.elconfidencial.com%2Foriginal%2F4eb%2Fa74%2F24e%2F4eba7424e74042f9492704a7a6718ec5.jpg")
                .resize(300, 300)
                .centerCrop()
                .into(image4);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Manejar el clic en el botón de retroceso para volver a la pantalla anterior
        if (item.getItemId() == android.R.id.home) {
            finish(); // Cerrar la actividad actual
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}


