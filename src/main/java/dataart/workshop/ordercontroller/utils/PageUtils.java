package dataart.workshop.ordercontroller.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class PageUtils {

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 10;

    public Pageable adjustPageable(Integer requestedSize, Integer requestedPage) {
        int page = getIfPresent(requestedPage, DEFAULT_PAGE);
        int size = getIfPresent(requestedSize, DEFAULT_SIZE);

        return PageRequest.of(page, size);
    }

    private int getIfPresent(Integer requestedParam, int defaultValue) {
        if (requestedParam == null) {
            return defaultValue;
        }
        return requestedParam;
    }

}
