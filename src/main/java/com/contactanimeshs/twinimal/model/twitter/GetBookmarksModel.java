package com.contactanimeshs.twinimal.model.twitter;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetBookmarksModel {
    String id;
    String text;
    String author_id;
    String author_name;
    String author_username;
    String author_profilepic;
    String url;
}
