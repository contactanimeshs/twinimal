package com.contactanimeshs.twinimal.model.twitter;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DataInGetBookmarks {
    String text;
    String author_id;
    AttachmentsInDataInBookmarks attachments;
    String id;
    String created_at;
}
