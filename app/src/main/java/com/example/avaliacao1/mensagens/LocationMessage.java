package com.example.avaliacao1.mensagens;

import com.example.avaliacao1.pacote.Localizacao;
import com.google.gson.Gson;

public class LocationMessage {
    private String deviceId;
    private Localizacao localizacao;

    public LocationMessage(String deviceId, Localizacao localizacao) {
        this.deviceId = deviceId;
        this.localizacao = localizacao;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static LocationMessage fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, LocationMessage.class);
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Localizacao getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(Localizacao localizacao) {
        this.localizacao = localizacao;
    }
}

