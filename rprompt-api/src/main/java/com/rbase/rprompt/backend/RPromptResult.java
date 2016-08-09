package com.rbase.rprompt.backend;

import java.util.List;

public class RPromptResult {

    private final List<String> columns;
    private final List<List<String>> values;

    public RPromptResult(
            List<String> columns,
            List<List<String>> values) {
        this.columns = columns;
        this.values = values;
    }

    public List<String> getColumns() {
        return columns;
    }

    public List<List<String>> getValues() {
        return values;
    }

    @Override
    public String toString() {
        return "RPromptResult [columns=" + columns + ", values=" + values + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((columns == null) ? 0 : columns.hashCode());
        result = prime * result + ((values == null) ? 0 : values.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        RPromptResult other = (RPromptResult) obj;
        if (columns == null) {
            if (other.columns != null)
                return false;
        } else if (!columns.equals(other.columns))
            return false;
        if (values == null) {
            if (other.values != null)
                return false;
        } else if (!values.equals(other.values))
            return false;
        return true;
    }

}
