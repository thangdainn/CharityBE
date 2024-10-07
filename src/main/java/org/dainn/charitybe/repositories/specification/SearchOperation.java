package org.dainn.charitybe.repositories.specification;

public enum SearchOperation {
    EQUALITY,
    NEGATION,
    GREATER_THAN_OR_EQUAL,
    LESS_THAN_OR_EQUAL,
    LIKE, STARTS_WITH,
    ENDS_WITH,
    CONTAINS,
    IN
}
