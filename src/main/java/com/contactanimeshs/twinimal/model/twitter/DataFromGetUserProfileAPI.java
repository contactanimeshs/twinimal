package com.contactanimeshs.twinimal.model.twitter;

import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * @author Animesh (@contactanimeshs)
 */

@Data
@NoArgsConstructor
public class DataFromGetUserProfileAPI {
    String name;
    String profile_image_url;
    String location;
    String id;
    String description;
    String username;
}
