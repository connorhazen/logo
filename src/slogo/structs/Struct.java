package slogo.structs;

public abstract class Struct implements Comparable<Struct> {
    private String myName;

    public Struct(String name){
        myName = name;
    }

    public String getName(){
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
