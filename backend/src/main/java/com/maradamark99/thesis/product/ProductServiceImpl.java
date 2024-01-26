package com.maradamark99.thesis.product;

import com.maradamark99.thesis.category.CategoryRepository;
import com.maradamark99.thesis.exception.ResourceNotFoundException;
import com.maradamark99.thesis.media.Media;
import com.maradamark99.thesis.media.MediaInfo;
import com.maradamark99.thesis.storage.Buckets;
import com.maradamark99.thesis.storage.StorageClient;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.UUID;
import java.util.function.Function;

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

    public Page<ProductDTO> getAll(Pageable pageable) {
        return productRepository
                .findAll(pageable)
                .map(productMapper::entityToDto);
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
    @Transactional
    public void uploadProductMedia(final long id, MultipartFile file, boolean isThumbnail) throws IOException {
        final var product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(PRODUCT_NOT_FOUND_MESSAGE.apply(id)));
        final var bucket = buckets.getProduct();
        final var mediaInfo = MediaInfo.fromMimeType(file.getContentType());
        final var key = "/%s/".formatted(mediaInfo.getMediaType().toString().toLowerCase()) + UUID.randomUUID();
        final var media = Media.builder()
                .bucket(bucket)
                .key(key)
                .mediaType(mediaInfo.getMediaType())
                .extension(mediaInfo.getExtension())
                .build();
        if (isThumbnail) {
            product.setThumbnailImage(media);
        } else {
            product.getMedia().add(media);
        }
        productRepository.save(product);
        storageClient.putObject(
                bucket,
                key + "." + mediaInfo.getExtension(),
                file.getBytes());
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        var product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(PRODUCT_NOT_FOUND_MESSAGE.apply(id)));
        productRepository.delete(product);
    }

}
