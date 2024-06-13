package com.dms.variant.mappers;

public interface Mapper<A, B> {

    B mapTo(A entity);

    A mapFrom(B dto);

    void updatePartial(A entity, B dto);

}
