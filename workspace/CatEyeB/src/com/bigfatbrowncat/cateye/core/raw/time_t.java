package com.bigfatbrowncat.cateye.core.raw;

import com.sun.jna.Structure;

public class time_t extends Structure {
    public int value;

    public time_t() {}

    public time_t(int value) {
        this.value = value;
    }
}