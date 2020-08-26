package com.sarchimarcus.covid19.Global;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sarchimarcus.covid19.InjectorUtils;
import com.sarchimarcus.covid19.Network.CoronaDataRepository;
import com.sarchimarcus.covid19.R;
import com.sarchimarcus.covid19.model.Global;

import java.text.NumberFormat;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GlobalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GlobalFragment extends Fragment {
    private static final String LOG_TAG = GlobalFragment.class.getSimpleName();
    private  Global global ;
    private TextView caseTv;
    private TextView deathsTv;
    private TextView recoveriesTv;
    private  TextView criticalTv;
    private  TextView casePerMillionTv;
    private TextView deathPerMillionTv;
    private  TextView dateTv;
    private ProgressBar progressBar;
    private CoronaDataRepository coronaDataRepository;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GlobalFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static GlobalFragment newInstance(String param1, String param2) {
        GlobalFragment fragment = new GlobalFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_global, container, false);

        caseTv = (TextView) rootView.findViewById(R.id.tV_cases);
        deathsTv = (TextView) rootView.findViewById(R.id.tV_deaths);
        recoveriesTv= (TextView) rootView.findViewById(R.id.tV_recovered);
        criticalTv = (TextView) rootView.findViewById(R.id.tV_critical) ;
        casePerMillionTv = (TextView) rootView.findViewById(R.id.tv_casesPerMillion) ;
        deathPerMillionTv = (TextView) rootView.findViewById(R.id.tv_deathsPerMillion) ;

       // dateTv = (TextView) rootView.findViewById(R.id.textView_date);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar_global);

        global = new Global();
        coronaDataRepository = InjectorUtils.provideRepository(this.getContext());
       getData();


      return rootView;
    }



    public void setupView(Global global){
        progressBar.setVisibility(View.GONE);
        if(global!=null){
            Integer cases = global.getCases();
            Integer deaths  = global.getDeaths();
            Integer recoveries = global.getRecovered();
            Integer critical = global.getCritical();
            Double casePerMillion = global.getCasesPerOneMillion();
            Double deathPerMillion = global.getDeathsPerOneMillion();

            caseTv.setText(addComma(cases));
            deathsTv.setText(addComma(deaths));
            recoveriesTv.setText(addComma(recoveries));
            criticalTv.setText(addComma(critical));
            casePerMillionTv.setText(casePerMillion.toString());
            deathPerMillionTv.setText(deathPerMillion.toString());
        }


    }
    public void getData(){
        GlobalViewModelFactory factory = new GlobalViewModelFactory(coronaDataRepository);
        GlobalViewModel viewModel = new ViewModelProvider(this,factory).get(GlobalViewModel.class);
        viewModel.getGlobalData().observe(getViewLifecycleOwner(), new Observer<Global>() {
            @Override
            public void onChanged(Global global) {
                setupView(global);
            }
        });
    }
    public String addComma(Integer number){
        NumberFormat myFormat = NumberFormat.getInstance();
        myFormat.setGroupingUsed(true);
        return myFormat.format(number);
    }
}
