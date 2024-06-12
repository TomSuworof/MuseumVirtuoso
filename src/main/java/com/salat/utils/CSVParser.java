package com.salat.utils;

import java.util.List;

public interface CSVParser<T extends Entity> {
    List<T> getEntitiesFromCSVFile(String fileName);
}
