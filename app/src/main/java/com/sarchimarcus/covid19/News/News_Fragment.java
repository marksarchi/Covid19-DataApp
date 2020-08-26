package com.sarchimarcus.covid19.News;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.sarchimarcus.covid19.R;
import com.sarchimarcus.covid19.model.NewsModel.Articles;
import com.sarchimarcus.covid19.model.NewsModel.Headlines;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link News_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class News_Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private static final String LOG_TAG = News_Fragment.class.getSimpleName();
   private RecyclerView recyclerView;
   private SwipeRefreshLayout swipeRefreshLayout;

    private Adapter newsAdapter;
    private List<Articles> articles = new ArrayList<>();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public News_Fragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static News_Fragment newInstance(String param1, String param2) {
        News_Fragment fragment = new News_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_news_,container,false);
        swipeRefreshLayout = rootview.findViewById(R.id.swipeRefresh);
        recyclerView = rootview.findViewById(R.id.recyclerView);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                retrieveNews();
            }
        });
        retrieveNews();
        return rootview;
    }
    private  void retrieveNews(){
        Call<Headlines> call;
        String coronaNews = "covid-19";
        String API_KEY = "0dcd1638c38243e485e3cc6ca4c1d230";

        call = ApiClient.getInstance().getApi().getSpecificData(coronaNews, API_KEY);
        call.enqueue(new Callback<Headlines>() {
            @Override
            public void onResponse(Call<Headlines> call, Response<Headlines> response) {
                if(response.isSuccessful()&&response.body().getArticles()!=null){
                    swipeRefreshLayout.setRefreshing(false);
                    articles.addAll(response.body().getArticles());
                    setupView(articles);
                }
            }

            @Override
            public void onFailure(Call<Headlines> call, Throwable t) {


            }
        });
    }
    private  void setupView(List<Articles> newArticles){

            newsAdapter = new Adapter(getContext(), newArticles);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(newsAdapter);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setNestedScrollingEnabled(true);
        }

    }

