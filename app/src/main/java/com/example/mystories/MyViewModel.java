package com.example.mystories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyViewModel extends ViewModel {
    private MutableLiveData<Story> story = new MutableLiveData<>();

    public void setData(Story data) {
        story.setValue(data);
    }

    public LiveData<Story> getData() {
        return story;
    }
}
