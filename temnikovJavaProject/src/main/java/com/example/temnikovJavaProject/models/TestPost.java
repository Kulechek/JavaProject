package com.example.temnikovJavaProject.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.sql.Timestamp;

@Entity
public class TestPost {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Timestamp timestamp;
    private double trackCpu, trackRam, trackDisk, trackNet;

    public TestPost(Timestamp timestamp, double trackCpu, double trackRam, double trackDisk, double trackNet) {
        this.timestamp = timestamp;
        this.trackCpu = trackCpu;
        this.trackRam = trackRam;
        this.trackDisk = trackDisk;
        this.trackNet = trackNet;
    }

    public TestPost() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public double getTrackCpu() {
        return trackCpu;
    }

    public void setTrackCpu(double trackCpu) {
        this.trackCpu = trackCpu;
    }

    public double getTrackRam() {
        return trackRam;
    }

    public void setTrackRam(double trackRam) {
        this.trackRam = trackRam;
    }

    public double getTrackDisk() {
        return trackDisk;
    }

    public void setTrackDisk(double trackDisk) {
        this.trackDisk = trackDisk;
    }

    public double getTrackNet() {
        return trackNet;
    }

    public void setTrackNet(double trackNet) {
        this.trackNet = trackNet;
    }

}
