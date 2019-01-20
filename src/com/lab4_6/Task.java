package com.lab4_6;

import java.util.TimerTask;

public class Task extends TimerTask {
    private Habitat habitat = null;
    private boolean first_rn = true;
    private long start_tm = 0;
    private long end_tm = 0;

    public Task(Habitat habitat){
        this.habitat = habitat;
    }

    public void run(){
        if (first_rn) {
            start_tm = System.currentTimeMillis();
            end_tm = start_tm;
            first_rn = false;
        }
        long current_tm = System.currentTimeMillis();
        double elapsed = (current_tm - start_tm) / 1000.0;
        double frame_tm = (end_tm - start_tm) / 1000.0;
        habitat.Update(elapsed, frame_tm);
        end_tm = current_tm;
    }
}