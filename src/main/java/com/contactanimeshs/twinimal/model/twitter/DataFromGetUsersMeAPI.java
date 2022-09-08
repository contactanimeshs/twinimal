package com.contactanimeshs.twinimal.model.twitter;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Animesh (@contactanimeshs)
 */

@Data
@NoArgsConstructor
public class DataFromGetUsersMeAPI {
    String id;
    String name;
    String username;
}
