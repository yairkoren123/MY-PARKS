package com.example.myparks.modle;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

public class ParkViewModel extends ViewModel {

    private final MutableLiveData<Park> selectedPark = new MutableLiveData<>();
    private final MutableLiveData<List<Park>> selectedParks = new MutableLiveData<>();
    private final MutableLiveData<String> code = new MutableLiveData<>();

    public void setSelectedPark(Park park){
        selectedPark.setValue(park);
    }
    public LiveData<Park> getSelectedPark(){
        return selectedPark;
    }
    public void setSelectedParks(List<Park> parks){
        selectedParks.setValue(parks);
    }
    public LiveData<List<Park>> getParks(){
        return selectedParks;
    }

    public LiveData<String> getCode (){return code;}

    public void selectCode(String c) {
        this.code.setValue(c);
    }
}
