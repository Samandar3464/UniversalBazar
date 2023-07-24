package uz.pdp.bazar.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import uz.pdp.bazar.entity.Market;
import uz.pdp.bazar.entity.Measurement;
import uz.pdp.bazar.entity.Product;
import uz.pdp.bazar.exception.RecordAlreadyExistException;
import uz.pdp.bazar.exception.RecordNotFoundException;
import uz.pdp.bazar.model.common.ApiResponse;
import uz.pdp.bazar.model.request.ProductDto;
import uz.pdp.bazar.model.response.ProductResponseList;
import uz.pdp.bazar.repository.MarketRepository;
import uz.pdp.bazar.repository.MeasurementRepository;
import uz.pdp.bazar.repository.ProductRepository;

import static uz.pdp.bazar.enums.Constants.*;


@Service
@RequiredArgsConstructor
public class ProductService implements BaseService<ProductDto, Integer> {

    private final ProductRepository productRepository;
    private final MeasurementRepository measurementRepository;
    private final MarketRepository marketRepository;

    @Override
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse create(ProductDto dto) {
        if (productRepository.existsByMarketIdAndMeasurementIdAndName(dto.getBranchId(), dto.getMeasurementId(), dto.getName())) {
            throw new RecordAlreadyExistException(PRODUCT_ALREADY_EXIST);
        }
        Market market = marketRepository.findById(dto.getBranchId()).orElseThrow(() -> new RecordNotFoundException(MARKET_NOT_FOUND));
        Measurement measurement = measurementRepository.findById(dto.getMeasurementId()).orElseThrow(() -> new RecordNotFoundException(MEASUREMENT_NOT_FOUND));
        Product product = Product.builder()
                .market(market)
                .measurement(measurement)
                .name(dto.getName())
                .quantity(dto.getQuantity())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .deleted(false)
                .active(false)
                .build();
        productRepository.save(product);
        return new ApiResponse(SUCCESSFULLY, true);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getById(Integer integer) {
        Product product = productRepository.findById(integer).orElseThrow(() -> new RecordNotFoundException(PRODUCT_NOT_FOUND));
        return new ApiResponse(product, true);
    }



    @Override
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse update(ProductDto dto) {
        Product product = productRepository.findById(dto.getId()).orElseThrow(() -> new RecordNotFoundException(PRODUCT_NOT_FOUND));
        Measurement measurement = measurementRepository.findById(dto.getMeasurementId()).orElseThrow(() -> new RecordNotFoundException(MEASUREMENT_NOT_FOUND));
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setMeasurement(measurement);
        product.setDescription(dto.getDescription());
        product.setQuantity(dto.getQuantity());
        productRepository.save(product);
        return new ApiResponse(SUCCESSFULLY, true);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse delete(Integer integer) {
        Product product = productRepository.findById(integer).orElseThrow(() -> new RecordNotFoundException(PRODUCT_NOT_FOUND));
        product.setDeleted(false);
        productRepository.save(product);
        return new ApiResponse(DELETED, true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getAllByMarketId(Integer page, Integer size, Integer integer) {
        Pageable page1 = PageRequest.of(page, size);
        Page<Product> product = productRepository.findAllByMarketIdAndActiveTrueAndDeletedFalse(integer, page1);
        ProductResponseList productResponseList = ProductResponseList.builder()
                .products(product.getContent())
                .totalElement(product.getTotalElements())
                .totalPage(product.getTotalPages())
                .size(product.getSize())
                .build();
        return new ApiResponse(productResponseList, true);
    }
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse activateProduct(Integer productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new RecordNotFoundException(PRODUCT_NOT_FOUND));
        product.setActive(true);
        productRepository.save(product);
        return new ApiResponse(SUCCESSFULLY, true);
    }
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getAllNeActivatedProducts(Integer page, Integer size) {
        Pageable page1 = PageRequest.of(page, size);
        Page<Product> products = productRepository.findAllByActiveFalseAndDeletedFalse(page1);
        return new ApiResponse(products, true);
    }
}
