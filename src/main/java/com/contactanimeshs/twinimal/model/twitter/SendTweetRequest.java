package com.contactanimeshs.twinimal.model.twitter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Animesh (@contactanimeshs)
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SendTweetRequest {
    String text;
}
