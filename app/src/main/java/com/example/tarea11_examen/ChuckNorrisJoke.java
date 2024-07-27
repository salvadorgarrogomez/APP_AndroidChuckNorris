package com.example.tarea11_examen;

import com.google.gson.annotations.SerializedName;

//  Configuracion del Endpoint de la API, cuenta con varios apartados, sin embargo en esta tarea solo se ha dado funcionamiento
// y utilidad al getValue para mostrar los datos.
public class ChuckNorrisJoke {
    @SerializedName("icon_url")
    private String iconUrl;
    private String id;
    private String url;
    private String value;

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}



