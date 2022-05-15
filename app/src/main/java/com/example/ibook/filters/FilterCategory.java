package com.example.ibook.filters;

import java.util.ArrayList;

import android.widget.Filter;

import com.example.ibook.adapters.AdapterCategory;
import com.example.ibook.models.ModelCategory;

public class FilterCategory extends Filter {
    ArrayList<ModelCategory> filterList;
    AdapterCategory adapterCategory;

    public FilterCategory(ArrayList<ModelCategory> filterList, AdapterCategory adapterCategory) {
        this.filterList = filterList;
        this.adapterCategory = adapterCategory;
    }


    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();
        if (constraint != null && constraint.length() >0) {
            constraint = constraint.toString().toUpperCase();
            ArrayList<ModelCategory> filteredModels = new ArrayList<>();
            for (int i=0; i<filterList.size(); i++){
                if (filterList.get(i).getCategory().toUpperCase().contains(constraint)){
                    filteredModels.add(filterList.get(i));
                }
            }
            results.count = filteredModels.size();
            results.values = filteredModels;
        }
        else {
            results.count = filterList.size();
            results.values = filterList;
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence charSequence, FilterResults results) {
        //apply filter changes
        adapterCategory.categoryArrayList = (ArrayList<ModelCategory>)results.values;

        //notify changes
        adapterCategory.notifyDataSetChanged();

    }
}
