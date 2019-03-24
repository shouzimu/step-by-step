package com.dh.es;

import java.util.LinkedList;

public interface ElasticService {

    LinkedList<String> queryDrug(String keyWord);
}
