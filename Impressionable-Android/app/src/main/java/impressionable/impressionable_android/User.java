package impressionable.impressionable_android;

/**
 * Created by David on 2016-10-15.
 */


class User {

    private String name;
    private String major;
    private String minor;
    private int year;
    private double gpa;

    User(String name, String major, String minor, int year, double gpa){
        this.name = name;
        this.major = major;
        this.minor = minor;
        this.year = year;
        this.gpa = gpa;
    }

    public String getName(){
        return name;
    }

    public String getMajor(){
        return major;
    }

    public String getMinor(){
        return minor;
    }

    public int getYear(){
        return year;
    }

    public double getGpa(){
        return gpa;
    }
}
