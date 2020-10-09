package sample;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Human_SinglePlace {
    final StringProperty arrivetime;
    final StringProperty leavetime;
    final StringProperty name;
    final StringProperty workspace;
    final StringProperty home;
    final StringProperty phonenumber;
    final StringProperty isMaskOn;
    final StringProperty IllLevel;

    public Human_SinglePlace() {
        this(null, null, null, null, null, null, null, null);
    }

    public Human_SinglePlace(String name, String workspace, String home, String phonenumber, String isMaskOn, String IllLevel, String arrivetime, String leavetime) {
        this.name = new SimpleStringProperty(name);
        this.workspace = new SimpleStringProperty(workspace);
        this.home = new SimpleStringProperty(home);
        this.phonenumber = new SimpleStringProperty(phonenumber);
        this.isMaskOn = new SimpleStringProperty(isMaskOn);
        this.IllLevel = new SimpleStringProperty(IllLevel);
        this.arrivetime = new SimpleStringProperty(arrivetime);
        this.leavetime = new SimpleStringProperty(leavetime);
    }

    public String toString() {
        return name + " " + workspace + " " + home + " " + phonenumber + " " + isMaskOn + " " + IllLevel + " " + arrivetime + " " + leavetime;
    }
}
