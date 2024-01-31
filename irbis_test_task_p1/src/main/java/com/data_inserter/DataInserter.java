package com.data_inserter;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import com.models.News;
import com.models.Rubric;

public class DataInserter implements IDataInserter{

    public void InsertData(HashMap<Rubric, HashSet<News>> data) {
        for(Map.Entry<Rubric, HashSet<News>> entry : data.entrySet()) {
                var key = entry.getKey();
                var value = entry.getValue();

                int keyId;
                
                try {
                    key.Insert();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                try {
                    keyId = Rubric.SelectByRubric(key.getName(), key.getSource_id());
                } catch (SQLException e) {
                    e.printStackTrace();
                    continue;
                }

                for (News news : value) {
                    news.setRubric(keyId);
                    try {
                        news.Insert();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
    }
}
