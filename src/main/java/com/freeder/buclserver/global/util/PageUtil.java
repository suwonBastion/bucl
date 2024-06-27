package com.freeder.buclserver.global.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageUtil {

    public static Pageable paging(
            int page,
            int pageSize
    ) {
        return PageRequest.of(page - 1, pageSize, Sort.by("createdAt").descending());
    }
}
