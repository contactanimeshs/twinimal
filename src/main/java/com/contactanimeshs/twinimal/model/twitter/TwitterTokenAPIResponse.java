package com.contactanimeshs.twinimal.model.twitter;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Animesh (@contactanimeshs)
 */

@Data
@NoArgsConstructor
public class TwitterTokenAPIResponse {
    String token_type;
    String expires_in;
    String access_token;
    String scope;
    String refresh_token;
}
