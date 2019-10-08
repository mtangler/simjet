package com.app.mayur.apis;


import com.app.mayur.pojo.Species;

import java.util.List;

public interface ResultInterface {
    void onResults(List<Species.Result> resultsList, int lastCount);

    void onMessage(String message);
}