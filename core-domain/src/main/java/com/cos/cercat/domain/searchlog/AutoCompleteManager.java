package com.cos.cercat.domain.searchlog;

import com.cos.cercat.domain.certificate.Certificate;
import java.util.List;

public interface AutoCompleteManager {

    void indexForAutoComplete(SearchLog searchLog);

    List<String> getAutoCompleteSuggestions(Certificate certificate, String query, int limit);
}
