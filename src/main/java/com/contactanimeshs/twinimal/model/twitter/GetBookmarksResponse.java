package com.contactanimeshs.twinimal.model.twitter;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class GetBookmarksResponse {
    List<DataInGetBookmarks> data;
    Object meta;
}
