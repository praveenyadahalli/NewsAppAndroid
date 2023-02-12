package com.example.api_project;

import android.content.Context;
import android.widget.Toast;

import com.example.api_project.Models.NewsApiResponse;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.http.GET;
import retrofit.http.Query;

public class RequestManager {
    Context context;
    Retrofit retrofit=new Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    public void getNewsHeadlines(OnFetchDataListener listener,String category, String query){
        CallNewsApi callNewsApi=retrofit.create(CallNewsApi.class);
        Call<NewsApiResponse>call=callNewsApi.callHeadlines("us",category,query,context.getString(R.string.api_key));

        try {
            call.enqueue(new Callback<NewsApiResponse>() {
                @Override
                public void onResponse(Response<NewsApiResponse> response, Retrofit retrofit) {
                    if(!response.isSuccess()){
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                    }
                    listener.onFetchData(response.body().getArticles(),response.message());
                }

                @Override
                public void onFailure(Throwable t) {
                    listener.onError("Request Failed!");
                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
    public RequestManager(Context context) {
        this.context = context;
    }

    public interface CallNewsApi{
        @GET("top-headlines")
        Call<NewsApiResponse> callHeadlines(
                @Query("country") String country,
                @Query("category") String category,
                @Query("q") String query,
                @Query("apiKey") String api_key

        );
    }


}
