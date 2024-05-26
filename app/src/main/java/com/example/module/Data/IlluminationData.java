package com.example.module.Data;

public class IlluminationData {

    private int id;
    private long level;
    private String created_at;
    private int illumination_class;


    public IlluminationData(int level, int illumination_class) {
        this.level = level;
        this.illumination_class = illumination_class;
    }

    public long getLevel() {
        return level;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLevel(long level) {
        this.level = level;
    }

    public String getCreatedAt() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getIlluminationClass() {
        return illumination_class;
    }

    public void setIllumination_class(int illumination_class) {
        this.illumination_class = illumination_class;
    }
}
