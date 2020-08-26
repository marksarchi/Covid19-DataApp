package com.sarchimarcus.covid19.Country;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.sarchimarcus.covid19.Adapter.CountryAdapter;
import com.sarchimarcus.covid19.InjectorUtils;
import com.sarchimarcus.covid19.Network.CoronaDataRepository;
import com.sarchimarcus.covid19.Network.ApiClient;
import com.sarchimarcus.covid19.R;
import com.sarchimarcus.covid19.model.Country;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CountriesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CountriesFragment extends Fragment {
    private static final String LOG_TAG = CountriesFragment.class.getSimpleName();
    private RecyclerView recyclerView;
    private CountryAdapter countryAdapter;
    private List<Country> countryList;
    private ProgressBar progressBar;
    private CoronaDataRepository repository;
    private EditText editText;






    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CountriesFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static CountriesFragment newInstance(String param1, String param2) {
        CountriesFragment fragment = new CountriesFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_countries, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView_countries);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar_country);
        editText = (EditText) rootView.findViewById(R.id.search);
        countryList = new ArrayList<>();
        countryAdapter= new CountryAdapter(this.getContext(),countryList);

        repository = InjectorUtils.provideRepository(this.getContext());

        getData();
        Log.e(LOG_TAG,"Countrilist"+ String.valueOf(countryList.size()));

       editText.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {

           }

           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {

           }

           @Override
           public void afterTextChanged(Editable s) {
               Log.e(LOG_TAG,"On textChange");
               Log.e(LOG_TAG,s.toString());


               List<Country> secondList = new ArrayList<>();

               for( Country country: countryList) {

                   if (country.getCountry().contains(s.toString())){

                           secondList.add(country);
                       countryAdapter.notifyDataSetChanged();
                       countryAdapter = new CountryAdapter(getContext(),secondList);

                     //  setupView(secondList);

                       Log.e(LOG_TAG,country.getCountry());
                   }
               }

           }
       });


        return rootView;
    }
    public String capitalize(String str)
    {
       StringUtils.capitalize(str);
        return str;
    }


    public void setupView(List<Country> countryList){
        Log.e(LOG_TAG, "Setup view called ");
            countryAdapter.notifyDataSetChanged();
            countryAdapter = new CountryAdapter(getContext(),countryList);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(countryAdapter);
            progressBar.setVisibility(View.GONE);

        }
        private void getData(){
        CountryViewModelFactory factory = new CountryViewModelFactory(repository);
        CountryViewModel viewModel = new ViewModelProvider(this,factory).get(CountryViewModel.class);
        viewModel.getCountryData().observe(getViewLifecycleOwner(), new Observer<List<Country>>() {
            @Override
            public void onChanged(List<Country> thisCountryList) {
                Log.e(LOG_TAG,"Countrilist"+ String.valueOf(countryList.size()));
                countryList.addAll(thisCountryList);
                setupView(countryList);
            }
        });
        }

    }

