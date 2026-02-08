package com.example.carrental.model;

public class EconomyCar extends Vehicle {
    // Extra field example
    private boolean compact = true;

    public boolean isCompact() { return compact; }
    public void setCompact(boolean compact) { this.compact = compact; }
}
