package com.data_inserter;

import java.util.HashMap;
import java.util.HashSet;

import com.models.News;
import com.models.Rubric;

public interface IDataInserter {
    public void InsertData(HashMap<Rubric, HashSet<News>> data);
}
