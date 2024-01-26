package com.maradamark99.thesis.media;

import lombok.Getter;

@Getter
public class MediaInfo {

    private final MediaType mediaType;

    private final String extension;

    private MediaInfo(MediaType mediaType, String extension) {
        this.mediaType = mediaType;
        this.extension = extension;
    }

    public static MediaInfo fromMimeType(String mimeType) {
        var fileInfo = mimeType.split("/");
        return new MediaInfo(MediaType.of(fileInfo[0]), fileInfo[1]);
    }

}
