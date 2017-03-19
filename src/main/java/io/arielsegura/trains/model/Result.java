package io.arielsegura.trains.model;

/**
 * Created by arielsegura on 3/18/17.
 */
public class Result {
    private String firstArrivalToEndOfTrack = "";
    private String secondArrivalToEndOfTrack = "";
    private String firstArrivalToOrigin = "";
    private String secondArrivalToOrigin = "";
    private String line;

    public String getFirstArrivalToEndOfTrack() {
        return firstArrivalToEndOfTrack;
    }

    public void setFirstArrivalToEndOfTrack(String firstArrivalToEndOfTrack) {
        this.firstArrivalToEndOfTrack = firstArrivalToEndOfTrack;
    }

    public String getSecondArrivalToEndOfTrack() {
        return secondArrivalToEndOfTrack;
    }

    public void setSecondArrivalToEndOfTrack(String secondArrivalToEndOfTrack) {
        this.secondArrivalToEndOfTrack = secondArrivalToEndOfTrack;
    }

    public String getFirstArrivalToOrigin() {
        return firstArrivalToOrigin;
    }

    public void setFirstArrivalToOrigin(String firstArrivalToOrigin) {
        this.firstArrivalToOrigin = firstArrivalToOrigin;
    }

    public String getSecondArrivalToOrigin() {
        return secondArrivalToOrigin;
    }

    public void setSecondArrivalToOrigin(String secondArrivalToOrigin) {
        this.secondArrivalToOrigin = secondArrivalToOrigin;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getLine() {
        return line;
    }
}
