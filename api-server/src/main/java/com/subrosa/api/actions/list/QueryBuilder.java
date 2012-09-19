package com.subrosa.api.actions.list;

/**
 * Interface for a class that takes query criteria and produces a runnable query.
 *
 * @param <T> type of model object for which query is searching
 * @param <Q> type of query
 */
public interface QueryBuilder<T, Q> {

    /**
     * Generate a query for the given criteria.
     *
     * @param criteria query criteria
     * @return runnable query
     */
    Q getQuery(QueryCriteria<T> criteria);
}

