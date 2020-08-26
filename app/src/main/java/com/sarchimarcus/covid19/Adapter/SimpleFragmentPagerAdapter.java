/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sarchimarcus.covid19.Adapter;



import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.sarchimarcus.covid19.Country.CountriesFragment;
import com.sarchimarcus.covid19.Global.GlobalFragment;
import com.sarchimarcus.covid19.News.News_Fragment;
import com.sarchimarcus.covid19.R;

/**
 * Provides the appropriate {@link Fragment} for a view pager.
 */
public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;


    public SimpleFragmentPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new GlobalFragment();
        }  else  if(position == 1) {
            return new CountriesFragment();
        }
        else{
            return  new News_Fragment();
        }
    }

    @Override
    public int getCount()
    {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return mContext.getString(R.string.category_global);
        } else if(position ==1)  {
            return mContext.getString(R.string.category_country);
        }
        else{
            return mContext.getString(R.string.News);
        }
    }
}
