package com.maradamark99.thesis.product;

import com.maradamark99.thesis.category.CategoryRepository;
import com.maradamark99.thesis.exception.ResourceNotFoundException;
import com.maradamark99.thesis.media.Media;
import com.maradamark99.thesis.media.MediaInfo;
import com.maradamark99.thesis.media.MediaType;
import com.maradamark99.thesis.storage.Buckets;
import com.maradamark99.thesis.storage.StorageClient;
import com.maradamark99.thesis.util.CaseInsensitivePageable;

import jakarta.activation.UnsupportedDataTypeException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private static final Function<Long, String> PRODUCT_NOT_FOUND_MESSAGE = (id) -> "Product with id: %d not found"
            .formatted(id);

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    private final CategoryRepository categoryRepository;

    private final StorageClient storageClient;

    private final Buckets buckets;

    public Page<ProductDTO> getAll(Pageable pageable, String category) {
        var products = category == null ? productRepository.findAll(pageable)
                : productRepository.findAllByCategoryName(pageable, category);
        return products.map(productMapper::entityToDto);
    }

    @Override
    public ProductView getById(long id) {
        return productMapper.entityToView(productRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(PRODUCT_NOT_FOUND_MESSAGE.apply(id))));
    }

    @Override
    @Transactional
    public Long create(ProductDTO productDTO) {
        var categories = categoryRepository.findByIdIn(productDTO.getCategories());
        return productRepository
                .save(productMapper.dtoToEntity(productDTO, categories))
                .getId();
    }

    @Override
    public void uploadThumbnailImage(final long id, final MultipartFile file) throws IOException {
        doUploadProductMedia(
                id,
                file,
                (i) -> i.getMediaType().equals(MediaType.IMAGE),
                (p, m) -> p.setThumbnailImage(m));
    }

    @Override
    public void uploadProductMedia(final long id, final MultipartFile file) throws IOException {
        doUploadProductMedia(
                id,
                file,
                (i) -> true,
                (p, m) -> p.getMedia().add(m));
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        var product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(PRODUCT_NOT_FOUND_MESSAGE.apply(id)));
        productRepository.delete(product);
    }

    @Transactional
    private void doUploadProductMedia(final long id, MultipartFile file, Predicate<MediaInfo> mediaInfoFilter,
            BiConsumer<Product, Media> productMediaAction)
            throws IOException {
        final var product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(PRODUCT_NOT_FOUND_MESSAGE.apply(id)));
        final var mediaInfo = MediaInfo.fromMimeType(file.getContentType());
        final var bucket = buckets.getProduct();
        final var key = UUID.randomUUID().toString();
        final var media = Media.builder()
                .bucket(bucket)
                .key(key)
                .mediaType(mediaInfo.getMediaType())
                .extension(mediaInfo.getExtension())
                .build();
        if (!mediaInfoFilter.test(mediaInfo)) {
            throw new UnsupportedDataTypeException();
        }
        productMediaAction.accept(product, media);
        productRepository.save(product);
        storageClient.putObject(
                bucket,
                key + "." + mediaInfo.getExtension(),
                file.getBytes());
    }

}
