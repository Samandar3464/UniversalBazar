package uz.pdp.bazar.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.bazar.entity.Market;
import uz.pdp.bazar.entity.Product;
import uz.pdp.bazar.entity.SoldProduct;
import uz.pdp.bazar.entity.User;
import uz.pdp.bazar.exception.RecordNotFoundException;
import uz.pdp.bazar.exception.UserNotFoundException;
import uz.pdp.bazar.model.common.ApiResponse;
import uz.pdp.bazar.model.request.SoldProductDto;
import uz.pdp.bazar.repository.MarketRepository;
import uz.pdp.bazar.repository.ProductRepository;
import uz.pdp.bazar.repository.SoldProductRepository;
import uz.pdp.bazar.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.UUID;

import static uz.pdp.bazar.enums.Constants.*;

@Service
@RequiredArgsConstructor
public class SoldProductService implements BaseService<SoldProductDto, UUID> {

    private final SoldProductRepository soldProductRepository;
    private final ProductRepository productRepository;
    private final MarketRepository marketRepository;
    private final UserRepository userRepository;


    @Override
    public ApiResponse create(SoldProductDto dto) {
        Product product = productRepository.findById(dto.getProductId()).orElseThrow(() -> new RecordNotFoundException(PRODUCT_NOT_FOUND));
        Market market = marketRepository.findById(dto.getMarketId()).orElseThrow(() -> new RecordNotFoundException(MARKET_NOT_FOUND));
        User user = userRepository.findById(dto.getSoldUserId()).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
        if (product.getQuantity() < dto.getQuantity()) {
            throw new RecordNotFoundException(NOT_ENOUGHT_QUENTITY);
        }
        product.setQuantity(product.getQuantity() - dto.getQuantity());
        SoldProduct soldProduct = SoldProduct.builder()
                .quantity(dto.getQuantity())
                .price(dto.getPrice())
                .soldDate(LocalDateTime.now())
                .product(product)
                .market(market)
                .soldUser(user)
                .build();
        productRepository.save(product);
        soldProductRepository.save(soldProduct);
        return new ApiResponse(SUCCESSFULLY, true);
    }

    @Override
    public ApiResponse getById(UUID uuid) {
        SoldProduct soldProduct = soldProductRepository.findById(uuid).orElseThrow(() -> new RecordNotFoundException(PRODUCT_NOT_FOUND));
        return new ApiResponse(soldProduct, true);
    }

    @Override
    public ApiResponse update(SoldProductDto dto) {
        SoldProduct soldProduct = soldProductRepository.findById(dto.getId()).orElseThrow(() -> new RecordNotFoundException(PRODUCT_NOT_FOUND));
        Market market = marketRepository.findById(dto.getMarketId()).orElseThrow(() -> new RecordNotFoundException(MARKET_NOT_FOUND));
        User user = userRepository.findById(dto.getSoldUserId()).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
        Product product = productRepository.findById(dto.getProductId()).orElseThrow(() -> new RecordNotFoundException(PRODUCT_NOT_FOUND));
        if (soldProduct.getProduct().getId().equals(dto.getProductId())) {

            if (soldProduct.getQuantity() < dto.getQuantity()) {
                double v = dto.getQuantity() - soldProduct.getQuantity();
                if (v > product.getQuantity()) {
                    throw new RecordNotFoundException(NOT_ENOUGHT_QUENTITY);
                } else {
                    product.setQuantity(product.getQuantity() - v);
                }
            }else if (soldProduct.getQuantity() > dto.getQuantity()){
                double v = soldProduct.getQuantity() - dto.getQuantity();
                product.setQuantity(product.getQuantity()+ v);
            }

        }else {
            if (product.getQuantity() < dto.getQuantity()) {
                throw new RecordNotFoundException(NOT_ENOUGHT_QUENTITY);
            }
            product.setQuantity(product.getQuantity() - dto.getQuantity());

        }
        soldProduct.setProduct(product);
        soldProduct.setMarket(market);
        soldProduct.setSoldUser(user);
        productRepository.save(product);
        soldProductRepository.save(soldProduct);
        return new ApiResponse(SUCCESSFULLY, true);
    }

    @Override
    public ApiResponse delete(UUID uuid) {
        return null;
    }
}
