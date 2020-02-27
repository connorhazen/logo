package slogo.structs;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public abstract class Struct implements Comparable<Struct> {
    private StringProperty myName;

    public Struct(String name){
        nameProperty().set(name);
    }

    public String getName(){
        return nameProperty().get();
    }

    public StringProperty nameProperty() {
        if (myName == null) {
            myName = new SimpleStringProperty(this, "");
        }
        return myName;
    }

    @Override
    public int compareTo(Struct o) {
        if (this == o) {
            return 0;
        }

        return stringCompareTo(this.getName(), o.getName());
    }

    private int stringCompareTo(String a, String b){
        return a.compareTo(b);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Struct)) {
            return false;
        }
        if (this == o) {
            return true;
        }

        return stringEquals(this.getName(), ((Struct) o).getName());
    }

    private boolean stringEquals(String a, String b){
        return a.equals(b);
    }

}
