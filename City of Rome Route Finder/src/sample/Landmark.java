package sample;

public class Landmark {
    private String landmarkName;
    public int historicalSignificance;
    public int starRating;
    public int X;
    public int Y;
    public Landmark(String landmarkName, int starRating){
        this.landmarkName=landmarkName;
        //this.historicalSignificance=historicalSignificance;
        this.starRating=starRating;
    }
    public void setCoordinates(int x, int y){
        X= x;
        Y= y;
    }
    public String getLandmarkName() {
        return landmarkName;
    }
    public void setLandmarkName(String landmarkName) {
        this.landmarkName = landmarkName;
    }
    public int getHistoricalSignificance() {
        return historicalSignificance;
    }
    public void setHistoricalSignificance(int historicalSignificance) {
        this.historicalSignificance = historicalSignificance;
    }
    public int getStarRating() {
        return starRating;
    }
    public void setStarRating(int starRating) {
        this.starRating = starRating;
    }
    @Override
    public String toString() {
        return landmarkName;
    }

}