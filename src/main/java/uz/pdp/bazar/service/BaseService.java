package uz.pdp.bazar.service;


import uz.pdp.bazar.model.common.ApiResponse;

public interface BaseService <T ,R> {

    ApiResponse create(T t);

    ApiResponse getById(R r);

    ApiResponse update(T t);

    ApiResponse delete(R r);
}
