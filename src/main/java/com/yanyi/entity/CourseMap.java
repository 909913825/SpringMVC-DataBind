package com.yanyi.entity;

import java.util.Map;

public class CourseMap {
    private Map<String,Course> courses;

    public void setCourses(Map<String, Course> courses) {
        this.courses = courses;
    }

    public Map<String, Course> getCourses() {
        return courses;
    }
}
