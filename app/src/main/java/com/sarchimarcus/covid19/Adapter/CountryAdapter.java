package com.sarchimarcus.covid19.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sarchimarcus.covid19.R;
import com.sarchimarcus.covid19.model.Country;

import java.text.NumberFormat;
import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.CountryViewHolder>{
    private List<Country> countries;
   private Context mContext;

    public CountryAdapter(Context context,List<Country> countryList){
        this.countries = countryList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item,parent,false);
        return  new CountryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryViewHolder holder, int position) {
        Country country = countries.get(position);
        String countryName = country.getCountry();
        Integer cases  = country.getCases();
        Integer deaths = country.getDeaths();
        Integer recoveries = country.getRecovered();

        holder.countryNameTv.setText(countryName);
            holder.casesTv.setText(addComma(cases));
            holder.deathsTv.setText(addComma(deaths));
            holder.recoveredTv.setText(addComma(recoveries));


    }

    @Override
    public int getItemCount() {
        return countries.size();
    }

    public class CountryViewHolder extends RecyclerView.ViewHolder{
        final TextView countryNameTv;
        final TextView casesTv;
        final TextView deathsTv;
        final TextView recoveredTv;
        //final TextView activeCasesTv;


        public CountryViewHolder(@NonNull View itemView) {
            super(itemView);
            countryNameTv = itemView.findViewById(R.id.textView_country_folded);
            casesTv = itemView.findViewById(R.id.textView_country_cases_number_folded);
            deathsTv= itemView.findViewById(R.id.textView_country_deaths_number_folded);
            recoveredTv=itemView.findViewById(R.id.textView_country_recovered_number_folded);
        }
    }
    public String addComma(Integer number){
        NumberFormat myFormat = NumberFormat.getInstance();
        myFormat.setGroupingUsed(true);
        return myFormat.format(number);
    }
}
