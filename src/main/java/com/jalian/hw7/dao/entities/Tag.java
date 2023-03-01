package com.jalian.hw7.dao.entities;

public class Tag extends BaseEntity {
    private String title;

    public Tag(int id) {
        super(id);
    }

    public Tag(int id, String title) {
        super(id);
        this.title = title;
    }

    public Tag(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "id: " + id + " title: " + title;
    }

    @Override
    public boolean equals(Object o) {
        Tag tag = (Tag) o;
        if (this.id == tag.getId()) {
            return true;
        }
        return false;
    }
}