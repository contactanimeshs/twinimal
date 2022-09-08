package com.contactanimeshs.twinimal.model.twitter;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Animesh (@contactanimeshs)
 */

@Data
@NoArgsConstructor
public class GetPreviousTweetsResponse {
    List<DataFromPreviousTweetsAPI> data;
    MetaFromPreviousTweetsAPI meta;
}
