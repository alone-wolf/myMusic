package com.wh.mymusic.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class WebDavPathListViewModel extends ViewModel {
    private final MutableLiveData<List<String>> webDavPathList;
    private final MutableLiveData<String> currentFullPath;

    public WebDavPathListViewModel() {
        webDavPathList = new MutableLiveData<>();
        webDavPathList.setValue(new ArrayList<>());

        currentFullPath = new MutableLiveData<>();
    }

    public MutableLiveData<List<String>> getWebDavPathList() {
        return this.webDavPathList;
    }

    public void setWebDavPathList(List<String> webDavPathList) {
        this.webDavPathList.postValue(webDavPathList);
    }

    public MutableLiveData<String> getCurrentFullPath() {
        return this.currentFullPath;
    }

    public void setCurrentFullPath(String fullPath) {
        this.currentFullPath.postValue(fullPath);
    }
}
