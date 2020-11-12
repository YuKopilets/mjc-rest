package com.epam.esm.dao;

import lombok.RequiredArgsConstructor;

/**
 * The Page request holds number and size of page for realizing pagination.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 */
@RequiredArgsConstructor
public class PageRequest {
    private final int page;
    private final int size;

    /**
     * Calculate position of start element. Based on number and size of page.
     *
     * @return the position
     */
    public int calculateStartElementPosition() {
        return size * (page - 1);
    }

    public int getPageSize() {
        return this.size;
    }
}
