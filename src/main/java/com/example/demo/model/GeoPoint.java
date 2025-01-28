package com.example.demo.model;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GeoPoint {
    @JsonProperty("gcs")
    private String gcs;

    @JsonProperty("lati")
    private String lati;

    @JsonProperty("lati_d")
    private String latiD;

    @JsonProperty("longi")
    private String longi;

    @JsonProperty("longi_d")
    private String longiD;

    // Getter and Setter
    public String getGcs() {
        return gcs;
    }

    public void setGcs(String gcs) {
        this.gcs = gcs;
    }

    public String getLati() {
        return lati;
    }

    public void setLati(String lati) {
        this.lati = lati;
    }

    public String getLatiD() {
        return latiD;
    }

    public void setLatiD(String latiD) {
        this.latiD = latiD;
    }

    public String getLongi() {
        return longi;
    }

    public void setLongi(String longi) {
        this.longi = longi;
    }

    public String getLongiD() {
        return longiD;
    }

    public void setLongiD(String longiD) {
        this.longiD = longiD;
    }

    @Override
    public String toString() {
        return "GeoPoint{" +
                "gcs='" + gcs + '\'' +
                ", lati='" + lati + '\'' +
                ", latiD='" + latiD + '\'' +
                ", longi='" + longi + '\'' +
                ", longiD='" + longiD + '\'' +
                '}';
    }
}
