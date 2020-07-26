package com.yumtaufik.gitser.model.search;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchResponse{
	@SerializedName("total_count")
	private int totalCount;
	@SerializedName("incomplete_results")
	private boolean incompleteResults;
	@SerializedName("items")
	private List<SearchItems> items;

	public void setTotalCount(int totalCount){
		this.totalCount = totalCount;
	}

	public int getTotalCount(){
		return totalCount;
	}

	public void setIncompleteResults(boolean incompleteResults){
		this.incompleteResults = incompleteResults;
	}

	public boolean isIncompleteResults(){
		return incompleteResults;
	}

	public void setItems(List<SearchItems> items){
		this.items = items;
	}

	public List<SearchItems> getItems(){
		return items;
	}
}