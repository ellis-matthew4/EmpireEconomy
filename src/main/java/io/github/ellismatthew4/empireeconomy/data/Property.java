package io.github.ellismatthew4.empireeconomy.data;

public abstract class Property implements Comparable<Property> {
    public String name;
    public String owner;
    public boolean repo;

    public Property(String name, String owner) {
        this.name = name;
        this.owner = owner;
        this.repo = false;
    }

    public Property(String name, String owner, boolean repo) {
        this.name = name;
        this.owner = owner;
        this.repo = repo;
    }

    public void repossess() {
        repo = true;
    }

    public void returnToOwner() {
        repo = false;
    }

    public void transfer(String newOwner) {
        owner = newOwner;
        repo = false;
    }

    @Override
    public int compareTo(Property p) {
        return name.compareToIgnoreCase(p.name);
    }
}
