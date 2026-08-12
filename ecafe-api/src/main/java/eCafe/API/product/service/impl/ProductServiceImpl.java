package eCafe.API.product.service.impl;

import eCafe.API.category.entity.Category;
import eCafe.API.category.repository.CategoryRepository;
import eCafe.API.common.constants.ExceptionMessages;
import eCafe.API.common.constants.LogMessages;
import eCafe.API.common.exception.BusinessException;
import eCafe.API.common.exception.ResourceNotFoundException;
import eCafe.API.product.dto.ProductRequest;
import eCafe.API.product.dto.ProductResponse;
import eCafe.API.product.entity.Product;
import eCafe.API.product.repository.ProductRepository;
import eCafe.API.product.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import eCafe.API.monitoring.ProductMetrics;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMetrics productMetrics;

    @Override
    @Transactional
    public ProductResponse create(ProductRequest request) {

        log.info(LogMessages.PRODUCT_CREATE_START, request.name());

        validateProductName(request.name());

        Category category = findCategoryById(request.categoryId());

        Product product = Product.builder()
                .name(request.name())
                .description(request.description())
                .price(request.price())
                .stockQuantity(request.stockQuantity())
                .active(request.active())
                .category(category)
                .imageUrl(request.imageUrl())
                .build();

        Product savedProduct = productRepository.save(product);
        productMetrics.incrementCreated();

        log.info(LogMessages.PRODUCT_CREATE_SUCCESS, savedProduct.getId());

        return toDto(savedProduct);
    }

    @Override
    @Transactional
    public ProductResponse update(Long id, ProductRequest request) {

        log.info(LogMessages.PRODUCT_UPDATE_START, id);

        Product product = findProductById(id);

        validateProductNameForUpdate(request.name(), id);

        Category category = findCategoryById(request.categoryId());

        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setStockQuantity(request.stockQuantity());
        product.setActive(request.active());
        product.setCategory(category);
        product.setImageUrl(request.imageUrl());

        Product updatedProduct = productRepository.save(product);

        productMetrics.incrementUpdated();

        log.info(LogMessages.PRODUCT_UPDATE_SUCCESS, updatedProduct.getId());

        return toDto(updatedProduct);
    }

    @Override
    public List<ProductResponse> findAll() {

        log.info(LogMessages.PRODUCT_FIND_ALL);

        return productRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public ProductResponse findById(Long id){

        log.info("Iniciando busca de produto!");

        Product product = productRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Produto não encontrado '{}' Id: " + id));

        return toDto(product);

    }

    @Override
    public List<ProductResponse> productCategoryId(Long id){

        Category category = findCategoryById(id);

        if(category.getId() == null){
            throw new ResourceNotFoundException(LogMessages.CATEGORY_NOT_FOUND + id);
        }

        return  productRepository.findByCategoryId(category.getId()).stream().map(this::toDto).toList();

    }


    @Override
    public List<ProductResponse>findByNameContains(String name){

        List<ProductResponse> product = productRepository.findByNameContainingIgnoreCase(name).stream().map(this::toDto).toList();

        if(product.isEmpty()){
            throw new ResourceNotFoundException("não possui produtos com que contem essas letras: '{]' " + name);
        }

        return product;
    }


    @Override
    @Transactional
    public void deleteById(Long id) {

        log.info(LogMessages.PRODUCT_DELETE_START, id);

        Product product = findProductById(id);

        productRepository.delete(product);

        productMetrics.incrementDeleted();

        log.info(LogMessages.PRODUCT_DELETE_SUCCESS, product.getName());
    }

    private Product findProductById(Long id) {

        return productRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn(LogMessages.PRODUCT_NOT_FOUND, id);
                    return new ResourceNotFoundException(
                            ExceptionMessages.PRODUCT_NOT_FOUND + id);
                });
    }

    private Category findCategoryById(Long id) {

        return categoryRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn(LogMessages.CATEGORY_NOT_FOUND, id);
                    return new ResourceNotFoundException(
                            ExceptionMessages.CATEGORY_NOT_FOUND + id);
                });
    }

    private void validateProductName(String name) {

        if (productRepository.existsByNameIgnoreCase(name)) {

            log.warn(LogMessages.PRODUCT_ALREADY_EXISTS, name);

            throw new BusinessException(ExceptionMessages.PRODUCT_ALREADY_EXISTS);
        }
    }

    private void validateProductNameForUpdate(String name, Long id) {

        productRepository.findByNameIgnoreCase(name)
                .filter(product -> !product.getId().equals(id))
                .ifPresent(product -> {

                    log.warn(LogMessages.PRODUCT_ALREADY_EXISTS, name);

                    throw new BusinessException(ExceptionMessages.PRODUCT_ALREADY_EXISTS);
                });
    }

    private ProductResponse toDto(Product product) {

        return new ProductResponse(
                product.getId(),
                product.getCategory().getId(),
                product.getCategory().getName(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStockQuantity(),
                product.getImageUrl(),
                product.getActive()
        );
    }
}
