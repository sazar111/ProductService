package com.example.SpringAPI.specifications;

import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class SpecificationBuilder<T> {

    private final List<Specification<T>> specifications = new ArrayList<>();

    public SpecificationBuilder<T> with(Specification<T> specification) {
        if (specification != null) {
            specifications.add(specification);
        }
        return this;
    }

    public Specification<T> build() {
        Specification<T> result = null;
        for (Specification<T> specification : specifications) {
            result = (result == null) ? specification : result.and(specification);
        }
        return result;
    }
}
