package com.example.ibook.filters;

import android.widget.Filter;
import com.example.ibook.adapters.AdapterPdfAdmin;
import com.example.ibook.models.ModelPdf;
import java.util.ArrayList;

public class FilterPdfAdmin extends Filter {
    ArrayList<ModelPdf> filterList;
    AdapterPdfAdmin adapterPdfAdmin;

    public FilterPdfAdmin(ArrayList<ModelPdf> filterList, AdapterPdfAdmin adapterPdfAdmin) {
        this.filterList = filterList;
        this.adapterPdfAdmin = adapterPdfAdmin;
    }


    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();
        if (constraint != null && constraint.length() >0) {
            constraint = constraint.toString().toUpperCase();
            ArrayList<ModelPdf> filteredModels = new ArrayList<>();
            for (int i=0; i<filterList.size(); i++){
                if (filterList.get(i).getTitle().toUpperCase().contains(constraint)){
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
        adapterPdfAdmin.pdfArrayList = (ArrayList<ModelPdf>)results.values;

        //notify changes
        adapterPdfAdmin.notifyDataSetChanged();

    }
}
