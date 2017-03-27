package by.epam.uber.timedistance;

public class TimeDistance implements Comparable<TimeDistance> {

    private int minutes;
    private int meters;

    public TimeDistance() {
    }


    public TimeDistance(int meters, int minutes) {
        this.minutes = minutes;
        this.meters = meters;
    }

    public TimeDistance(int meters, double speed) {
        this.meters = meters;
        this.minutes = (int) (meters / speed);
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getMeters() {
        return meters;
    }

    public void setMeters(int meters) {
        this.meters = meters;
    }

    @Override
    public int compareTo(TimeDistance o) {

        if (o == null) {
            return -1;
        } else {
            return Integer.valueOf(minutes).compareTo(
                    Integer.valueOf(o.getMinutes()));
        }
    }

    @Override
    public String toString() {
        return String.format("%d minutes, %d meters", minutes, meters);
    }

}
