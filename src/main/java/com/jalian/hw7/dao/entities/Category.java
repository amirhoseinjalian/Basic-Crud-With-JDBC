package com.jalian.hw7.dao.entities;

public class Category extends BaseEntity {
    private String description;
    private String title;

    public Category(int id, String description, String title) {
        super(id);
        this.description = description;
        this.title = title;
    }

    public Category(String description, String title) {
        super();
        this.description = description;
        this.title = title;
    }

    public Category(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "id: " + id + " title: " + title + ", description: " + description;
    }
}