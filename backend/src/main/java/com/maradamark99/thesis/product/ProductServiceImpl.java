package com.maradamark99.thesis.product;

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

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    private final StorageClient storageClient;

    private final Buckets buckets;

    public Page<ProductDTO> getAll(Pageable pageable) {
        return productRepository
                .findAll(pageable)
                .map(productMapper::entityToDto);
    }

    @Override
    public Long create(ProductDTO productDTO) {
        // TODO: check for duplicates
        return productRepository
                .save(productMapper.dtoToEntity(productDTO))
                .getId();
    }

    @Override
    @Transactional
    public void uploadMedia(final long id, MultipartFile file) throws IOException {
        final var product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id: %d not found".formatted(id)));
        final var bucket = buckets.getProduct();
        final var mediaInfo = MediaInfo.fromMimeType(file.getContentType());
        final var key = "%d/%s".formatted(id, mediaInfo.getMediaType().toString().toLowerCase()) + UUID.randomUUID();
        product.getMedia().add(
                Media.builder()
                        .bucket(bucket)
                        .key(key)
                        .mediaType(mediaInfo.getMediaType())
                        .extension(mediaInfo.getExtension())
                        .build()
        );
        productRepository.save(product);
        storageClient.putObject(
                bucket,
                key + "." + mediaInfo.getExtension(),
                file.getBytes()
        );
    }

    @Override
}
