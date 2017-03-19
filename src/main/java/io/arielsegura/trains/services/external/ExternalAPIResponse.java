package io.arielsegura.trains.services.external;

import com.fasterxml.jackson.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arielsegura on 3/18/17.
 */
public class ExternalAPIResponse {

    private Response response = new Response();

    public ExternalAPIResponse() {
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Response {
        List<Arrival> arrivals = new ArrayList<>();
        @JsonIgnore
        String metadata;

        public String getMetadata() {
            return metadata;
        }

        public Response setMetadata(String metadata) {
            this.metadata = metadata;
            arrivals.forEach(arrival -> arrival.setLine(metadata));
            return this;
        }

        public Response() {
        }

        public List<Arrival> getArrivals() {
            return arrivals;
        }

        public void setArrivals(List<Arrival> arrivals) {
            this.arrivals = arrivals;
        }
    }

    public static class Arrival {
        //TODO rename variables and use JSON Property
        String nombre = ""; // station
        String minutos_1 = ""; // first train to end of track (i.e. ezeiza)
        String minutos_2 = ""; // next train to end of track (i.e. ezeiza)
        String minutos_3 = ""; // first train to origin (i.e. constitucion)
        String minutos_4 = ""; // next train to origin (i.e. constitucion)
        private String line;

        public Arrival() {
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getMinutos_1() {
            return minutos_1;
        }

        public void setMinutos_1(String minutos_1) {
            this.minutos_1 = minutos_1;
        }

        public String getMinutos_2() {
            return minutos_2;
        }

        public void setMinutos_2(String minutos_2) {
            this.minutos_2 = minutos_2;
        }

        public String getMinutos_3() {
            return minutos_3;
        }

        public void setMinutos_3(String minutos_3) {
            this.minutos_3 = minutos_3;
        }

        public String getMinutos_4() {
            return minutos_4;
        }

        public void setMinutos_4(String minutos_4) {
            this.minutos_4 = minutos_4;
        }

        public void setLine(String line) {
            this.line = line;
        }

        public String getLine() {
            return line;
        }
    }
}
