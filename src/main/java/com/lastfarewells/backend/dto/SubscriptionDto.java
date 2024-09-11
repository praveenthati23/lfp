package com.lastfarewells.backend.dto;

import java.time.Instant;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubscriptionDto {

    private Long    subscriptionId;
    private String  name;
    private Instant createdOn;
    private Instant updatedOn;

    private List<FeatureDto> features;

    private PlanDto plan;

    @Data
    @Builder
    public static class FeatureDto {
        private Long id;
        private String name;
    }

    @Data
    @Builder
    public static class PlanDto {
        private int id;
        private MediaData photos;
        private AudioSettings lastAudios;
        private VideoSettings lastVideos;
        private MediaData lastLetters;
    }
    @Data
    @Builder
    public static class MediaData {
        private int dataCountLimit;
        private int uploadSizeLimit;
    }
    @Data
    @Builder
    public static class AudioSettings {
        private int lengthLimit;
        private int dataCountLimit;
        private int uploadSizeLimit;
    }

    @Data
    @Builder
    public static class VideoSettings {
        private int lengthLimit;
        private int dataCountLimit;
        private int uploadSizeLimit;
    }
}
