package com.tiva11.mtfoodrecipes;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tiva11.food2fork.Recipe;
import com.tiva11.mtfoodrecipes.databinding.RecipeCardviewBinding;

import java.util.ArrayList;
import java.util.List;

public class RecipeListRVAdapter extends RecyclerView.Adapter {
    interface IOnClickListener { //SAM, Single Abstract Method interface for lambdas for Java 8
        void onClick(@NonNull View v,@NonNull Recipe recipe);
    }
    @NonNull private List<Recipe> recipeList = new ArrayList<>();
    @NonNull private final MutableLiveData<Throwable> mldError;
//    private final View.OnClickListener onClickListener;
    private final IOnClickListener onClickListener;
    public RecipeListRVAdapter(@NonNull MutableLiveData<Throwable> mldError, final IOnClickListener onClickListener) {
    //public RecipeListRVAdapter(@NonNull MutableLiveData<Throwable> mldError, final View.OnClickListener onClickListener) {
        this.mldError = mldError;
        this.onClickListener = onClickListener;
    }
    public class RecipeListVH extends RecyclerView.ViewHolder {
//    class RecipeListVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        final RecipeCardviewBinding binding;
        Recipe recipe;
        RecipeListVH(@NonNull RecipeCardviewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
//            if(onClickListener != null) binding.getRoot().setOnClickListener(onClickListener);
        }
        void bindTo(Recipe recipe) { //Remember to set the variables defined in the layout data section
            this.recipe = recipe;
            //setRecipe and setVH were generated by AS for the data binding from the layout XML variable Recipe
            binding.setRecipe(recipe);
            binding.setVH(this);
            binding.executePendingBindings();//IMPORTANT for RV
        }
//        @Override
//        public void onClick(View v) {
//            //Remember the VH object cen be retrieved from the root view with getTag
//            if(onClickListener != null) onClickListener.onClick(v);
//        }
        public void onClick(View v, Recipe recipe){
            if(onClickListener != null) onClickListener.onClick(v,recipe);
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RecipeCardviewBinding binding = RecipeCardviewBinding.inflate(LayoutInflater.from(viewGroup.getContext()),viewGroup,false);
        RecipeListVH vh = new RecipeListVH(binding);
        // Save the binding in the root view, this could be terribly useful for event handlers
        // The OnClickListener handler, for example, gets the VH object from the view
        binding.getRoot().setTag(vh);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof RecipeListVH) {
            try { ((RecipeListVH) viewHolder).bindTo(recipeList.get(i));
            } catch(Exception e) { mldError.setValue(e); }
        }
    }
    @Override
    public int getItemCount() {
        return recipeList.size();
    }
    public void submitList(@NonNull List<Recipe> recipeList) {
        this.recipeList = recipeList;
        this.notifyDataSetChanged();
    }
}
