package com.bryanpoh.c347_p06_ps_taskmananger;

import java.io.Serializable;

public class Task implements Serializable {

    private String name;
    private String description;

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
