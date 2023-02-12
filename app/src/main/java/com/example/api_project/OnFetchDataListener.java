package com.example.api_project;

import com.example.api_project.Models.NewsHeadlines;

import java.util.List;

public interface OnFetchDataListener<NewsApiResponse> {
    void onFetchData(List<NewsHeadlines>list,String message);
    void onError(String message);

}
