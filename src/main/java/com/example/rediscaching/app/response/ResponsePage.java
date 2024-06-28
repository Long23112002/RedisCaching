package com.example.rediscaching.app.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponsePage<T, S> {
  private List<S> data;
  private MetaData metaData;

  public ResponsePage(Page<T> page, Class<S> s) {
    ModelMapper mapper = new ModelMapper();
    data = page.getContent().stream()
            .map(ob -> mapper.map(ob, s))
            .collect(Collectors.toList());
    metaData = new MetaData(page);
  }


  public ResponsePage(Page<S> page) {
    this.data = page.getContent();
    this.metaData = new MetaData(page);
  }


  public ResponsePage(Pageable pageable, Long total, List<T> listInput, Class<S> s) {
    ModelMapper mapper = new ModelMapper();
    data = listInput.stream()
            .map(ob -> mapper.map(ob, s))
            .collect(Collectors.toList());
    metaData = new MetaData(total, pageable);
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class MetaData {
    @JsonProperty("page")
    private int page;
    @JsonProperty("size")
    private int size;
    @JsonProperty("totalPages")
    private int totalPages;
    @JsonProperty("totalElements")
    private long totalElements;


    public <T> MetaData(Page<T> page) {
      this.page = page.getNumber();
      this.size = page.getSize();
      this.totalPages = page.getTotalPages();
      this.totalElements = page.getTotalElements();
    }


    public MetaData(long totalElements, Pageable pageable) {
      this.page = pageable.getPageNumber();
      this.size = pageable.getPageSize();
      this.totalElements = totalElements;
      this.totalPages = (int) Math.ceil((double) totalElements / pageable.getPageSize());
    }
  }
}