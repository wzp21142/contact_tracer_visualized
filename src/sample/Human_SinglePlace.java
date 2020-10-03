package sample;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Human_SinglePlace{
    final StringProperty arrivetime;
    final StringProperty leavetime;
    final StringProperty name;
    final StringProperty workspace;
    final StringProperty home;
    final StringProperty phonenumber;
    final StringProperty isMaskOn;
    final StringProperty IllLevel;

    public Human_SinglePlace(){
        this(null,null,null,null,null,null,null,null);
    }

    public Human_SinglePlace(String name, String workspace, String home, String phonenumber,String isMaskOn,String IllLevel, String arrivetime, String leavetime) {
        this.name=new SimpleStringProperty(name);
        this.workspace=new SimpleStringProperty(workspace);
        this.home=new SimpleStringProperty(home);
        this.phonenumber=new SimpleStringProperty(phonenumber);
        this.isMaskOn=new SimpleStringProperty(isMaskOn);
        this.IllLevel=new SimpleStringProperty(IllLevel);
        this.arrivetime=new SimpleStringProperty(arrivetime);
        this.leavetime=new SimpleStringProperty(leavetime);;
    }

    public String getArrivetime() {
        return arrivetime.get();
    }

    public StringProperty arrivetimeProperty() {
        return arrivetime;
    }

    public void setArrivetime(String arrivetime) {
        this.arrivetime.set(arrivetime);
    }

    public String getLeavetime() {
        return leavetime.get();
    }

    public StringProperty leavetimeProperty() {
        return leavetime;
    }

    public void setLeavetime(String leavetime) {
        this.leavetime.set(leavetime);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getWorkspace() {
        return workspace.get();
    }

    public StringProperty workspaceProperty() {
        return workspace;
    }

    public void setWorkspace(String workspace) {
        this.workspace.set(workspace);
    }

    public String getHome() {
        return home.get();
    }

    public StringProperty homeProperty() {
        return home;
    }

    public void setHome(String home) {
        this.home.set(home);
    }

    public String getPhonenumber() {
        return phonenumber.get();
    }

    public StringProperty phonenumberProperty() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber.set(phonenumber);
    }

    public String getIsMaskOn() {
        return isMaskOn.get();
    }

    public StringProperty isMaskOnProperty() {
        return isMaskOn;
    }

    public void setIsMaskOn(String isMaskOn) {
        this.isMaskOn.set(isMaskOn);
    }

    public String getIllLevel() {
        return IllLevel.get();
    }

    public StringProperty illLevelProperty() {
        return IllLevel;
    }

    public void setIllLevel(String illLevel) {
        this.IllLevel.set(illLevel);
    }

    public String toString(){
        return name+" "+workspace+" "+home+" "+phonenumber+" "+isMaskOn+" "+IllLevel+" "+arrivetime+" "+leavetime;
    }
}
