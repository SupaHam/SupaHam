package com.supaham.supaarrows.config;

public class Property<T> implements Comparable<Property> {

    private final String path;
    private final String name;
    private final T value;

    public Property(String path, T t) {
        if (path == null || t == null) {
            throw new IllegalArgumentException("path or t can not be null.");
        }
        this.path = path;
        this.value = t;
        int index = path.lastIndexOf('.');
        if (index < 0) {
            this.name = path;
        } else {
            this.name = path.substring(index + 1);
        }
    }

    /**
     * Gets the path of this property.
     *
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * Gets the name of this property.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the default value of this property.
     *
     * @return the default value
     */
    public T getDefaultValue() {
        return value;
    }

    /**
     * Compares this property with another.
     *
     * @param o property to compare with
     * @return a negative integer, zero, or a positive integer as the specified String is greater than, equal to, or less than this String, ignoring
     * case considerations.
     */
    @Override
    public int compareTo(Property o) {
        return getName().compareToIgnoreCase(o.getName());
    }

    @Override
    public String toString() {
        return "Property{name=" + getName() +"}";
    }
}
