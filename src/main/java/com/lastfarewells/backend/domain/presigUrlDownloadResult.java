package com.lastfarewells.backend.domain;

import com.lastfarewells.backend.entity.PresignedUrl;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class presigUrlDownloadResult {
   public String url;
   public PresignedUrl metadata;
}
