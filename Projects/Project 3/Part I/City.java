public class City {
    private int id;
    private double x;
    private double y;

    public City(int id, double x, double y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double calculateDistance(City otherCity) {
        return Math.sqrt(Math.pow(this.x - otherCity.x, 2) + Math.pow(this.y - otherCity.y, 2));
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
}