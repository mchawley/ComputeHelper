package com.example.manishchawley.computehelper.provider;

import com.example.manishchawley.computehelper.model.Commuter;

import java.util.List;

/**
 * Created by manishchawley on 06/11/16.
 */

public class CommuterResult {
    private List<Commuter> commuterList;

    private CommuterResult(){
        //Blank private constructor
    }

    public static CommuterResult getCommuterResult(){
        CommuterResult cr = new CommuterResult();

        //TODO
        List<Commuter> c = null;

        cr.setCommuterList(c);
        return cr;
    }

    private void setCommuterList(List<Commuter> list) {
        this.commuterList = list;
    }

    public List<Commuter> getCommuterListResult(){
        //TODO
        return commuterList;
    }



}
