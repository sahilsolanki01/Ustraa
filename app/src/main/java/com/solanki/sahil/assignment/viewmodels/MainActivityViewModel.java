package com.solanki.sahil.assignment.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.solanki.sahil.assignment.models.Model_Product;
import com.solanki.sahil.assignment.models.Model_Tab;
import com.solanki.sahil.assignment.repositories.Repository_Product;
import com.solanki.sahil.assignment.repositories.Repository_Tab;

import java.util.List;

public class MainActivityViewModel extends ViewModel {

    private MutableLiveData<List<Model_Tab>> data_tab;
    private Repository_Tab repository_tab;
    private MutableLiveData<List<Model_Product>> data_product;
    private Repository_Product repository_product;

    public void init_tab(Repository_Tab.RepositoryCallback callback){
        if(data_tab!=null){
            return;
        }

        repository_tab = Repository_Tab.getInstance();
        data_tab = repository_tab.repoData_tab(callback);
    }

    public LiveData<List<Model_Tab>> getData_tab(){
        return data_tab;
    }

    public void init_product(Repository_Product.RepositoryCallback callback){
        if(data_product!=null){
            return;
        }

        repository_product = Repository_Product.getInstance();
        data_product = repository_product.repoData_product(callback, 154);
    }

    public LiveData<List<Model_Product>> getData_product(){
        return data_product;
    }

}
