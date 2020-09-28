package sample;

public class Town {
    public String name;   //just public for test purposes
    public int touristRating;

    @Override
    public String toString() {
        return name;
    }

    public Town(String name, int touristRating) {
        this.name = name;
        this.touristRating = touristRating;
    }
}
