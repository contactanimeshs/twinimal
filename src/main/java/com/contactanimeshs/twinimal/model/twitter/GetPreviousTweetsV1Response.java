package com.contactanimeshs.twinimal.model.twitter;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @author Animesh (@contactanimeshs)
 */

@Data
@NoArgsConstructor
public class GetPreviousTweetsV1Response {
    List<Map<String, Object>> statuses;
    Map<String, Object> search_metadata;
}
