package com.contactanimeshs.twinimal.model.twitter;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Animesh (@contactanimeshs)
 */

@Data
@NoArgsConstructor
public class MetaFromPreviousTweetsAPI {
    String newest_id;
    String oldest_id;
    long result_count;
    String next_token;
}
